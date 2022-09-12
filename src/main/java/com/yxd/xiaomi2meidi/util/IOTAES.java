package com.yxd.xiaomi2meidi.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes10.dex */
public class IOTAES {
    int[] Sbox = new int[256];
    int[] InvSbox = new int[256];

    /* renamed from: w */
    int[][][] f16913w = (int[][][]) Array.newInstance(int.class, 11, 4, 4);

    public static void log(String str) {
    }

    public static void log(String str, Object[] objArr) {
    }

    public IOTAES(byte[] bArr) {
        System.arraycopy(new int[]{40, 194, 232, 87, 72, 36, 151, 197, 24, 140, 155, 114, 247, 153, 222, 211, 133, 88, 122, 11, 68, 209, 176, 71, 199, 174, 240, 109, 83, 225, 96, 180, 75, 92, 16, 82, 1, 57, 185, 144, 171, 0, 212, 115, 100, 50, 159, 161, 106, 28, 107, 33, 241, 239, 63, 103, 149, 242, 121, 224, 12, 208, 102, 127, 44, 195, 18, 90, 52, 235, 21, 166, 188, 125, 160, 184, 85, 73, 193, 251, 233, 8, 60, 226, 86, 47, 48, 158, 14, 203, 37, 227, 70, 93, 236, 76, 9, 190, 135, 4, 137, 216, 25, 61, 113, 64, 126, 95, 22, 123, 58, 39, 67, 118, 69, 138, 53, 150, 249, 7, 143, 15, 84, 207, 186, 56, 131, 206, 42, 172, 104, 244, 128, 49, 164, 254, 147, 179, 124, 202, 182, 117, 218, 168, 215, 177, 55, 196, 108, 111, 213, 79, 35, 20, 152, 170, 220, 101, 116, 66, 200, 65, 141, 165, 34, 234, 77, 181, 23, 2, 30, 136, 145, 51, 198, 120, 59, 45, 217, 252, 27, 154, 205, 230, 31, 237, 146, 41, 74, 250, 26, 238, 13, 231, 99, 142, 255, 6, 62, 29, 248, 173, 229, 54, 5, 112, 162, 105, 132, 191, 169, 139, 3, 214, 38, 46, 130, 98, 129, 228, 148, 167, 175, 94, 157, 210, 178, 134, 43, 81, 204, 221, 19, 245, 119, 80, 192, 183, 10, 89, 78, 201, 187, 17, 163, 110, 219, 246, 97, 156, 32, 223, 253, 189, 91, 243}, 0, this.Sbox, 0, 256);
        System.arraycopy(new int[]{41, 36, 169, 212, 99, 204, 197, 119, 81, 96, 238, 19, 60, 192, 88, 121, 34, 243, 66, 232, 153, 70, 108, 168, 8, 102, 190, 180, 49, 199, 170, 184, 250, 51, 164, 152, 5, 90, 214, 111, 0, 187, 128, 228, 64, 177, 215, 85, 86, 133, 45, 173, 68, 116, 203, 146, 125, 37, 110, 176, 82, 103, 198, 54, 105, 161, 159, 112, 20, 114, 92, 23, 4, 77, 188, 32, 95, 166, 240, 151, 235, 229, 35, 28, 122, 76, 84, 3, 17, 239, 67, 254, 33, 93, 223, 107, 30, 248, 217, 194, 44, 157, 62, 55, 130, 207, 48, 50, 148, 27, 245, 149, 205, 104, 11, 43, 158, 141, 113, 234, 175, 58, 18, 109, 138, 73, 106, 63, 132, 218, 216, 126, 208, 16, 227, 98, 171, 100, 115, 211, 9, 162, 195, 120, 39, 172, 186, 136, 220, 56, 117, 6, 154, 13, 181, 10, 249, 224, 87, 46, 74, 47, 206, 244, 134, 163, 71, 221, 143, 210, 155, 40, 129, 201, 25, 222, 22, 145, 226, 137, 31, 167, 140, 237, 75, 38, 124, 242, 72, 253, 97, 209, 236, 78, 1, 65, 147, 7, 174, 24, 160, 241, 139, 89, 230, 182, 127, 123, 61, 21, 225, 15, 42, 150, 213, 144, 101, 178, 142, 246, 156, 231, 14, 251, 59, 29, 83, 91, 219, 202, 183, 193, 2, 80, 165, 69, 94, 185, 191, 53, 26, 52, 57, 255, 131, 233, 247, 12, 200, 118, 189, 79, 179, 252, 135, 196}, 0, this.InvSbox, 0, 256);
        KeyExpansion(bArr, this.f16913w);
    }

