package com.yxd.xiaomi2meidi.config;

import com.yxd.xiaomi2meidi.intercept.BasicParamInterceptor;
import com.yxd.xiaomi2meidi.intercept.HeaderInterceptor;
import com.yxd.xiaomi2meidi.intercept.TrackerInterceptor;
import com.yxd.xiaomi2meidi.intercept.UserAgentInterceptor;
import com.yxd.xiaomi2meidi.util.SSLSocketClient;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.X509TrustManager;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        X509TrustManager trustManager = Platform.get().platformTrustManager();
        OkHttpClient mHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new BasicParamInterceptor())
                .addInterceptor(new UserAgentInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new TrackerInterceptor(new HashMap<>()))
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.getX509TrustManager())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        return mHttpClient;
    }
}
