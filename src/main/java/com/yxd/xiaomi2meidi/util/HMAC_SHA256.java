package com.yxd.xiaomi2meidi.util;

import java.io.UnsupportedEncodingException;

/* loaded from: classes12.dex */
public class HMAC_SHA256 {
    private static final String HEX_LC = "0123456789abcdef";

    public static byte[] hmac_sha256(String str, String str2) {
        try {
            return hmac_sha256(str.getBytes("UTF-8"), str2.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] hmac_sha256(byte[] bArr, byte[] bArr2) {
        try {
            if (bArr.length > 64) {
                bArr = sha256(bArr);
            }
            byte[] bArr3 = new byte[64];
            for (int i = 0; i < bArr.length; i++) {
                bArr3[i] = (byte) (bArr[i] ^ 54);
            }
            for (int length = bArr.length; length < 64; length++) {
                bArr3[length] = 54;
            }
            byte[] sha256 = sha256(bArr3, bArr2);
            SHA256.zdump_hex("out1", sha256);
            for (int i2 = 0; i2 < bArr.length; i2++) {
                bArr3[i2] = (byte) (bArr[i2] ^ 92);
            }
            for (int length2 = bArr.length; length2 < 64; length2++) {
                bArr3[length2] = 92;
            }
            return sha256(bArr3, sha256);
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] sha256(byte[]... bArr) {
        if (bArr.length > 1) {
            int i = 0;
            for (byte[] bArr2 : bArr) {
                i += bArr2.length;
            }
            byte[] bArr3 = new byte[i];
            int i2 = 0;
            for (byte[] bArr4 : bArr) {
                for (byte b : bArr4) {
                    bArr3[i2] = b;
                    i2++;
                }
            }
            SHA256.zdump_hex("nb", bArr3);
            return SHA256.sha256(bArr3);
        }
        return SHA256.sha256(bArr[0]);
    }

    public static String intsToLcHexString(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < iArr.length) {
            stringBuffer.append(Integer.toHexString(iArr[i]));
            stringBuffer.append(",");
            i++;
            if (i % 8 == 0) {
                stringBuffer.append('\n');
            }
        }
        return stringBuffer.toString();
    }

    public static String bytesToLcHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            stringBuffer.append(byteToLcHexString(b));
        }
        return stringBuffer.toString();
    }

    public static String byteToLcHexString(byte b) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HEX_LC.charAt((b >> 4) & 15));
        stringBuffer.append(HEX_LC.charAt(b & 15));
        return stringBuffer.toString();
    }
}