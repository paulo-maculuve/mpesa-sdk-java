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

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public String getInitiatorIdentifier() {
		return initiatorIdentifier;
	}

	public void setInitiatorIdentifier(String initiatorIdentifier) {
		this.initiatorIdentifier = initiatorIdentifier;
	}

	public String getSecurityCredential() {
		return securityCredential;
	}

	public void setSecurityCredential(String securityCredential) {
		this.securityCredential = securityCredential;
	}

}
