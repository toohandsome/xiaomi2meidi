package com.yxd.xiaomi2meidi.intercept;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UserAgentInterceptor implements Interceptor {

    @Override // okhttp3.Interceptor
    public Response intercept(Chain chain) throws IOException {
        Request.Builder removeHeader = chain.request().newBuilder().removeHeader("User-Agent");
        Request build = removeHeader.addHeader("User-Agent", "meiju/8.3.0.45 (Android;cepheus11)").build();
        try {
            return chain.proceed(build);
        } catch (NullPointerException unused) {
            return chain.proceed(build);
        }
    }

}