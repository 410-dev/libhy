package me.hysong.libhyextended.security;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import me.hysong.libhycore.CoreAES;
import me.hysong.libhycore.CoreSHA;

/**
 * This class provides methods to generate RSA key pairs,
 * and to encrypt and decrypt messages using RSA.
 */
public class RSACryptography {
    private static final String RSA = "RSA";
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    /**
     * Generates an RSA key pair.
     *
     * @param algorithmInBits the key size in bits. Acceptable values are any number greater than or equal to 512.
     * @return An array containing the Base64 encoded public and private keys.
     *    [0]: Public key - Encryption key
     *    [1]: Private key - Decryption key
     * @throws IllegalArgumentException if an unsupported key size is provided.
     */
    public static String[] generateRSAKey(int algorithmInBits) {
        if (algorithmInBits < 512) {
            throw new IllegalArgumentException("Invalid key size. Acceptable key sizes are 1024, 2048, 3072, and 4096 bits.");
        }
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
            keyGen.initialize(algorithmInBits);
            KeyPair pair = keyGen.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

            return new String[]{publicKey, privateKey};
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate RSA key pair: " + e.getMessage());
        }
    }

    /**
     * Generates an RSA key pair with a default size of 512 bits.
     *
     * @return An array containing the Base64 encoded public and private keys.
     *      [0]: Public key - Encryption key
     *      [1]: Private key - Decryption key
     */
    public static String[] generateRSAKey() {
        return generateRSAKey(512);
    }

    /**
     * Encrypts a plaintext message using the provided public key.
     *
     * @param plaintext  the plaintext message to be encrypted.
     * @param publicKey  the Base64 encoded public key.
     * @return The encrypted message in Base64 encoding.
     */
    public static String encrypt(String plaintext, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            PublicKey key = keyFactory.generatePublic(publicKeySpec);

            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt message: " + e.getMessage());
        }
    }

    /**
     * Decrypts a ciphertext message using the provided private key.
     *
     * @param ciphertext  the encrypted message in Base64 encoding.
     * @param privateKey  the Base64 encoded private key.
     * @return The decrypted plaintext message.
     */
    public static String decrypt(String ciphertext, String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            PrivateKey key = keyFactory.generatePrivate(privateKeySpec);

            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt message: " + e.getMessage());
        }
    }

    /**
     * Encrypts the RSA private key using a password.
     *
     * @param privateKey  the RSA private key to be encrypted.
     * @param password    the password to derive the encryption key.
     * @return The encrypted private key.
     */
    public static String encryptPrivateKey(String privateKey, String password) {
        try {
            byte[] key = deriveKeyFromPassword(password);
            return CoreAES.encrypt(privateKey, Base64.getEncoder().encodeToString(key));
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt private key: " + e.getMessage());
        }
    }

    /**
     * Decrypts the RSA private key using a password.
     * @param encryptedPrivateKey  the encrypted RSA private key.
     * @param password             the password to derive the decryption key.
     * @return The decrypted private key.
     */
    public static String decryptPrivateKey(String encryptedPrivateKey, String password) {
        try {
            byte[] key = deriveKeyFromPassword(password);
            return CoreAES.decrypt(encryptedPrivateKey, Base64.getEncoder().encodeToString(key));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt private key: " + e.getMessage());
        }
    }

    /**
     * Derives a key from a password.
     *
     * @param password  the password used for key derivation.
     * @return A byte array representing the derived key.
     */
    private static byte[] deriveKeyFromPassword(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[16], ITERATION_COUNT, KEY_LENGTH);
        SecretKey secretKey = factory.generateSecret(spec);
        return secretKey.getEncoded();
    }

    /**
     * Generates an RSA public key from a hashed password.
     * WARNING: This is not traditional RSA key generation and might not provide the same security guarantees as generating RSA keys through standard methods. This approach should be used with caution and primarily for experimental or educational purposes.
     *
     * @param password the password to be hashed and used as a seed.
     * @param salt the salt to be used for hashing.
     * @return The Base64 encoded RSA public key.
     */
    public static String generatePublicKeyFromPassword(String password, String salt) {
        try {
            // Hash the password with the salt using SHA512
            String hashedPassword = CoreSHA.hash512(password, salt);

            // Convert the hash to a byte array to use as a seed
            byte[] seed = hashedPassword.getBytes();

            // Create a secure random instance seeded with the hashed password
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);

            // Initialize a key pair generator using the secure random instance
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4), secureRandom);

            // Generate the key pair and extract the public key
            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();

            // Return the Base64 encoded public key
            System.err.println("WARNING: Key generation was successful, but this approach is not recommended for production use. This is not traditional RSA key generation and might not provide the same security guarantees as generating RSA keys through standard methods. This approach should be used with caution and primarily for experimental or educational purposes.");
            return Base64.getEncoder().encodeToString(publicKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate public key from password: " + e.getMessage());
        }
    }

    /**
     * Decrypts a message using an RSA private key derived from a password.
     *
     * @param encryptedMessage the encrypted message in Base64 encoding.
     * @param password the password used to derive the private key.
     * @param salt the salt used for hashing the password.
     * @return The decrypted message.
     */
    public static String decryptWithPasswordDerivedKey(String encryptedMessage, String password, String salt) {
        try {
            // Hash the password with the salt using SHA512
            String hashedPassword = CoreSHA.hash512(password, salt);

            // Convert the hash to a byte array to use as a seed
            byte[] seed = hashedPassword.getBytes();

            // Create a secure random instance seeded with the hashed password
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);

            // Initialize a key pair generator using the secure random instance
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4), secureRandom);

            // Generate the key pair and extract the private key
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();

            // Decrypt the message
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));

            System.err.println("WARNING: Key generation was successful, but this approach is not recommended for production use. This is not traditional RSA key generation and might not provide the same security guarantees as generating RSA keys through standard methods. This approach should be used with caution and primarily for experimental or educational purposes.");
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt message: " + e.getMessage());
        }
    }
}
