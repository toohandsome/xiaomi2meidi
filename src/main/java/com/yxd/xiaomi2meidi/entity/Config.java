package com.yxd.xiaomi2meidi.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Config {
    @JSONField(ordinal = 1)
    private String phone = "";
    @JSONField(ordinal = 2)
    private String password = "";
    @JSONField(ordinal = 3)
    private String acNameList = "";
    @JSONField(ordinal = 4)
    private String blinkerKeyList = "";
    @JSONField(ordinal = 5)
    private String uid = "";
    @JSONField(ordinal = 6)
    private String accessToken = "";
    @JSONField(ordinal = 7)
    private String tokenPwd = "";
    @JSONField(ordinal = 8)
    private String homeId = "";
    @JSONField(ordinal = 9)
    private String appVersion = "";
    @JSONField(ordinal = 10)
    private String deviceId = "";
    @JSONField(ordinal = 11)
    private String deviceName = "";
    @JSONField(ordinal = 12)
    private String osVersion = "";
    @JSONField(ordinal = 13)
    private List<Device> deviceList = new ArrayList<>();
    @JSONField(ordinal = 14)
    private boolean useNullInfo = true;

}

