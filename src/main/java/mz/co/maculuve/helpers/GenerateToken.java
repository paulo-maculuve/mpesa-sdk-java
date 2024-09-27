package mz.co.maculuve.helpers;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class GenerateToken {
    public static String parse(String apiKey, String publicKey) throws Exception {
        PublicKey key = keysToCertificate(publicKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] token = cipher.doFinal(apiKey.getBytes());
        return Base64.getEncoder().encodeToString(token);
    }

    protected static PublicKey keysToCertificate(String publicKey) throws Exception {
        String formattedKey = "-----BEGIN PUBLIC KEY-----\n" + publicKey.replaceAll("(.{60})", "$1\n") + // Wrap key to 60 characters
                "\n-----END PUBLIC KEY-----";

        String base64PublicKey = formattedKey.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");

        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);

    }
}
