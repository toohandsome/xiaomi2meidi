package com.yxd.xiaomi2meidi.tracker.service;

import com.yxd.xiaomi2meidi.tracker.Common;
import okhttp3.Call;
import okhttp3.Request;
import org.springframework.util.StringUtils;

/* loaded from: classes11.dex */
public class RequestInfo {
    public int businessCode;
    public String requestMethod;
    public String requestUrl;
    public long responseTime;

    public RequestInfo() {
        this.responseTime = -1L;
        this.businessCode = -1;
    }

    public RequestInfo(long j, String str, String str2, int i) {
        this.responseTime = -1L;
        this.businessCode = -1;
        this.responseTime = j;
        this.requestMethod = str;
        this.requestUrl = str2;
        this.businessCode = i;
    }

    public static String format(String str) {
        return (!str.equals( "-1") && str != null) ? str : "";
    }

    public static RequestInfo formatCall(Call call, String str, String str2, int i) {
        Request request = call.request();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.requestMethod = str;
        requestInfo.requestUrl = str2;
        String header = request.header(Common.TRACE_TIMESTAMP);
        if (!StringUtils.isEmpty(header)) {
            try {
                requestInfo.responseTime = System.currentTimeMillis() - Long.valueOf(header).longValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        requestInfo.businessCode = i;
        return requestInfo;
    }

    public static RequestInfo formatCall(Call call, String str, String str2) {
        return formatCall(call, str, str2, -1);
    }

    @Override
    public String toString() {
        return format(String.valueOf(this.responseTime)) + "|" + format(this.requestMethod) + "|" + format(this.requestUrl) + "|" + format(String.valueOf(this.businessCode));
    }
}