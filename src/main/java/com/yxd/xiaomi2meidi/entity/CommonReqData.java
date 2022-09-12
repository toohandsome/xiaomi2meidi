package com.yxd.xiaomi2meidi.entity;

import lombok.Data;

@Data
public class CommonReqData {

    private MucData data;
    private IotData iotData;
    private String timestamp;


    public CommonReqData(MucData data, IotData iotData, Object o) {
        this.data =data;
        this.iotData =iotData;
    }

}
