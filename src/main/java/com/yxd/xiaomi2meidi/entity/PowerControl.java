package com.yxd.xiaomi2meidi.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class PowerControl {


    @JSONField(ordinal = 1)
    private String stamp ;
    @JSONField(ordinal = 2)
    private boolean power ;
    @JSONField(ordinal = 3)
    private String reqId ;


}
