package com.yxd.xiaomi2meidi.entity.contrl;

import com.alibaba.fastjson2.annotation.JSONField;

public class ControllerBody {

    @JSONField(ordinal = 1)
    private String reqId;
    @JSONField(ordinal = 2)
    private long stamp;
    @JSONField(ordinal = 3)
    private String applianceCode = "183618442012635";
    @JSONField(ordinal = 4)
    private Command command = new Command();

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public String getApplianceCode() {
        return applianceCode;
    }

    public void setApplianceCode(String applianceCode) {
        this.applianceCode = applianceCode;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
