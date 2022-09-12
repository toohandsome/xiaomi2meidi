package com.yxd.xiaomi2meidi.entity.login;

import com.alibaba.fastjson2.annotation.JSONField;

public class LoginData {
    @JSONField(ordinal = 1)
    private String appKey = "46579c15";
    @JSONField(ordinal = 2)
    private String appVersion = "1.0.0";
    @JSONField(ordinal = 3)
    private String osVersion = "";
    @JSONField(ordinal = 4)
    private int platform = 110;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
