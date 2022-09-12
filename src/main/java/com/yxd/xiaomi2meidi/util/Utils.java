package com.yxd.xiaomi2meidi.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.yxd.xiaomi2meidi.cache.Gcache;
import com.yxd.xiaomi2meidi.corn.RefreshToken;
import com.yxd.xiaomi2meidi.entity.MasRsp;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public class Utils {


    public static Headers setHeaderParams(Map<String, String> headerParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (headerParams != null && headerParams.size() > 0) {
            for (String key : headerParams.keySet()) {
                if (headerParams.get(key) != null) {
                    headersbuilder.add(key, headerParams.get(key));
                }
            }
        }

        headers = headersbuilder.build();
        return headers;
    }

    public static String doExecute(OkHttpClient httpClient, Request request) {
        Call call = httpClient.newCall(request);
        try {
            var resp = call.execute().body().string();
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void checkResp(String respStr) {
        MasRsp resp = JSON.parseObject(respStr, MasRsp.class);
        if (resp.getCode() != 0) {
            if (resp.getCode() == 40002) {
                log.info("token 过期 , 尝试换取token");
                RefreshToken bean = SpringUtil.getBean(RefreshToken.class);
                bean.configureTasks();
            } else if (resp.getCode() == 1020) {
                throw new RuntimeException("请在手机上退出登录后,重新登录一次后再试");
            } else {
                throw new RuntimeException("非正常返回");
            }
        }
    }

    public static void writeConfig() {

//        String pretty1 = JSONObject.toJSONString(Gcache.config, JSONWriter.Feature.PrettyFormat);
        String pretty2 = JSON.toJSONString(Gcache.config, JSONWriter.Feature.PrettyFormat);
//        log.info("pretty1:" + pretty1);
//        log.info("pretty2:" + pretty2);
        try {
            Files.writeString(Paths.get(Gcache.cache.get("configPath")), pretty2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
