package com.voter.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil extends DigestUtils {

    private static final char[] HEX_CODE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public DigestUtil() {
    }

    public static String encrypt(String data) {
        return StringUtils.isBlank(data) ? "" : sha1Hex(md5Hex(data));
    }


    public static String sha1Hex(String data) {
        return sha1Hex(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha1Hex(final byte[] bytes) {
        return digestHex("SHA-1", bytes);
    }

    public static String digestHex(String algorithm, byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return encodeHex(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeHex(byte[] bytes) {
        StringBuilder r = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {
            r.append(HEX_CODE[b >> 4 & 15]);
            r.append(HEX_CODE[b & 15]);
        }

        return r.toString();
    }

    public static String md5Hex(final String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(StandardCharsets.UTF_8));
    }
}
