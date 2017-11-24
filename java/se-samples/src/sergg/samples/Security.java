/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package sergg.samples;

import org.apache.commons.lang.ArrayUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class Security {
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 1;
    private static final int KEY_LENGTH = 256;

    public static void main(String[] args) throws GeneralSecurityException {
        byte[] salt = getNextSalt();
        System.out.println("salt=" + DatatypeConverter.printBase64Binary(salt));
        String password = "jNETx";
        System.out.println("1=" + DatatypeConverter.printBase64Binary(hash(password.toCharArray(), salt)));
        System.out.println("2=" + DatatypeConverter.printBase64Binary(hashMD(password.toCharArray(), salt)));


        String data = generatePasswordHash(password.toCharArray());
        System.out.println(data);
        System.out.println(checkPasswordHash(password.toCharArray(), data));

    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static byte[] hashMD(char[] password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            return digest.digest((new String(password) + new String(salt)).getBytes());
        } catch (/*UnsupportedEncodingException |*/ NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String generatePasswordHash(char[] password) {
        byte[] salt = getNextSalt();
        byte[] hash = hash(password, salt);
        String data = "$2y$10$" +
                DatatypeConverter.printBase64Binary(salt) + "$" +
                DatatypeConverter.printBase64Binary(hash);
        return data;
    }

    public static boolean checkPasswordHash(char[] password, String storedHash) throws GeneralSecurityException {
        String[] algorithmParams = storedHash.split("\\$");
        byte[] salt = DatatypeConverter.parseBase64Binary(algorithmParams[3]);
        byte[] hash = DatatypeConverter.parseBase64Binary(algorithmParams[4]);
        return checkPasswordHash(password, salt, hash);
    }

    /**
     * Returns true if the given password and salt match the hashed value, false otherwise.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password     the password to check
     * @param salt         the salt used to hash the password
     * @param expectedHash the expected hashed value of the password
     *
     * @return true if the given password and salt match the hashed value, false otherwise
     */
    public static boolean checkPasswordHash(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 16 bytes random salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }
}

