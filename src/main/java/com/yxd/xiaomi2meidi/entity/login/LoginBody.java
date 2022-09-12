package com.yxd.xiaomi2meidi.entity.login;

import com.alibaba.fastjson2.annotation.JSONField;

public class LoginBody {

    @JSONField(ordinal = 1)
    private long timestamp;

    @JSONField(ordinal = 2)
    private LoginData data = new LoginData();

    @JSONField(ordinal = 3)
    private IotData iotData = new IotData();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public IotData getIotData() {
        return iotData;
    }

    public void setIotData(IotData iotData) {
        this.iotData = iotData;
    }
}
