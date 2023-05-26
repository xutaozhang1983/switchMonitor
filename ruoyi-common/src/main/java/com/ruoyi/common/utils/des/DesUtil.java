package com.ruoyi.common.utils.des;

import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.io.Charsets;
import org.springframework.lang.Nullable;


import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;
import java.util.Objects;


public class DesUtil {
    public static final String DES_ALGORITHM = "DES";

    public DesUtil() {
    }

    public static String genDesKey() {
        return StringUtils.random(16);
    }

    public static String encryptToHex(byte[] data, String password) throws Exception {
        return HexUtil.encodeToString(encrypt(data, password));
    }

    @Nullable
    public static String encryptToHex(@Nullable String data, String password) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        } else {
            byte[] dataBytes = data.getBytes(Charsets.UTF_8);
            return encryptToHex(dataBytes, password);
        }
    }

    @Nullable
    public static String decryptFormHex(@Nullable String data, String password) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        } else {
            byte[] hexBytes = HexUtil.decode(data);
            return new String(decrypt(hexBytes, password), Charsets.UTF_8);
        }
    }


    public static String base64Decoder(String str)  {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(str));
    }

    public static String base64Encoder(String str)  {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(str.getBytes());
    }



    public static byte[] encrypt(byte[] data, byte[] desKey) throws Exception {
        return des(data, desKey, 1);
    }

    public static byte[] encrypt(byte[] data, String desKey) throws Exception {
        return encrypt(data, ((String) Objects.requireNonNull(desKey)).getBytes(Charsets.UTF_8));
    }

    public static byte[] decrypt(byte[] data, byte[] desKey) throws Exception {
        return des(data, desKey, 2);
    }

    public static byte[] decrypt(byte[] data, String desKey) throws Exception {
        return decrypt(data, ((String)Objects.requireNonNull(desKey)).getBytes(Charsets.UTF_8));
    }

    private static byte[] des(byte[] data, byte[] desKey, int mode) throws Exception {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec(desKey);
            cipher.init(mode, keyFactory.generateSecret(desKeySpec), Holder.SECURE_RANDOM);
            return cipher.doFinal(data);
        } catch (Exception var6) {
            throw new Exception(var6);
        }
    }
}
