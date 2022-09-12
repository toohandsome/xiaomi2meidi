package com.yxd.xiaomi2meidi.entity;

import lombok.Data;

@Data
public final class MasRsp<T> {
    private int code;
    private T data;
    private String msg;


}