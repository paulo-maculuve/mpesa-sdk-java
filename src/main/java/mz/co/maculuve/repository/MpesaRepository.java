package mz.co.maculuve.repository;

import mz.co.maculuve.Transaction;

import java.io.IOException;

public interface MpesaRepository {

    public Transaction c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws Exception;

    public Transaction b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException;

    public Transaction b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference) throws IOException;

    public Transaction reversal(double amount, String transactionReference, String thirdPartyReference) throws IOException;

    public Transaction transaction(String transactionReference, String thirdPartyReference) throws IOException;
}
