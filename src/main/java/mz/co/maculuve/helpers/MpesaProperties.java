package mz.co.maculuve.helpers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mpesa")
public class MpesaProperties {
	private String environment;
	private String publicKey;
	private String apiKey;
	private String serviceProviderCode;
	private String initiatorIdentifier;
	private String securityCredential;

	public String getEnvironment() {
		return environment;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public String getInitiatorIdentifier() {
		return initiatorIdentifier;
	}

	public String getSecurityCredential() {
		return securityCredential;
	}


}
