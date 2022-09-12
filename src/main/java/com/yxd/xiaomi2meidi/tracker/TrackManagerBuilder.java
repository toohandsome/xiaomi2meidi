package com.yxd.xiaomi2meidi.tracker;

import com.yxd.xiaomi2meidi.tracker.login.LoginLogTrackHelper;
import com.yxd.xiaomi2meidi.tracker.service.TrackFormat;
import com.yxd.xiaomi2meidi.tracker.service.TrackManager;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes11.dex */
public class TrackManagerBuilder {
    private static final String DEFAULT_APP_NAME = "美的美居";
    private TrackFormat trackFormat;
    private String transaction;

    public TrackManagerBuilder( String str) {
        this.transaction = str;
        this.trackFormat = new DefaultTrackFormat(str);

            this.trackFormat.setAppName(DEFAULT_APP_NAME);
        this.trackFormat.setAppVersion("v8.0.0.3");
    }

    public TrackManagerBuilder setTrackFormat(TrackFormat trackFormat) {
        this.trackFormat = trackFormat;
        return this;
    }

    public TrackManager create() {
        return new TrackManager(this.trackFormat);
    }


    public static Map<String, String> getTrackHeaders() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(Common.TRACE_ID, LoginLogTrackHelper.INSTANCE.getTrackerId());
        linkedHashMap.put(Common.TRACE_PATH, LoginLogTrackHelper.INSTANCE.getTrackerPath());
        linkedHashMap.put(Common.TRACE_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return linkedHashMap;
    }
}