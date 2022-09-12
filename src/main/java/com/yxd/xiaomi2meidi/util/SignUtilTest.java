package com.yxd.xiaomi2meidi.util;

public final class SignUtilTest {
    public static final SignUtilTest INSTANCE = new SignUtilTest();

    private SignUtilTest() {
    }
    //511f16ee9a08326e8b0142d0407c07dedf6bde361fd79861ef43ef5759414e37

//    public static void main(String[] args) {
//        String mAS_KEY$http_release = IOTPWManager.decode(Common.HTTP_SERVER_MAS_KEY);
//        String sb = "{\"loginAccount\":\"18080087079\",\"stamp\":\"20220824093346\",\"reqId\":\"e84e47b7b5634fcf92d1c29ff0084ca7\"}";
//        String random = "1661304826613";
//        byte[] bArr = HMAC_SHA256.hmac_sha256(mAS_KEY$http_release, Common.HTTP_SERVER_MUC_SECRET +  sb + random);
//        String bytesToLcHexString = HMAC_SHA256.bytesToLcHexString(bArr);
//        System.out.println(bytesToLcHexString);
//    }


//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
//        String key = IOTPWManager.decode(Common.HTTP_SERVER_MAS_KEY);
//        String sb = "{\"stamp\":\"20220825232504\",\"power\":false,\"reqId\":\"8d87ea734b8943958365438d32ae2a0f\"}";
//        String random = "1661441104435";
//        String dataStr = Common.HTTP_SERVER_MUC_SECRET + sb + random;
//        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(signingKey);
//        String re = byte2hex(mac.doFinal(dataStr.getBytes("UTF-8")));
//        System.out.println(re);
//
//        String decode = IOTPWManager.decode(HTTP_SERVER_IOT_SECRET);
//
////        String shabimeidi213 = SecurityUtils.encodeSHA256("shabimeidi213");
////        System.out.println(shabimeidi213);
//
//        String shabimeidi213 =SecurityUtils.encodeMD5( SecurityUtils.encodeMD5("shabimeidi213"));
//        System.out.println(shabimeidi213);
//    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

}