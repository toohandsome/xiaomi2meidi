package com.yxd.xiaomi2meidi.tracker.service;

/* loaded from: classes11.dex */
public abstract class TrackFormat {
    protected String tracePath;
    protected String tranceId;
    protected String transId;
    protected String transaction;
    protected String platformType = "Android";
    protected String appName = "";
    protected String appVersion = "";

    public abstract String addToTrancePath(String str);

    public abstract String makeTrackLog(RequestInfo requestInfo, String str, boolean z);

    public abstract String makeTranceId();

    public TrackFormat(String str) {
        this.transaction = str;
    }

    public String getTransaction() {
        return this.transaction;
    }

    public String getTracePath() {
        return this.tracePath;
    }

    public String getTransId() {
        return this.transId;
    }

    public String getTranceId() {
        return this.tranceId;
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public void setPlatformType(String str) {
        this.platformType = str;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getPlatformType() {
        return this.platformType;
    }
}