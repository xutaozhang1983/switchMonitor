package com.ruoyi.common.utils.des;

import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.io.Charsets;
import org.springframework.lang.Nullable;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;


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


    public static SecretKey getKey(String key) throws Exception {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            random.setSeed(key.getBytes());//设置加密用的种子，密钥
            keyGenerator.init(random);
            return keyGenerator.generateKey();

        }catch (Exception e){
            throw new Exception(e);
        }

    }

    /**
     * 加密测试
     */

    public static String AESEncrypt(String content,String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密测试
     */

    public static String AESDecrypt(String content,String key) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(decryptedBytes,StandardCharsets.UTF_8);
        }catch (Exception e){
            return null;
        }
    }

    public static String generateMD5(String input) {
        try {
            // 创建MD5摘要算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算输入字符串的MD5哈希值
            byte[] mdBytes = md.digest(input.getBytes());
            // 将MD5哈希值转换为16进制表示
            BigInteger bigInt = new BigInteger(1, mdBytes);
            String md5Hash = bigInt.toString(16);
            // 如果MD5哈希值长度不足32位，前面补0
            while (md5Hash.length() < 32) {
                md5Hash = "0" + md5Hash;
            }
            // 截取前16位作为密码
            return md5Hash.substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
