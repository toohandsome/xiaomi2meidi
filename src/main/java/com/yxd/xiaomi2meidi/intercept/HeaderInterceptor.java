package com.yxd.xiaomi2meidi.intercept;

import com.yxd.xiaomi2meidi.cache.Gcache;
import com.yxd.xiaomi2meidi.util.SignUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        String valueOf = String.valueOf(System.currentTimeMillis());
        Request.Builder newBuilder = chain.request().newBuilder();
        SignUtil signUtil = SignUtil.INSTANCE;
        Request request = chain.request();
        Request.Builder header = newBuilder.header("sign", signUtil.sign(request, valueOf)).header("secretVersion", "1").header("random", valueOf);
        //String appVersionName = AppUtils.getAppVersionName();
        Response proceed = chain.proceed(header.header("version", Gcache.config.getAppVersion()).header("systemVersion", Gcache.config.getOsVersion()).header("platform", "0").header("Accept-Encoding", "identity").build());

        return proceed;
    }
   Pattern p = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
    private final String getRealVersionName(String str) {
        Matcher matcher = p.matcher(str);
        if (matcher.find()) {
            String group = matcher.group(0);

            return group;
        }
        return str;
    }
}