package me.hy.libhycore;

import me.hy.libhycore.exceptions.InvalidInputException;
import me.hy.libhycore.exceptions.InvalidInputException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Simple AES encryption and decryption.
 */
public class CoreAES {

    /**
     * Encrypts a string using AES encryption. The key is hashed using SHA-512 and then truncated to 16 bytes.
     * @param input String to encrypt using AES. This is equivalent to plain text.
     * @param keyStr Key to use for encryption. This is equivalent to password.
     * @return Encrypted string
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidInputException
     */
    public static String encrypt(String input, String keyStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidInputException {

        if (input == null || keyStr == null) {
            throw new InvalidInputException("Input or key is null");
        }

        if (input.length() == 0 || keyStr.length() == 0) {
            return input;
        }

        keyStr = CoreSHA.hash512(keyStr);
        input = CoreBase64.encode(input);
        SecretKeySpec secretKey;
        MessageDigest sha = null;
        String encryptedString = "";
        byte[] key;
        key = keyStr.getBytes(StandardCharsets.UTF_8);
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] pass = cipher.doFinal(input.trim().getBytes(StandardCharsets.UTF_8));

        encryptedString = Base64.getEncoder().encodeToString(pass);
        encryptedString = CoreBase64.encode(encryptedString);

        return encryptedString;
    }

    /**
     * Decrypts a string using AES encryption. The key is hashed using SHA-512 and then truncated to 16 bytes.
     * @param input String to decrypt using AES. This is equivalent to cipher text.
     * @param keyStr Key to use for decryption. This is equivalent to password.
     * @return Decrypted string
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidInputException
     */
    public static String decrypt(String input, String keyStr) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidInputException {

        if (input == null || keyStr == null) {
            throw new InvalidInputException("Input or key is null");
        }

        if (input.length() == 0 || keyStr.length() == 0) {
            return input;
        }

        keyStr = CoreSHA.hash512(keyStr);
        input = CoreBase64.decode(input);
        byte[] key;
        String decryptedString = "";
        SecretKeySpec secretKey;

        MessageDigest sha = null;
        key = keyStr.getBytes("UTF-8");
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] pass = input.trim().getBytes();
        decryptedString = (new String(cipher.doFinal(Base64.getDecoder().decode(pass))));
        decryptedString = CoreBase64.decode(decryptedString);

        return decryptedString;
    }

    /**
     * @deprecated
     * Do not use this constructor. It does not have any functionality.
     */
    @Deprecated
    public CoreAES() {}
}