package com.yxd.xiaomi2meidi.util;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public final class RequestUtils {
    public static final RequestUtils INSTANCE = new RequestUtils();

    private RequestUtils() {
    }

    public final RequestBody createBody(Object any) {
        MediaType parse = MediaType.parse("application/json");
        Gson gson = new Gson();
        RequestBody create = RequestBody.create(parse, gson.toJson(any) );

        return create;
    }

    public final RequestBody createBody(String jsonStr) {

        RequestBody create = RequestBody.create(MediaType.parse("application/json"), jsonStr);

        return create;
    }
}