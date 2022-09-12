package com.yxd.xiaomi2meidi.intercept;

import com.yxd.xiaomi2meidi.tracker.Common;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes11.dex */
public class TrackerInterceptor implements Interceptor {
    private Map<String, String> mTrackerMap;

    public TrackerInterceptor(Map<String, String> map) {
        this.mTrackerMap = map;
    }

    public TrackerInterceptor(String str, String str2) {
        HashMap hashMap = new HashMap();
        this.mTrackerMap = hashMap;
        hashMap.put(Common.TRACE_PATH, str);
        this.mTrackerMap.put(Common.TRACE_ID, str2);
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(getNewRequest(chain.request()));
    }

    public Request getNewRequest(Request request) {
        Map<String, String> map = this.mTrackerMap;
        if (map != null) {
            map.put(Common.TRACE_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
            Request.Builder newBuilder = request.newBuilder();
            for (String str : this.mTrackerMap.keySet()) {
                String str2 = this.mTrackerMap.get(str);
                if (!StringUtils.isEmpty(str2)) {
                    newBuilder.addHeader(str, str2);
                }
            }
            return newBuilder.build();
        }
        return request;
    }
}