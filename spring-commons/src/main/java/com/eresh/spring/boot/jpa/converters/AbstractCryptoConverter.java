package com.eresh.spring.boot.jpa.converters;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.AttributeConverter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public abstract class AbstractCryptoConverter <T> implements AttributeConverter<T, String> {
    private CipherInitializer cipherInitializer;

    public AbstractCryptoConverter() {
        this(new CipherInitializer());
    }

    public AbstractCryptoConverter(CipherInitializer cipherInitializer) {
        this.cipherInitializer = cipherInitializer;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (isNotEmpty("ZY6PiJQi9AM8ohNIx4YPbC0iFbLIRInw") && isNotNullOrEmpty(attribute)) {
            try {
                Cipher cipher = cipherInitializer.prepareAndInitCipher(Cipher.ENCRYPT_MODE, "ZY6PiJQi9AM8ohNIx4YPbC0iFbLIRInw");
                return encrypt(cipher, attribute);
            } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
        }
        return entityAttributeToString(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (isNotEmpty("ZY6PiJQi9AM8ohNIx4YPbC0iFbLIRInw") && isNotEmpty(dbData)) {
            try {
                Cipher cipher = cipherInitializer.prepareAndInitCipher(Cipher.DECRYPT_MODE, "ZY6PiJQi9AM8ohNIx4YPbC0iFbLIRInw");
                return decrypt(cipher, dbData);
            } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
        }
        return stringToEntityAttribute(dbData);
    }

    abstract boolean isNotNullOrEmpty(T attribute);

    abstract T stringToEntityAttribute(String dbData);

    abstract String entityAttributeToString(T attribute);

    byte[] callCipherDoFinal(Cipher cipher, byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(bytes);
    }

    private String encrypt(Cipher cipher, T attribute) throws IllegalBlockSizeException, BadPaddingException {
        byte[] bytesToEncrypt = entityAttributeToString(attribute).getBytes();
        byte[] encryptedBytes = callCipherDoFinal(cipher, bytesToEncrypt);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private T decrypt(Cipher cipher, String dbData) throws IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes = Base64.getDecoder().decode(dbData);
        byte[] decryptedBytes = callCipherDoFinal(cipher, encryptedBytes);
        return stringToEntityAttribute(new String(decryptedBytes));
    }
}