    private byte[] Cipher(byte[] bArr, int i) {
        int[][] iArr = (int[][]) Array.newInstance(int.class, 4, 4);
        for (int i2 = 0; i2 < 4; i2++) {
            for (int i3 = 0; i3 < 4; i3++) {
                iArr[i2][i3] = bArr[(i3 * 4) + i + i2] & 255;
            }
        }
        log("Cipher.state1:", (Object[]) iArr);
        log("Cipher.w:", (Object[]) this.f16913w[10]);
        AddRoundKey(iArr, this.f16913w[0]);
        log("AddRoundKey.state:", (Object[]) iArr);
        for (int i4 = 1; i4 <= 10; i4++) {
            Subints(iArr);
            ShiftRows(iArr);
            if (i4 != 10) {
                MixColumns(iArr);
            }
            AddRoundKey(iArr, this.f16913w[i4]);
        }
        for (int i5 = 0; i5 < 4; i5++) {
            for (int i6 = 0; i6 < 4; i6++) {
                bArr[(i6 * 4) + i + i5] = (byte) iArr[i5][i6];
            }
        }
        return bArr;
    }

    private byte[] InvCipher(byte[] bArr, int i) {
        int[][] iArr = (int[][]) Array.newInstance(int.class, 4, 4);
        for (int i2 = 0; i2 < 4; i2++) {
            for (int i3 = 0; i3 < 4; i3++) {
                iArr[i2][i3] = bArr[(i3 * 4) + i + i2] & 255;
            }
        }
        log("state1:", (Object[]) iArr);
        log("AddRoundKey.w:", (Object[]) this.f16913w[10]);
        AddRoundKey(iArr, this.f16913w[10]);
        log("AddRoundKey.state:", (Object[]) iArr);
        for (int i4 = 9; i4 >= 0; i4--) {
            log("loop" + i4 + ":");
            InvShiftRows(iArr);
            log("InvShiftRows.state:", (Object[]) iArr);
            InvSubints(iArr);
            log("InvSubints.state:", (Object[]) iArr);
            AddRoundKey(iArr, this.f16913w[i4]);
            log("AddRoundKey.state:", (Object[]) iArr);
            if (i4 != 0) {
                InvMixColumns(iArr);
            }
            log("loopstate:", (Object[]) iArr);
        }
        for (int i5 = 0; i5 < 4; i5++) {
            for (int i6 = 0; i6 < 4; i6++) {
                bArr[(i6 * 4) + i + i5] = (byte) iArr[i5][i6];
            }
        }
        return bArr;
    }

    public byte[] Cipher(byte[] bArr) {
        int length = bArr.length;
        int ceil = ((int) Math.ceil(length / 16.0d)) * 16;
        byte[] bArr2 = new byte[ceil];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        for (int i = 0; i < ceil; i += 16) {
            Cipher(bArr2, i);
        }
        return bArr2;
    }

