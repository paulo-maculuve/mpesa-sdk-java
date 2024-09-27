package mz.co.maculuve;

import com.fasterxml.jackson.annotation.JsonProperty;
import mz.co.maculuve.repository.TransactionRepository;
import org.json.JSONObject;

public class Transaction implements TransactionRepository {

    @JsonProperty("output_ResponseCode")
    private String responseCode;

    @JsonProperty("output_TransactionID")
    private String transactionID;

    @JsonProperty("output_ConversationID")
    private String conversationID;

    @JsonProperty("output_ResponseDesc")
    private String responseDescription;

    @JsonProperty("output_ThirdPartyReference")
    private String thirdPartyReference;


    public Transaction() {}

    public Transaction(JSONObject jsonObject) {
        this.responseCode = jsonObject.optString("output_ResponseCode", null);
        this.transactionID = jsonObject.optString("output_TransactionID", null);
        this.conversationID = jsonObject.optString("output_ConversationID", null);
        this.responseDescription = jsonObject.optString("output_ResponseDesc", null);
        this.thirdPartyReference = jsonObject.optString("output_ThirdPartyReference", null);
    }

    @Override
    public String getResponseCode() {
        return responseCode;
    }
    @Override
    public String getTransactionID() {
        return transactionID;
    }
    @Override
    public String getConversationID() {
        return conversationID;
    }
    @Override
    public String getDescription() {
        return responseDescription;
    }
    @Override
    public String getThirdPartReference() {
        return thirdPartyReference;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "responseCode='" + responseCode + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", conversationID='" + conversationID + '\'' +
                ", responseDescription='" + responseDescription + '\'' +
                ", thirdPartyReference='" + thirdPartyReference + '\'' +
                '}';
    }
}
