package com.yxd.xiaomi2meidi.util;

import com.yxd.xiaomi2meidi.tracker.EncodeAndDecodeUtils;

/* loaded from: classes14.dex */
public class SecurityUtils {
    private static final String DEFAULT_KEY = "xhdiwjnchekd4d512chdjx5d8e4c394D2D7S";
    private static final EncodeAndDecodeUtils UTILS = EncodeAndDecodeUtils.getInstance();

    public static String encodeMD5(String str) {
        return UTILS.encodeMD5(str);
    }

    public static byte[] encodeMD5(byte[] bArr) {
        return UTILS.encodeMD5(bArr);
    }

    public static byte[] encodeMD5WithDefaultKey(byte[] bArr) {
        byte[] bytes = DEFAULT_KEY.getBytes();
        byte[] bArr2 = new byte[bytes.length + bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(bytes, 0, bArr2, bArr.length, bytes.length);
        return UTILS.encodeMD5(bArr2);
    }

    public static String encodeSHA256(String str) {
        return UTILS.encodeSHA(str).toLowerCase();
    }

    public static String encodeAES128WithAppKey(String str, String str2) {
        try {
            if (!isNotEmpty(str) || !isNotEmpty(str2)) {
                return null;
            }
            String encodeAppKey = encodeAppKey(str2);
            String encodeIv = encodeIv(str2);
            if (!isNotEmpty(encodeAppKey)) {
                return null;
            }
            return UTILS.encodeAES(str, encodeAppKey, encodeIv);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decodeAES128WithAppKey(String str, String str2) {
        try {
            if (!isNotEmpty(str) || !isNotEmpty(str2)) {
                return null;
            }
            String encodeAppKey = encodeAppKey(str2);
            String encodeIv = encodeIv(str2);
            if (!isNotEmpty(encodeAppKey)) {
                return null;
            }
            return UTILS.decodeAES(str, encodeAppKey, encodeIv);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodeAES128(String str, String str2) {
        return encodeAES128(str, str2, null);
    }

    public static String encodeAES128(String str, String str2, String str3) {
        try {
            if (isNotEmpty(str) && isNotEmpty(str2)) {
                return UTILS.encodeAES(str, str2, str3);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decodeAES128(String str, String str2) {
        return decodeAES128(str, str2, null);
    }

    public static String decodeAES128(String str, String str2, String str3) {
        try {
            if (isNotEmpty(str) && isNotEmpty(str2)) {
                return UTILS.decodeAES(str, str2, str3);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encodeAES128WithoutKey(byte[] bArr) {
        if (bArr != null) {
            try {
                EncodeAndDecodeUtils encodeAndDecodeUtils = UTILS;
                return encodeAndDecodeUtils.encodeAES(bArr, encodeAndDecodeUtils.encodeMD5(DEFAULT_KEY.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static byte[] decodeAES128WithoutKey(byte[] bArr) {
        if (bArr != null) {
            try {
                EncodeAndDecodeUtils encodeAndDecodeUtils = UTILS;
                return encodeAndDecodeUtils.decodeAES(bArr, encodeAndDecodeUtils.encodeMD5(DEFAULT_KEY.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private static String encodeAppKey(String str) {
        String encodeMD5;

        encodeMD5 = encodeSHA256(str);
        System.out.println("encodeAppKey - encodeSHA256:  " + encodeMD5);
        encodeMD5 = UTILS.encodeMD5(str);
        System.out.println("encodeAppKey - encodeMD5:  " + encodeMD5);

        if (encodeMD5 != null) {
            return encodeMD5.substring(0, 16);
        }
        return null;
    }

    public static String encodeIv(String str) {
//        String encodeSHA256;
//        if (!MSContext.getInstance().isOverseas() || (encodeSHA256 = encodeSHA256(str)) == null) {
//            return null;
//        }
//        return encodeSHA256.substring(16, 32);
        throw new RuntimeException("xxx");
//        return null;
    }

    private static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }
}