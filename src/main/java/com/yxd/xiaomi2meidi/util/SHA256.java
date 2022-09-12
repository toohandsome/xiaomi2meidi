package com.yxd.xiaomi2meidi.util;

import java.security.MessageDigest;

/* loaded from: classes12.dex */
public class SHA256 {
    static final int SHA256_BLOCK_SIZE = 64;
    static final int SHA256_COVER_SIZE = 128;

    /* renamed from: k */
    static int[] f16262k = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};

    static final int CHX(int i, int i2, int i3) {
        return ((~i) & i3) ^ (i2 & i);
    }

    static final int MAJ(int i, int i2, int i3) {
        return ((i & i3) ^ (i & i2)) ^ (i2 & i3);
    }

    static final int ROTL(byte b, int i) {
        return (b >> (8 - i)) | (b << i);
    }

    static final int ROTL(int i, int i2) {
        return (i >> (32 - i2)) | (i << i2);
    }

    static final int ROTR(byte b, int i) {
        return (b << (8 - i)) | (b >>> i);
    }

    static final int ROTR(int i, int i2) {
        return (i << (32 - i2)) | (i >>> i2);
    }

    static final int SHFR(int i, int i2) {
        return i >>> i2;
    }

    public static void zdump_hex(String str, byte[] bArr) {
    }

    static void zdump_hex(String str, int[] iArr) {
    }

    static final int BSIG0(int i) {
        return ROTR(i, 22) ^ (ROTR(i, 2) ^ ROTR(i, 13));
    }

    static final int BSIG0(byte b) {
        return ROTR(b, 22) ^ (ROTR(b, 2) ^ ROTR(b, 13));
    }

    static final int BSIG1(int i) {
        return ROTR(i, 25) ^ (ROTR(i, 6) ^ ROTR(i, 11));
    }

    static final int BSIG1(byte b) {
        return ROTR(b, 25) ^ (ROTR(b, 6) ^ ROTR(b, 11));
    }

    static final int SSIG0(int i) {
        return SHFR(i, 3) ^ (ROTR(i, 7) ^ ROTR(i, 18));
    }

    static final int SSIG0(byte b) {
        return SHFR(b, 3) ^ (ROTR(b, 7) ^ ROTR(b, 18));
    }

    static final int SSIG1(int i) {
        return SHFR(i, 10) ^ (ROTR(i, 17) ^ ROTR(i, 19));
    }

    static final int SSIG1(byte b) {
        return SHFR(b, 10) ^ (ROTR(b, 17) ^ ROTR(b, 19));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] sha256(byte[] bArr) {
        zdump_hex("src", bArr);
        int[] iArr = {1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225};
        int length = bArr.length / 64;
        int length2 = bArr.length % 64;
        int i = length2 < 56 ? 64 : 128;
        byte[] bArr2 = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr2[i2] = 0;
        }
        if (length2 != 0) {
            for (int i3 = 0; i3 < length2; i3++) {
                bArr2[i3] = bArr[(length * 64) + i3];
            }
        }
        bArr2[length2] = Byte.MIN_VALUE;
        bArr2[i - 4] = (byte) ((((bArr.length * 8) & (-16777216)) >> 24) & 255);
        bArr2[i - 3] = (byte) ((((bArr.length * 8) & 16711680) >> 16) & 255);
        bArr2[i - 2] = (byte) ((((bArr.length * 8) & 65280) >> 8) & 255);
        bArr2[i - 1] = (byte) ((bArr.length * 8) & 255 & 255);
        zdump_hex("tmp", bArr);
        zdump_hex("cover_data", bArr2);
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            ztransform(bArr, i4, iArr);
            i4 += 64;
        }
        zdump_hex("h1", iArr);
        int i6 = i / 64;
        int i7 = 0;
        for (int i8 = 0; i8 < i6; i8++) {
            ztransform(bArr2, i7, iArr);
            i7 += 64;
        }
        byte[] bArr3 = new byte[32];
        for (int i9 = 0; i9 < 32; i9++) {
            bArr3[i9] = (byte) ((iArr[i9 / 4] >>> ((3 - (i9 % 4)) * 8)) & 255);
        }
        return bArr3;
    }

    static int ztransform(byte[] bArr, int i, int[] iArr) {
        int i2;
        int[] iArr2 = new int[64];
        zdump_hex("msg", bArr);
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= 16) {
                break;
            }
            int i5 = i + i4;
            int i6 = (bArr[i5 + 1] << 16) &16711680;
            iArr2[i3] = (bArr[i5 + 3] & 255) | i6 | ((bArr[i5] << 24) & (-16777216)) | ((bArr[i5 + 2] << 8) & 65280);
            i4 += 4;
            i3++;
        }
        for (i2 = 16; i2 < 64; i2++) {
            iArr2[i2] = SSIG1(iArr2[i2 - 2]) + iArr2[i2 - 7] + SSIG0(iArr2[i2 - 15]) + iArr2[i2 - 16];
        }
        zdump_hex("w", iArr2);
        int i7 = iArr[0];
        int i8 = iArr[1];
        int i9 = iArr[2];
        int i10 = iArr[3];
        int i11 = iArr[4];
        int i12 = iArr[5];
        int i13 = iArr[6];
        int i14 = iArr[7];
        int i15 = 0;
        while (i15 < 64) {
            int BSIG1 = i14 + BSIG1(i11) + CHX(i11, i12, i13) + f16262k[i15] + iArr2[i15];
            int i16 = i10 + BSIG1;
            i15++;
            int i17 = i8;
            i8 = i7;
            i7 = BSIG1 + BSIG0(i7) + MAJ(i7, i8, i9);
            i14 = i13;
            i13 = i12;
            i12 = i11;
            i11 = i16;
            i10 = i9;
            i9 = i17;
        }
        iArr[0] = iArr[0] + i7;
        iArr[1] = iArr[1] + i8;
        iArr[2] = iArr[2] + i9;
        iArr[3] = iArr[3] + i10;
        iArr[4] = iArr[4] + i11;
        iArr[5] = iArr[5] + i12;
        iArr[6] = iArr[6] + i13;
        iArr[7] = iArr[7] + i14;
        return 0;
    }

    static byte[] encodeSHA(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Exception unused) {
            return null;
        }
    }
}