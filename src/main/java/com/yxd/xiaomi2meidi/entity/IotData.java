package com.yxd.xiaomi2meidi.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Data
public final class IotData {
    private String appVersion;
    private String birthDate;
    private Integer clientType;
    private String deviceId;
    private String dxToken;
    private String force;
    private String headPicUrl;
    private String iampwd;
    private String iotAppId;
    private Integer jPushAppCode;
    private String loginAccount;
    private String loginToken;
    private String mobile;
    private String newIampwd;
    private String newPassword;
    private String openUid;
    private String password;
    private String randomCode;
    private String reqId = UUID.randomUUID().toString().replace("-", "");
    private String sex;
    private String smsCode;
    private Integer src;
    private String stamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    private String tAccessToken;
    private String tNickname;
    private Integer type;


    public IotData(String iotAppId, String loginAccount, String password, String iampwd, String loginIdStr, String appVersion, String deviceId, int i) {
        this.iotAppId = iotAppId;
        this.loginAccount = loginAccount;
        this.password = password;
        this.iampwd = iampwd;
        this.appVersion = appVersion;
        this.deviceId = deviceId;
        this.clientType = 1;
    }

}