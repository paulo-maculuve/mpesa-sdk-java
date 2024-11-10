package mz.co.maculuve.repository;

import mz.co.maculuve.Transaction;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface MpesaRepository {

    public CompletableFuture<Transaction> c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException;

    public CompletableFuture<Transaction> b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException;

    public CompletableFuture<Transaction> b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException;

    public CompletableFuture<Transaction> reversal(double amount, String transactionReference, String thirdPartyReference) throws IOException;

    public CompletableFuture<Transaction> transaction(String transactionReference, String thirdPartyReference) throws IOException;
}