    public byte[] InvCipher(byte[] bArr) {
        byte[] bArr2 = (byte[]) bArr.clone();
        int length = bArr2.length;
        for (int i = 0; i < length; i += 16) {
            InvCipher(bArr2, i);
        }
        int length2 = bArr2.length - 1;
        while (length2 > 0 && bArr2[length2] == 0) {
            length2--;
        }
        int i2 = length2 + 1;
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr2, 0, bArr3, 0, i2);
        return bArr3;
    }

    public void KeyExpansion(byte[] bArr, int[][][] iArr) {
        int[] iArr2 = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54};
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr[0][i][i2] = bArr[(i2 * 4) + i] & 255;
            }
        }
        for (int i3 = 1; i3 <= 10; i3++) {
            int i4 = 0;
            while (i4 < 4) {
                int[] iArr3 = new int[4];
                for (int i5 = 0; i5 < 4; i5++) {
                    iArr3[i5] = (i4 != 0 ? iArr[i3][i5][i4 - 1] : iArr[i3 - 1][i5][3]) & 255;
                }
                if (i4 == 0) {
                    int i6 = iArr3[0];
                    int i7 = 0;
                    while (i7 < 3) {
                        int i8 = i7 + 1;
                        iArr3[i7] = this.Sbox[iArr3[i8 % 4]];
                        i7 = i8;
                    }
                    iArr3[3] = this.Sbox[i6];
                    iArr3[0] = iArr3[0] ^ iArr2[i3 - 1];
                    iArr3[0] = iArr3[0] & 255;
                }
                for (int i9 = 0; i9 < 4; i9++) {
                    iArr[i3][i9][i4] = iArr[i3 - 1][i9][i4] ^ iArr3[i9];
                    iArr[i3][i9][i4] = iArr[i3][i9][i4] & 255;
                }
                i4++;
            }
        }
    }

    private int FFmul(int i, int i2) {
        byte[] bArr = new byte[4];
        bArr[0] = (byte) i2;
        for (int i3 = 1; i3 < 4; i3++) {
            int i4 = i3 - 1;
            bArr[i3] = (byte) (bArr[i4] << 1);
            if ((bArr[i4] & 128) != 0) {
                bArr[i3] = (byte) (bArr[i3] ^ 27);
            }
        }
        byte b = 0;
        for (int i5 = 0; i5 < 4; i5++) {
            if (((i >> i5) & 1) != 0) {
                b = (byte) (b ^ bArr[i5]);
            }
        }
        return b;
    }

    private void Subints(int[][] iArr) {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr[i][i2] = this.Sbox[iArr[i][i2]];
            }
        }
    }

    private void ShiftRows(int[][] iArr) {
        int[] iArr2 = new int[4];
        for (int i = 1; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr2[i2] = iArr[i][(i2 + i) % 4];
            }
            for (int i3 = 0; i3 < 4; i3++) {
                iArr[i][i3] = iArr2[i3];
            }
        }
    }

    private void MixColumns(int[][] iArr) {
        int[] iArr2 = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr2[i2] = iArr[i2][i];
            }
            int i3 = 0;
            while (i3 < 4) {
                int i4 = i3 + 1;
                iArr[i3][i] = (FFmul(1, iArr2[(i3 + 3) % 4]) ^ ((FFmul(2, iArr2[i3]) ^ FFmul(3, iArr2[i4 % 4])) ^ FFmul(1, iArr2[(i3 + 2) % 4]))) & 255;
                i3 = i4;
            }
        }
    }

    private void AddRoundKey(int[][] iArr, int[][] iArr2) {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                int[] iArr3 = iArr[i2];
                iArr3[i] = iArr3[i] ^ iArr2[i2][i];
                int i3 = iArr[i2][i];
            }
        }
    }

    private void InvSubints(int[][] iArr) {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr[i][i2] = this.InvSbox[iArr[i][i2]];
            }
        }
    }

    private void InvShiftRows(int[][] iArr) {
        int[] iArr2 = new int[4];
        for (int i = 1; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr2[i2] = iArr[i][((i2 - i) + 4) % 4];
            }
            for (int i3 = 0; i3 < 4; i3++) {
                iArr[i][i3] = iArr2[i3];
            }
        }
    }

    private void InvMixColumns(int[][] iArr) {
        int[] iArr2 = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr2[i2] = iArr[i2][i];
            }
            int i3 = 0;
            while (i3 < 4) {
                int i4 = i3 + 1;
                iArr[i3][i] = (FFmul(9, iArr2[(i3 + 3) % 4]) ^ ((FFmul(14, iArr2[i3]) ^ FFmul(11, iArr2[i4 % 4])) ^ FFmul(13, iArr2[(i3 + 2) % 4]))) & 255;
                i3 = i4;
            }
        }
    }

    public static void log(String str, byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(Arrays.toString(bArr));
    }

    public static void log(String str, Object obj) {
        if (obj == null) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(Arrays.deepToString(new Object[]{obj}));
    }

//    public static void logBin(String str, int i) {
//        String str2;
//        ("00000000000000" + Integer.toBinaryString(i)).substring(str2.length() - 8);
//    }
}