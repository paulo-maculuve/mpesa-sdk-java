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
	protected static boolean fake = false;
	protected static String developmentHost = "https://api.sandbox.vm.co.mz";
	protected static String productionHost = "https://api.vm.co.mz";
	protected static String origin = "developer.mpesa.vm.co.mz";
	protected static String status = "";
	protected static int responseCode = 200;
	@Autowired
	private MpesaProperties mpesaProperties;

	public Mpesa() {
		this.mpesaProperties = new MpesaProperties();
	}

	public static void fake(int responseCode, String status) {
		Mpesa.fake = true;
		Mpesa.status = status;
		Mpesa.responseCode = responseCode;
	}

	public void setStatus(String status) {
		Mpesa.status = status;
	}

	public void setResponseCode(int code) {
		Mpesa.responseCode = code;
	}

	public Transaction c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws Exception {
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
			return mpesaRequest.setCall(fake, responseCode, status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


	}
}
