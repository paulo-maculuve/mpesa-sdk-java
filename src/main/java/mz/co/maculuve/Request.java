package mz.co.maculuve;

import mz.co.maculuve.repository.MpesaRepository;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class Request implements MpesaRepository {
    private String host;
    private String origin;
    private String token;
    private String serviceProviderCode;
    private String initiatorIdentifier;
    private String securityCredential;

    private boolean fake;
    private int responseCode;
    private String responseStatus;

    public Request(String host, String origin, String token, String serviceProviderCode,
                   String initiatorIdentifier, String securityCredential) {
        this.host = host;
        this.origin = origin;
        this.token = token;
        this.serviceProviderCode = serviceProviderCode;
        this.initiatorIdentifier = initiatorIdentifier;
        this.securityCredential = securityCredential;
    }


    public MpesaRepository setCall(boolean fake, int code, String status) {
        this.fake = fake;
        this.responseCode = code;
        this.responseStatus = status;
        return this;
    }
    @Override
    public Transaction c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException {
        String port = "18352";
        return initiateTransaction(port, amount, msisdn, transactionReference, thirdPartyReference, "/ipg/v1x/c2bPayment/singleStage/");
    }

    @Override
    public Transaction b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException {
        String port = "18345";
        return initiateTransaction(port, amount, msisdn, transactionReference, thirdPartyReference, "/ipg/v1x/b2cPayment/");
    }

    @Override
    public Transaction b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException {
        String port = "18349";
        return initiateTransaction(port, amount, msisdn, transactionReference, thirdPartyReference, "/ipg/v1x/b2bPayment/");
    }

    @Override
    public Transaction reversal(double amount, String transactionReference, String thirdPartyReference) throws IOException {
        String port = "18354";
        JSONObject data = new JSONObject();
        data.put("input_Amount", amount);
        data.put("input_TransactionID", transactionReference);
        data.put("input_ThirdPartyReference", thirdPartyReference);
        data.put("input_ServiceProviderCode", this.serviceProviderCode);
        data.put("input_InitiatorIdentifier", this.initiatorIdentifier);
        data.put("input_SecurityCredential", this.securityCredential);
        return sendRequest(port, "/ipg/v1x/reversal/", data, "PUT");
    }

    @Override
    public Transaction transaction(String transactionReference, String thirdPartyReference) throws IOException {
        String port = "18353";
        JSONObject data = new JSONObject();
        data.put("input_QueryReference", transactionReference);
        data.put("input_ThirdPartyReference", thirdPartyReference);
        data.put("input_ServiceProviderCode", this.serviceProviderCode);
        return sendRequest(port, "/ipg/v1x/queryTransactionStatus/", data, "GET");
    }

    private Transaction initiateTransaction(String port, double amount, String msisdn, String transactionReference,
                                            String thirdPartyReference, String endpoint) throws IOException {
        JSONObject data = new JSONObject();
        data.put("input_TransactionReference", transactionReference);
        data.put("input_CustomerMSISDN", msisdn);
        data.put("input_Amount", amount);
        data.put("input_ThirdPartyReference", thirdPartyReference);
        data.put("input_ServiceProviderCode", this.serviceProviderCode);
        return sendRequest(port, endpoint, data, "POST");
    }

    private Transaction sendRequest(String port, String endpoint, JSONObject data, String method) throws IOException {
        String url = this.host + ":" + port + endpoint;

        OkHttpClient client = new OkHttpClient();
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

        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            if (response.body() != null) {
                String responseBody = response.body().string();
                return new Transaction(new JSONObject(responseBody));
            } else {
                throw new IOException("Empty response body");
            }
        }
    }
}
