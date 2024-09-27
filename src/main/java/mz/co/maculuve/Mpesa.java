package mz.co.maculuve;

import mz.co.maculuve.helpers.GenerateToken;
import mz.co.maculuve.helpers.MpesaProperties;
import mz.co.maculuve.repository.MpesaRepository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mpesa {
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

	public static void setStatus(String status) {
		Mpesa.status = status;
	}

	public static void setResponseCode(int code) {
		Mpesa.responseCode = code;
	}

	public static Transaction c2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws Exception {
		return new Mpesa().mPesa().c2b(amount, msisdn, transactionReference, thirdPartyReference);
	}

	public static Transaction b2b(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().b2b(amount, msisdn, transactionReference, thirdPartyReference);
	}

	public static Transaction b2c(double amount, String msisdn, String transactionReference, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().b2c(amount, msisdn, transactionReference, thirdPartyReference);
	}

	public static Transaction transaction(String transactionReference, String thirdPartyReference) throws IOException {
		return new Mpesa().mPesa().transaction(transactionReference, thirdPartyReference);
	}

	public static Transaction reversal(double amount, String transactionID, String thirdPartyReference)
			throws IOException {
		return new Mpesa().mPesa().reversal(amount, transactionID, thirdPartyReference);
	}

	protected MpesaRepository mPesa() {
	    try {
	        String environment = mpesaProperties.getEnvironment();
	        if (environment == null) {
	            throw new IllegalArgumentException("Environment property cannot be null");
	        }

	        String host = environment.equals("production") ? productionHost : developmentHost;
	        String token = GenerateToken.parse(mpesaProperties.getApiKey(), mpesaProperties.getPublicKey());
	        String providerCode = mpesaProperties.getServiceProviderCode();
	        String identifier = mpesaProperties.getInitiatorIdentifier();
	        String credential = mpesaProperties.getSecurityCredential();

	        Request mpesaRequest = new Request(host, origin, token, providerCode, identifier, credential);
	        return mpesaRequest.setCall(fake, responseCode, status);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
