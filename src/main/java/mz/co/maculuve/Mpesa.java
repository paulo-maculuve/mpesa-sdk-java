package mz.co.maculuve;

import mz.co.maculuve.helpers.Config;
import mz.co.maculuve.helpers.GenerateToken;
import mz.co.maculuve.helpers.MpesaProperties;
import mz.co.maculuve.repository.MpesaRepository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mpesa extends Config {
	@Autowired
	private MpesaProperties mpesaProperties;

	public Mpesa() {
		this.mpesaProperties = new MpesaProperties();
	}

	public Transaction c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().c2b(amount, msisdn, transactionReference, thirdPartyReference);
	}

	public Transaction b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().b2b(amount, msisdn, transactionReference, thirdPartyReference);
	}

	public Transaction b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().b2c(amount, msisdn, transactionReference, thirdPartyReference);
	}

	public Transaction transaction(String transactionReference, String thirdPartyReference) throws IOException {
		return new Mpesa().mPesa().transaction(transactionReference, thirdPartyReference);
	}

	public Transaction reversal(double amount, String transactionID, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().reversal(amount, transactionID, thirdPartyReference);
	}

	protected MpesaRepository mPesa() {
        try {
           String token = GenerateToken.parse(Config.getApiKey(), Config.getPublicKey());
			Request mpesaRequest = new Request(Config.getHost(), Config.getOrigin(), token, Config.getServiceProviderCode(), Config.getInitiatorIdentifier(), Config.getSecurityCredential());
			return mpesaRequest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


	}
}
