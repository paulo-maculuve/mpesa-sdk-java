package mz.co.maculuve.helpers;

public class Config {

    private static String environment;
    private static String host;
    private static String hostDeveloper = "api.sandbox.vm.co.mz";
    private static String hostProduction = "api.vm.co.mz";
    private static String origin = "developer.mpesa.vm.co.mz";
    private static String apiKey;
    private static String publicKey;
    private static String serviceProviderCode = "171717";
    private static String initiatorIdentifier;
    private static String securityCredential;

    public static void config(String environment, String origin, String apiKey, String publicKey, String serviceProviderCode, String initiatorIdentifier, String securityCredential) {
        Config.host = (environment.equalsIgnoreCase("development")) ? Config.hostDeveloper : Config.hostProduction;
        Config.origin = (origin != null) ? origin : Config.origin;
        Config.apiKey = (apiKey != null) ? apiKey : Config.apiKey;
        Config.publicKey = (publicKey != null) ? publicKey : Config.publicKey;
        Config.serviceProviderCode = (serviceProviderCode != null) ? serviceProviderCode : Config.serviceProviderCode;
        Config.initiatorIdentifier = (initiatorIdentifier != null) ? initiatorIdentifier : Config.initiatorIdentifier;
        Config.securityCredential = (securityCredential != null) ? securityCredential : Config.securityCredential;
    }

    // Getters and setters...

    public static String getEnvironment() {
        return environment;
    }

    public static String getHost() {
        return host;
    }

    public static String getOrigin() {
        return origin;
    }

    public static String getApiKey() {
        return apiKey;
    }


    public static String getPublicKey() {
        return publicKey;
    }

    public static String getServiceProviderCode() {
        return serviceProviderCode;
    }

    public static String getInitiatorIdentifier() {
        return initiatorIdentifier;
    }

    public static String getSecurityCredential() {
        return securityCredential;
    }

}


