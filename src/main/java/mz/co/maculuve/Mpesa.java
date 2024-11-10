package mz.co.maculuve;

import mz.co.maculuve.helpers.Config;
import mz.co.maculuve.helpers.GenerateToken;
import mz.co.maculuve.repository.MpesaRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Mpesa extends Config {

    public CompletableFuture<Transaction> c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
            throws IOException {
        return mPesa().c2b(amount, msisdn, transactionReference, thirdPartyReference);
    }

    public CompletableFuture<Transaction> b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
            throws IOException {
        return mPesa().b2b(amount, msisdn, transactionReference, thirdPartyReference);
    }

    public CompletableFuture<Transaction> b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference)
            throws IOException {
        return mPesa().b2c(amount, msisdn, transactionReference, thirdPartyReference);
    }

    public CompletableFuture<Transaction> transaction(String transactionReference, String thirdPartyReference) throws IOException {
        return mPesa().transaction(transactionReference, thirdPartyReference);
    }

    public CompletableFuture<Transaction> reversal(double amount, String transactionID, String thirdPartyReference)
            throws IOException {
        return mPesa().reversal(amount, transactionID, thirdPartyReference);
    }

    protected MpesaRepository mPesa() {
        try {
            String token = GenerateToken.parse(Config.getApiKey(), Config.getPublicKey());
            return new Request(
                    Config.getHost(),
                    Config.getOrigin(),
                    token,
                    Config.getServiceProviderCode(),
                    Config.getInitiatorIdentifier(),
                    Config.getSecurityCredential()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
