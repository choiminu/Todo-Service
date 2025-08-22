package com.todo.common.utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class AESEncrypt implements EncryptService {

    private static final String KEY = "1234567890123456";

    public String encrypt(String input) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(input.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encrypted) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getUrlDecoder().decode(encrypted);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
