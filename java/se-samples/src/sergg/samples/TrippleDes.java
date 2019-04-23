package sergg.samples;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

public class TrippleDes {
    private static final String SECRET = "BNIWBS@PUNkmk22312we1sm'981";

    private static final Logger logger = LoggerFactory.getLogger(TrippleDes.class);

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;

    public TrippleDes(String encryptionKey) throws Exception {
        myEncryptionKey = encryptionKey;
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }


    public String encrypt(String unencryptedString) throws Exception {
        logger.debug("encrypting '{}'", unencryptedString);
        String encryptedString = null;
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
        byte[] encryptedText = cipher.doFinal(plainText);
        encryptedString = new String(Base64.encodeBase64(encryptedText));
        logger.debug("encrypted '{}'", encryptedString);
        return encryptedString;
    }


    public String decrypt(String encryptedString) throws Exception {
        logger.debug("decrypting '{}'", encryptedString);
        String decryptedText=null;
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedText = Base64.decodeBase64(encryptedString);
        byte[] plainText = cipher.doFinal(encryptedText);
        decryptedText= new String(plainText);
        logger.debug("decripted '{}'", decryptedText);
        return decryptedText;
    }


    public static void main(String args []) throws Exception
    {
        TrippleDes td= new TrippleDes(SECRET);

        String target="imparator";
        String encrypted=td.encrypt(target);
        String decrypted=td.decrypt(encrypted);

        System.out.println("String To Encrypt: "+ target);
        System.out.println("Encrypted String:" + encrypted);
        System.out.println("Decrypted String:" + decrypted);


        System.out.println("CTvBUqo61okDkhVCoRa/fHTGbG8a9M9mi/XD7GwACGI= " + td.decrypt("CTvBUqo61okDkhVCoRa/fHTGbG8a9M9mi/XD7GwACGI="));
        System.out.println("CTvBUqo61okDkhVCoRa/fBc+eZUdAjW0Hiunz61Txqg= " + td.decrypt("CTvBUqo61okDkhVCoRa/fBc+eZUdAjW0Hiunz61Txqg="));
    }
}
