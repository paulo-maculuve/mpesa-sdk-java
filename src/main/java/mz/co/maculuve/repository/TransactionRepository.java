package mz.co.maculuve.repository;

public interface TransactionRepository {

    public String getResponseCode();

    public String getTransactionStatus();

    public String getTransactionID();

    public String getConversationID();

    public String getDescription();

    public String getThirdPartReference();

}
