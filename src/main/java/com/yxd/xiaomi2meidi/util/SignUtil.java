package com.yxd.xiaomi2meidi.util;

import com.yxd.xiaomi2meidi.tracker.Common;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

public final class SignUtil {
    public static final SignUtil INSTANCE = new SignUtil();

    private SignUtil() {
    }

    public final String sign(Request request, String random) {
        MediaType contentType;
        StringBuilder sb = new StringBuilder();
        RequestBody body = request.body();
        if (body != null && (contentType = body.contentType()) != null) {
            SignUtil signUtil = INSTANCE;
            if (signUtil.isText(contentType)) {
                sb.append(signUtil.bodyToString(request));
            }
        }
        HttpUrl url = request.url();
        Set<String> keys = url.queryParameterNames();
        if (!keys.isEmpty()) {
            TreeMap treeMap = new TreeMap();
            for (String key : keys) {
                treeMap.put(key, url.queryParameter(key));
            }
            treeMap.remove("alias");
//            for (Object o : treeMap.keySet()) {
//                Map.Entry entry = (Map.Entry) o;
//                sb.append((String) entry.getKey());
//                sb.append((String) entry.getValue());
//            }
        }
//        String mAS_KEY$http_release = Common.HTTP_SERVER_MAS_KEY;
//        String bytesToLcHexString = HMAC_SHA256.bytesToLcHexString(HMAC_SHA256.hmac_sha256(mAS_KEY$http_release, Common.HTTP_SERVER_IOT_SECRET+ ((Object) sb) + random));

        String key = IOTPWManager.decode(Common.HTTP_SERVER_MAS_KEY);
        String dataStr = Common.HTTP_SERVER_MUC_SECRET + sb + random;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            String result = byte2hex(mac.doFinal(dataStr.getBytes("UTF-8")));
            return result.toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }

    private final boolean isText(MediaType mediaType) {
        return "text".equals(mediaType.type()) || "json".equals(mediaType.subtype()) || "xml".equals(mediaType.subtype()) || "html".equals(mediaType.subtype()) || "webviewhtml".equals(mediaType.subtype());
    }

    private final String bodyToString(Request request) {
        try {
            Request build = request.newBuilder().build();
            Buffer buffer = new Buffer();
            RequestBody body = build.body();
            if (body != null) {
                body.writeTo(buffer);
            }
            String readUtf8 = buffer.readUtf8();
            return readUtf8;
        } catch (IOException unused) {
            return "something error when show requestBody.";
        }
    }

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