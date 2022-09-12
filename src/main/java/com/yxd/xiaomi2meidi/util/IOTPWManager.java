package com.yxd.xiaomi2meidi.util;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;

/* loaded from: classes10.dex */
public class IOTPWManager {
    static int[] switchTable = {8, 9, 10, 11, 28, 29, 30, 31, 0, 1, 2, 3, 16, 17, 18, 19, 12, 13, 14, 15, 24, 25, 26, 27, 20, 21, 22, 23, 4, 5, 6, 7};

    public static byte[] pwReplace(byte[] bArr) {
        int ceil = ((int) Math.ceil(bArr.length / 32.0d)) * 32;
        byte[] bArr2 = new byte[ceil];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = new byte[ceil];
        for (int i = 0; i < ceil; i++) {
            int i2 = (i / 32) * 32;
            int i3 = i % 32;
            switch (i3) {
                case 0:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 1:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 1);
                    break;
                case 2:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 3:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] + 3);
                    break;
                case 4:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 5:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 5);
                    break;
                case 6:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 7:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] - 7);
                    break;
                case 8:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 9:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 9);
                    break;
                case 10:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 11:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] + 11);
                    break;
                case 12:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 13:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 13);
                    break;
                case 14:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 15:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] - 15);
                    break;
                case 16:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 17:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 17);
                    break;
                case 18:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 19:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] + 19);
                    break;
                case 20:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 21:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 21);
                    break;
                case 22:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 23:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] + C11147c.MIDEA_GAS_WATER_HEATER);
                    break;
                case 24:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 25:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 25);
                    break;
                case 26:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 27:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] + 27);
                    break;
                case 28:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 29:
                    bArr3[i2 + switchTable[i3]] = (byte) ((bArr2[i] & 255) ^ 29);
                    break;
                case 30:
                    bArr3[i2 + switchTable[i3]] = bArr2[i];
                    break;
                case 31:
                    bArr3[i2 + switchTable[i3]] = (byte) (bArr2[i] + C11147c.MIDEA_DISH_WASHER);
                    break;
            }
        }
        return deleteArrayTailZero(bArr3);
    }

    public static byte[] pwUnReplace(byte[] bArr) {
        int ceil = ((int) Math.ceil(bArr.length / 32.0d)) * 32;
        byte[] bArr2 = new byte[ceil];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = new byte[ceil];
        for (int i = 0; i < ceil; i++) {
            int i2 = (i / 32) * 32;
            int i3 = i % 32;
            switch (i3) {
                case 0:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 1:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 1);
                    break;
                case 2:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 3:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] - 3);
                    break;
                case 4:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 5:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 5);
                    break;
                case 6:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 7:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] + 7);
                    break;
                case 8:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 9:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 9);
                    break;
                case 10:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 11:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] +  -11);
                    break;
                case 12:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 13:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 13);
                    break;
                case 14:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 15:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] + 15);
                    break;
                case 16:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 17:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 17);
                    break;
                case 18:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 19:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] + C11147c.MIDEA_WATER_PURIFIER);
                    break;
                case 20:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 21:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 21);
                    break;
                case 22:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 23:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] + 29);
                    break;
                case 24:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 25:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 25);
                    break;
                case 26:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 27:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] - 27);
                    break;
                case 28:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 29:
                    bArr3[i] = (byte) ((bArr2[i2 + switchTable[i3]] & 255) ^ 29);
                    break;
                case 30:
                    bArr3[i] = bArr2[i2 + switchTable[i3]];
                    break;
                case 31:
                    bArr3[i] = (byte) (bArr2[i2 + switchTable[i3]] + 31);
                    break;
            }
        }
        return deleteArrayTailZero(bArr3);
    }

    private static byte[] deleteArrayTailZero(byte[] bArr) {
        int length = bArr.length - 1;
        while (length >= 0 && bArr[length] == 0) {
            length--;
        }
        int i = length + 1;
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

//    public static String encode(String str) {
//        return IOTBase64.encode(new String(new IOTAES("rockykaikainihao".getBytes()).InvCipher(new byte[]{-123, 96, 29, C11147c.MIDEA_HOB, -107, -18, C11147c.MIDEA_WATER_PURIFIER, 62, -13, 59, -8,  -32, 68, C11147c.MIDEA_STEAMER, 48, -59, -28, 94, 46, -8, -36, C11147c.MIDEA_MICROWAVE_OVEN, 13, MessagePack.Code.STR8, -86, MessagePack.Code.EXT32, -117, 13, BleOrderConstans.MSG_TYPE_COUNTRYCODE, 90, 82, MessagePack.Code.EXT32, -86, 32, 108, 45, 5, -26, -96, 113, 36, -7, MessagePack.Code.MAP16, -16, -28, 3, 126, -1, 20, -10, -87, -52, MessagePack.Code.FIXEXT4, -17, -14, 13, -62, 13, 55, -36, 53, C11147c.MIDEA_STERILIZER, 125, ByteSourceJsonBootstrapper.UTF8_BOM_2})).toCharArray(), pwUnReplace(str.getBytes()));
//    }

    public static String decode(String str) {
        return new String(pwReplace(IOTBase64.decode(new String(new IOTAES("rockykaikainihao".getBytes()).InvCipher(new byte[]{-123, 96, 29, C11147c.MIDEA_HOB, -107, -18, C11147c.MIDEA_WATER_PURIFIER, 62, -13, 59, -8, -32, 68, C11147c.MIDEA_STEAMER, 48, -59, -28, 94, 46, -8, -36, C11147c.MIDEA_MICROWAVE_OVEN, 13, -39, -86, -55, -117, 13, 103, 90, 82, -55, -86, 32, 108, 45, 5, -26, -96, 113, 36, -7, -34, -16, -28, 3, 126, -1, 20, -10, -87, -52, -42, -17, -14, 13, -62, 13, 55, -36, 53, C11147c.MIDEA_STERILIZER, 125, ByteSourceJsonBootstrapper.UTF8_BOM_2})).toCharArray(), str)));
    }

//    public static void main(String[] args) {
//        String decode = IOTPWManager.decode(HTTP_SERVER_IOT_SECRET);
//        System.out.println(decode);
//    }
}