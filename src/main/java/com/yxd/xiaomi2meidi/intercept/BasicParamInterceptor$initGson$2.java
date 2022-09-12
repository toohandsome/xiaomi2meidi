package com.yxd.xiaomi2meidi.intercept;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

final class BasicParamInterceptor$initGson$2<T> implements JsonDeserializer<HashMap<String, Object>> {
    public static final BasicParamInterceptor$initGson$2 INSTANCE = new BasicParamInterceptor$initGson$2();

    BasicParamInterceptor$initGson$2() {
    }



    @Override
    public HashMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        HashMap<String, Object> hashMap = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            hashMap.put(key, value);
        }
        return hashMap;
    }
}