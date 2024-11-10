package mz.co.maculuve;

import mz.co.maculuve.repository.MpesaRepository;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Request implements MpesaRepository {
    private final String host;
    private final String origin;
    private final String token;
    private final String serviceProviderCode;
    private final String initiatorIdentifier;
    private final String securityCredential;

    public Request(String host, String origin, String token, String serviceProviderCode,
                   String initiatorIdentifier, String securityCredential) {
        this.host = host;
        this.origin = origin;
        this.token = token;
        this.serviceProviderCode = serviceProviderCode;
        this.initiatorIdentifier = initiatorIdentifier;
        this.securityCredential = securityCredential;
    }

    @Override
    public CompletableFuture<Transaction> c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) {
        String port = "18352";
        return initiateTransaction(port, amount, msisdn, transactionReference, thirdPartyReference, "/ipg/v1x/c2bPayment/singleStage/");
    }

    @Override
    public CompletableFuture<Transaction> b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference) {
        String port = "18345";
        return initiateTransaction(port, amount, msisdn, transactionReference, thirdPartyReference, "/ipg/v1x/b2cPayment/");
    }

    @Override
    public CompletableFuture<Transaction> b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) {
        String port = "18349";
        return initiateTransaction(port, amount, msisdn, transactionReference, thirdPartyReference, "/ipg/v1x/b2bPayment/");
    }

    @Override
    public CompletableFuture<Transaction> reversal(double amount, String transactionReference, String thirdPartyReference) {
        String port = "18354";
        JSONObject data = new JSONObject();
        data.put("input_Amount", amount);
        data.put("input_TransactionID", transactionReference);
        data.put("input_ThirdPartyReference", thirdPartyReference);
        data.put("input_ServiceProviderCode", this.serviceProviderCode);
        data.put("input_InitiatorIdentifier", this.initiatorIdentifier);
        data.put("input_SecurityCredential", this.securityCredential);
        return sendRequestAsync(port, "/ipg/v1x/reversal/", data, "PUT");
    }

    @Override
    public CompletableFuture<Transaction> transaction(String transactionReference, String thirdPartyReference) {
        String port = "18353";
        JSONObject data = new JSONObject();
        data.put("input_QueryReference", transactionReference);
        data.put("input_ThirdPartyReference", thirdPartyReference);
        data.put("input_ServiceProviderCode", this.serviceProviderCode);
        return sendRequestAsync(port, "/ipg/v1x/queryTransactionStatus/", data, "GET");
    }

    private CompletableFuture<Transaction> initiateTransaction(String port, double amount, String msisdn, String transactionReference,
                                                               String thirdPartyReference, String endpoint) {
        JSONObject data = new JSONObject();
        data.put("input_TransactionReference", transactionReference);
        data.put("input_CustomerMSISDN", msisdn);
        data.put("input_Amount", amount);
        data.put("input_ThirdPartyReference", thirdPartyReference);
        data.put("input_ServiceProviderCode", this.serviceProviderCode);
        return sendRequestAsync(port, endpoint, data, "POST");
    }

    private CompletableFuture<Transaction> sendRequestAsync(String port, String endpoint, JSONObject data, String method) {
        CompletableFuture<Transaction> future = new CompletableFuture<>();
        OkHttpClient client = createClient();
        okhttp3.Request request = buildRequest(port, endpoint, data, method);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    String responseBody = response.body() != null ? response.body().string() : null;
                    if (response.isSuccessful() && response.body() != null) {
                        Transaction transactionResponse = new Transaction(new JSONObject(responseBody));
                        future.complete(transactionResponse);
                    } else if (response.body() != null) {
                        JSONObject errorJson = new JSONObject(responseBody);
                        Transaction errorTransaction = new Transaction(errorJson);
                        future.complete(errorTransaction);
                    } else {
                        future.completeExceptionally(new IOException("Unsuccessful response with code: " + response.code()));
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(70, TimeUnit.SECONDS)
                .writeTimeout(70, TimeUnit.SECONDS)
                .readTimeout(70, TimeUnit.SECONDS)
                .build();
    }

    private okhttp3.Request buildRequest(String port, String endpoint, JSONObject data, String method) {
        String url = "https://" + this.host + ":" + port + endpoint;
        RequestBody body = RequestBody.create(data.toString(), MediaType.get("application/json"));

        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("origin", this.origin)
                .addHeader("Authorization", "Bearer " + this.token);

        switch (method.toUpperCase()) {
            case "POST":
                requestBuilder.post(body);
                break;
            case "PUT":
                requestBuilder.put(body);
                break;
            case "GET":
                requestBuilder.get();
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return requestBuilder.build();
    }
}
