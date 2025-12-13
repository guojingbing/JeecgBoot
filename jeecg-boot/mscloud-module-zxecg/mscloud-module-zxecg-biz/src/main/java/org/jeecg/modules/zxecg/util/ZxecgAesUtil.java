package org.jeecg.modules.zxecg.util;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ZxecgAesUtil {

    /**
     * 加密算法：AES/ECB/PKCS5Padding
     */
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES密钥长度：128位（16字节）
     */
    private static final int AES_KEY_LENGTH = 16;

    /**
     * 从密码生成AES密钥
     * @param password 密码
     * @return AES密钥
     * @throws Exception 异常
     */
    private static SecretKeySpec getAesKey(String password) throws Exception {
        // 使用SHA-256哈希密码，确保密钥长度固定
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = password.getBytes("UTF-8");
        byte[] keyBytes = md.digest(passwordBytes);
        // 截取128位（16字节）作为AES密钥
        keyBytes = Arrays.copyOf(keyBytes, AES_KEY_LENGTH);
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 加密
     * @param content 需要加密的内容
     * @param password 密码
     * @return 加密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] encrypt(String content, String password) throws Exception {
        SecretKeySpec key = getAesKey(password);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] contentBytes = content.getBytes("UTF-8");
        return cipher.doFinal(contentBytes);
    }

    /**
     * 解密
     * @param content 需要解密的内容（字节数组）
     * @param password 密码
     * @return 解密后的内容
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception {
        SecretKeySpec key = getAesKey(password);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(content);
    }

    /**
     * 将二进制转换成16进制
     * @param buf 二进制数组
     * @return 16进制字符串
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr 16进制字符串
     * @return 二进制数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr == null || hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * AES加密字符串（返回16进制）
     * @param content 需要加密的内容
     * @param password 密码
     * @return 加密后的16进制字符串
     * @throws Exception 异常
     */
    public static String aesEncrypt(String content, String password) throws Exception {
        byte[] encryptResult = encrypt(content, password);
        return parseByte2HexStr(encryptResult);
    }

    /**
     * AES解密字符串（从16进制解密）
     * @param encryptContent 加密后的16进制字符串
     * @param password 密码
     * @return 解密后的内容
     * @throws Exception 异常
     */
    public static String aesDecrypt(String encryptContent, String password) throws Exception {
        byte[] contentBytes = parseHexStr2Byte(encryptContent);
        byte[] decryptResult = decrypt(contentBytes, password);
        return new String(decryptResult, "UTF-8");
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) throws Exception {
        String content = "Zxyl2016!@#!@#";
        String password = "zxyl";

        System.out.println("原始内容：" + content);

        // 加密
        String encryptResult = aesEncrypt(content, password);
        System.out.println("加密结果：" + encryptResult);

        // 解密
        String decryptResult = aesDecrypt(encryptResult, password);
        System.out.println("解密结果：" + decryptResult);
    }
}
