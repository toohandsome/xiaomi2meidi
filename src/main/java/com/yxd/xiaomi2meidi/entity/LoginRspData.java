package com.yxd.xiaomi2meidi.entity;

import lombok.Data;

@Data
public class LoginRspData {
    private  Long clusterId;
    private  String iotUserId;
    private  String isOldUse;
    private  String jwtToken;
    private  String key;
    private  LoginRspMData mdata;
    private  String uid;
    private  UserInfo userInfo;


    public LoginRspData(Long clusterId, String iotUserId, String isOldUse, String jwtToken, String key, LoginRspMData mdata, String uid, UserInfo userInfo) {
        this.clusterId = clusterId;
        this.iotUserId = iotUserId;
        this.isOldUse = isOldUse;
        this.jwtToken = jwtToken;
        this.key = key;
        this.mdata = mdata;
        this.uid = uid;
        this.userInfo = userInfo;
    }

}
