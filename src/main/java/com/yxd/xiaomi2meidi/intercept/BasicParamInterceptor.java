package com.yxd.xiaomi2meidi.intercept;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public final class BasicParamInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String uuid = UUID.randomUUID().toString();

        String replace$default = uuid.replace("-", "");
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        String method = request.method();
        if (method != null) {

            if (method.equals("POST")) {
                if (request.body() instanceof FormBody) {
                    FormBody.Builder builder = new FormBody.Builder();
                    FormBody formBody = (FormBody) request.body();
                    if (formBody != null) {
                        int size = formBody.size();
                        for (int i = 0; i < size; i++) {
                            builder.add(formBody.name(i), formBody.value(i));
                        }
                    }
                    builder.add("reqId", replace$default);
                    builder.add("stamp", timeStamp);
                    Response proceed = chain.proceed(request.newBuilder().post(builder.build()).build());
                    return proceed;
                }
                Buffer buffer = new Buffer();
                RequestBody body = request.body();
                if (body != null) {
                    body.writeTo(buffer);
                }
                if (buffer.size() == 0) {
                    HashMap hashMap = new HashMap();
                    HashMap hashMap2 = hashMap;
                    hashMap2.put("reqId", replace$default);
                    hashMap2.put("stamp", timeStamp);
                    Gson gson = new Gson();
                    Response proceed2 = chain.proceed(request.newBuilder().post(RequestBody.create(MediaType.parse("application/json"), gson.toJson(hashMap))).build());
                    return proceed2;
                }
                String oldParamsJson = buffer.readUtf8();
                if (oldParamsJson.startsWith("{") && oldParamsJson.endsWith("}")) {
                    Gson initGson = initGson();
                    Type type = new TypeToken<HashMap<String, Object>>() { // from class: com.midea.base.http.interceptor.BasicParamInterceptor$intercept$rootMap$1
                    }.getType();
                    Object fromJson = initGson.fromJson(oldParamsJson, type);
                    HashMap hashMap3 = (HashMap) fromJson;
                    HashMap hashMap4 = hashMap3;
                    hashMap4.put("reqId", replace$default);
                    hashMap4.put("stamp", timeStamp);
                    Gson gson2 = new Gson();
                    Response proceed3 = chain.proceed(request.newBuilder().post(RequestBody.create(MediaType.parse("application/json"), gson2.toJson(hashMap3))).build());
                    return proceed3;
                }
            }

        }
        Response proceed5 = chain.proceed(request);

        return proceed5;
    }

    private final Gson initGson() {
        Gson create = new GsonBuilder().registerTypeAdapter(new TypeToken<HashMap<String, Object>>() { // from class: com.midea.base.http.interceptor.BasicParamInterceptor$initGson$1
        }.getType(), BasicParamInterceptor$initGson$2.INSTANCE).create();

        return create;
    }


}