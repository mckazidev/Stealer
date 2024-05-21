package dev.kazi.stealer.utils.decrypt;

import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

public class DecryptUtil {

    private static final int KEY_LENGTH = 32;
    private static final int IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    
    public static byte[] getDecryptBytes(final byte[] inputBytes, final byte[] keyBytes, final byte[] ivBytes) {
        try {
            if (inputBytes == null) {
                throw new IllegalArgumentException();
            }
            if (keyBytes == null) {
                throw new IllegalArgumentException();
            }
            if (keyBytes.length != 32) {
                throw new IllegalArgumentException();
            }
            if (ivBytes == null) {
                throw new IllegalArgumentException();
            }
            if (ivBytes.length != 12) {
                throw new IllegalArgumentException();
            }
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            final SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            final GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
            cipher.init(2, secretKeySpec, gcmParameterSpec);
            return cipher.doFinal(inputBytes);
        }
        catch (final Exception ex) {
            return null;
        }
    }
}
