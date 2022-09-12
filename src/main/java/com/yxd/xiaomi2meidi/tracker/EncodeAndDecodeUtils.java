package com.yxd.xiaomi2meidi.tracker;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes14.dex */
public class EncodeAndDecodeUtils {
    private static final String HEX_LC = "0123456789abcdef";
    private static final String HEX_UC = "0123456789ABCDEF";
    private static EncodeAndDecodeUtils instance = new EncodeAndDecodeUtils();

    private EncodeAndDecodeUtils() {
    }

    public static EncodeAndDecodeUtils getInstance() {
        return instance;
    }
 
    public String encodeAES(String str, String str2) {
        try {
            return bytesToLcHexString(encodeAES(str.getBytes("UTF-8"), str2.getBytes()));
        } catch (Exception unused) {
            return null;
        }
    }
 
    public String encodeAES(String str, String str2, String str3) {
        try {
            return bytesToLcHexString(encodeAES(str.getBytes("UTF-8"), str2.getBytes(), str3 == null ? null : str3.getBytes()));
        } catch (Exception unused) {
            return null;
        }
    }
 
    public String decodeAES(String str, String str2) {
        return decodeAES(str, str2, (String) null);
    }
 
    public String decodeAES(String str, String str2, String str3) {
        try {
            byte[] decodeAES = decodeAES(hexStringToBytes(str), str2.getBytes(), str3 == null ? null : str3.getBytes());
            if (decodeAES != null) {
                return new String(decodeAES, "UTF-8");
            }
        } catch (Exception unused) {
        }
        return null;
    }

   
    public byte[] encodeAES(byte[] bArr, byte[] bArr2) {
        return encodeAES(bArr, bArr2, (byte[]) null);
    }

   
    public byte[] encodeAES(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Cipher cipher;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            if (bArr3 == null) {
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(1, secretKeySpec);
            } else {
                Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher2.init(1, secretKeySpec, new IvParameterSpec(bArr3));
                cipher = cipher2;
            }
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
    public byte[] decodeAES(byte[] bArr, byte[] bArr2) {
        return decodeAES(bArr, bArr2, (byte[]) null);
    }

   
    public byte[] decodeAES(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Cipher cipher;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            if (bArr3 == null) {
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, secretKeySpec);
            } else {
                Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher2.init(2, secretKeySpec, new IvParameterSpec(bArr3));
                cipher = cipher2;
            }
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
    public String encodeMD5(String str) {
        return (str == null || str.length() <= 0) ? str : bytesToLcHexString(encodeMD5(str.getBytes()));
    }

   
    public byte[] encodeMD5(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

   
    public String encodeSHA(String str) {
        return bytesToLcHexString(encodeSHA(str.getBytes()));
    }

   
    public byte[] encodeSHA(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

   
    public static byte[] HMACSHA256(String str, String str2) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(str2.getBytes("UTF-8"), "HmacSHA256"));
        return mac.doFinal(str.getBytes("UTF-8"));
    }

   
    private byte[] hexStringToBytes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            try {
                bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return bArr;
    }

   
    private String bytesToUcHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(byteToUcHexString(b));
        }
        return sb.toString();
    }

   
    private String bytesToLcHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(byteToLcHexString(b));
        }
        return sb.toString();
    }

   
    private String byteToLcHexString(byte b) {
        return String.format("%s%s", Character.valueOf(HEX_LC.charAt((b >> 4) & 15)), Character.valueOf(HEX_LC.charAt(b & 15)));
    }

   
    private String byteToUcHexString(byte b) {
        return String.format("%s%s", Character.valueOf(HEX_UC.charAt((b >> 4) & 15)), Character.valueOf(HEX_UC.charAt(b & 15)));
    }
}