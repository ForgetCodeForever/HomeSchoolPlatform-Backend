package com.dlut.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptor {

    private static final int SALT_LENGTH = 16;
    private static final int ODD_STEP = 1;
    private static final int EVEN_STEP = 2;

    /**
     * 生成MD5哈希
     */
    private static String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 生成随机盐值
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes).substring(0, SALT_LENGTH);
    }

    /**
     * 对密码进行加盐MD5加密，并混淆盐
     */
    public static String encrypt(String password) {
        String salt = generateSalt();
        String md5Hash = md5Hash(password + salt);
        return mixSaltWithHash(md5Hash, salt, password.length());
    }

    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String encrypted) {
        int passLength = password.length();
        String extractedSalt = extractSalt(encrypted, passLength);
        String md5Hash = md5Hash(password + extractedSalt);
        String recombined = mixSaltWithHash(md5Hash, extractedSalt, passLength);
        return encrypted.equals(recombined);
    }

    /**
     * 将盐值混入MD5哈希
     */
    private static String mixSaltWithHash(String hash, String salt, int passLength) {
        StringBuilder mixed = new StringBuilder();
        int hashIndex = 0, saltIndex = 0;
        int interval = (passLength % 2 == 0) ? EVEN_STEP : ODD_STEP;

        for (int i = 0; i < hash.length() + salt.length(); i++) {
            if (i % (interval + 1) == 0 && saltIndex < salt.length()) {
                mixed.append(salt.charAt(saltIndex++));
            } else if (hashIndex < hash.length()) {
                mixed.append(hash.charAt(hashIndex++));
            }
        }
        return mixed.toString();
    }

    /**
     * 从混合后的密文中提取盐值
     */
    private static String extractSalt(String mixedHash, int passLength) {
        StringBuilder salt = new StringBuilder();
        int interval = (passLength % 2 == 0) ? EVEN_STEP : ODD_STEP;

        for (int i = 0; i < mixedHash.length(); i++) {
            if (i % (interval + 1) == 0 && salt.length() < SALT_LENGTH) {
                salt.append(mixedHash.charAt(i));
            }
        }
        return salt.toString();
    }

    public static void main(String[] args) {
        String password = "大工书院";
        String encrypted = encrypt(password);
        System.out.println("加密后：" + encrypted);
        System.out.println("验证密码：" + verify(password, encrypted));
    }

}
