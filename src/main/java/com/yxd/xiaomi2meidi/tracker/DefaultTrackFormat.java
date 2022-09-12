package com.yxd.xiaomi2meidi.tracker;

import com.yxd.xiaomi2meidi.tracker.service.RequestInfo;
import com.yxd.xiaomi2meidi.tracker.service.TrackFormat;
import org.springframework.util.StringUtils;

import java.util.UUID;

/* loaded from: classes11.dex */
public class DefaultTrackFormat extends TrackFormat {
    public DefaultTrackFormat(String str) {
        super(str);
        initTransId();
    }

    private void initTransId() {
        this.transId = this.transaction + getUUID();
    }

    @Override // com.midea.service.tracker.service.TrackFormat
    public String makeTranceId() {
        String uuid = getUUID();
        this.tranceId = uuid;
        return uuid;
    }

    @Override // com.midea.service.tracker.service.TrackFormat
    public String addToTrancePath(String str) {
        if (StringUtils.isEmpty(this.tracePath)) {
            this.tracePath = getUUID() + ":" + this.platformType + "_" + this.appVersion;
        }
        if (!StringUtils.isEmpty(str)) {
            this.tracePath += "#" + str;
        }
        return this.tracePath;
    }

    @Override // com.midea.service.tracker.service.TrackFormat
    public String getTracePath() {
        if (StringUtils.isEmpty(this.tracePath)) {
            this.tracePath = addToTrancePath(null);
        }
        return this.tracePath;
    }

    @Override // com.midea.service.tracker.service.TrackFormat
    public String makeTrackLog(RequestInfo requestInfo, String str, boolean z) {
        String str2 = ((System.currentTimeMillis() + "|" + getPlatformType() + "_" + getAppName() + "_" + getAppVersion()) + "|" + getTransId() + "$" + (z ? makeTranceId() : getTranceId()) + "|" + getTracePath()) + "|" + requestInfo.toString() + "|" + str;
        this.tracePath = null;
        this.tranceId = null;
        return str2;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}