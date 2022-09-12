package com.yxd.xiaomi2meidi.corn;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.yxd.xiaomi2meidi.cache.Gcache;
import com.yxd.xiaomi2meidi.entity.HomeList;
import com.yxd.xiaomi2meidi.entity.MasRsp;
import com.yxd.xiaomi2meidi.entity.NewToken;
import com.yxd.xiaomi2meidi.tracker.TrackManagerBuilder;
import com.yxd.xiaomi2meidi.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.util.Map;

import static com.yxd.xiaomi2meidi.util.Utils.*;

/**
 * @author Administrator
 */
@Configuration
@EnableScheduling
@Slf4j
public class RefreshToken {

    @Autowired
    private OkHttpClient httpClient;

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void configureTasks() {
        log.info("准备刷新token: " + JSON.toJSONString(Gcache.config, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteNulls));
        var accessToken = Gcache.config.getAccessToken();
        var uid = Gcache.config.getUid();
        var tokenPwd = Gcache.config.getTokenPwd();

        if (StringUtils.hasText(accessToken) && StringUtils.hasText(uid) && StringUtils.hasText(tokenPwd)) {
            Map<String, String> trackHeaders = TrackManagerBuilder.getTrackHeaders();
            trackHeaders.put("accessToken", Gcache.config.getAccessToken());
            Headers headers2 = setHeaderParams(trackHeaders);
            Request request2 = new Request.Builder().url("https://mp-prod.smartmidea.net/mas/v5/app/proxy?alias=/mj/user/autoLogin")
                    .headers(headers2)
                    .post(RequestUtils.INSTANCE.createBody(
                            "{\"data\":{\"uid\":\"" + uid + "\",\"tokenPwd\":\"" + tokenPwd + "\",\"rule\":1,\"platform\":2,\"iotAppId\":\"900\",\"deviceId\":\"" + Gcache.config.getDeviceId() + "\"}}"))
                    .build();

            var resp2 = doExecute(httpClient, request2);
            log.info("refreshTokenResp: " + resp2);
            checkResp(resp2);
            MasRsp<NewToken> refreshToken = JSON.parseObject(resp2, new TypeReference<MasRsp<NewToken>>() {
            });
            Gcache.config.setAccessToken(refreshToken.getData().getAccessToken());
            writeConfig();
        } else {
            log.warn("accessToken,uid,tokenPwd 配置不完整,如果是第一次运行无需关注");
        }
    }

}
