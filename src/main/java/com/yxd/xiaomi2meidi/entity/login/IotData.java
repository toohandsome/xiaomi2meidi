package com.yxd.xiaomi2meidi.entity.login;

import com.alibaba.fastjson2.annotation.JSONField;

public class IotData {

    @JSONField(ordinal = 1)
    private String iotAppId = "1133";

    @JSONField(ordinal = 2)
    private String wxAccessToken = "";

    @JSONField(ordinal = 3)
    private String nickname = "";

    @JSONField(ordinal = 4)
    private String reqId;

    @JSONField(ordinal = 5)
    private String stamp;


    public String getIotAppId() {
        return iotAppId;
    }

    public void setIotAppId(String iotAppId) {
        this.iotAppId = iotAppId;
    }

    public String getWxAccessToken() {
        return wxAccessToken;
    }

    public void setWxAccessToken(String wxAccessToken) {
        this.wxAccessToken = wxAccessToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
