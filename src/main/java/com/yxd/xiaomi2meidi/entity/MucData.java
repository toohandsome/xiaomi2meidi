package com.yxd.xiaomi2meidi.entity;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class MucData {

    private JsonObject f30861android;
    private String appKey;
    private String appVersion;
    private JsonObject clientData;
    private String deviceId;
    private String deviceName;
    private String imgCode;
    private Integer logoffType;
    private String mobile;
    private String osVersion;
    private Integer platform;
    private String randomToken;
    private String tokenPwd;
    private String uid;


    public MucData(String appKey, String appVersion, String deviceId, String deviceName, String osVersion, int platform) {

        this.appKey = appKey;
        this.appVersion = appVersion;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.osVersion = osVersion;
        this.platform = platform;
    }



}
