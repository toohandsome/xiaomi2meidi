package com.yxd.xiaomi2meidi.start;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.yxd.xiaomi2meidi.cache.Gcache;
import com.yxd.xiaomi2meidi.config.MqttProviderConfig;
import com.yxd.xiaomi2meidi.corn.RefreshToken;
import com.yxd.xiaomi2meidi.entity.Config;
import com.yxd.xiaomi2meidi.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class AppStart implements CommandLineRunner {

//    @Autowired
//    private OkHttpClient httpClient;

    @Autowired
    RefreshToken refreshToken;

    @Override
    public void run(String... args) {
        try {
            File config = new File("config.json");
            Path configPath = Paths.get(config.getAbsolutePath());
            log.info("configPath: " + config.getAbsolutePath());
            Gcache.cache.put("configPath", config.getAbsolutePath());
            if (config.exists()) {
                var s = Files.readString(configPath);
                Config config1 = null;

                try {
                    config1 = JSON.parseObject(s, Config.class);
                } catch (Exception e) {
                    log.error("配置文件格式错误,请按照json 格式进行配置");
                    return;
                }
                if (config1 == null) {
                    log.error("配置文件为空");
                    return;
                }
                Gcache.config = config1;

                if (!StringUtils.hasText(config1.getPhone()) || !StringUtils.hasText(config1.getPassword()) || !StringUtils.hasText(config1.getAcNameList()) || !StringUtils.hasText(config1.getBlinkerKeyList())) {
                    log.error("配置不完整,请检查 phone,password,acNameList,blinkerKeyList 均已配置");
                } else {
                    String[] authKeyArr = config1.getBlinkerKeyList().replace("，", ",").split(",");
                    String[] nameArr = config1.getAcNameList().replace("，", ",").split(",");
                    if (authKeyArr.length != nameArr.length) {
                        log.error("配置不正确: acNameList,blinkerKeyList 数量是否相等");
                        return;
                    }

                    for (int i = 0; i < authKeyArr.length; i++) {
                        var authKey = authKeyArr[i].trim();
                        try {

                            HttpRequest request = HttpRequest.newBuilder(URI.create("https://iot.diandeng.tech/api/v1/user/device/diy/auth?authKey=" + authKey + "&protocol=mqtt"))
                                    .GET()
                                    .build();
                            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
                            var connectInfoStr = response.body();
                            log.info(connectInfoStr);
                            JSONObject parse = (JSONObject) JSON.parse(connectInfoStr);
                            JSONObject detail = (JSONObject) parse.get("detail");
                            MqttProviderConfig mqttProviderConfig = new MqttProviderConfig();
                            mqttProviderConfig.deviceName = detail.getString("deviceName");
                            mqttProviderConfig.productKey = detail.getString("productKey");
                            mqttProviderConfig.authKey = authKey;

                            var host = detail.getString("host");
                            if (host.contains("aliyuncs")) {
                                mqttProviderConfig.isAliyun = true;
                            }
                            mqttProviderConfig.hostUrl = host
                                    .replaceFirst("mqtts:", "tcp:")
                                    .replaceFirst("mqtt:", "tcp:")
                                    + ":"
                                    + detail.getString("port");

                            mqttProviderConfig.iotId = detail.getString("iotId");
                            mqttProviderConfig.iotToken = detail.getString("iotToken");
                            mqttProviderConfig.uuid = detail.getString("uuid");
                            mqttProviderConfig.chineseName = nameArr[i].trim();

                            mqttProviderConfig.nomalMqttConnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    refreshToken.configureTasks();
                }

            } else {

                Config object = new Config();

                object.setDeviceId(getDeviceId());
                object.setAppVersion(getAppVersion());
                object.setDeviceName(getDeviceName());
                object.setOsVersion(getOsVer());
                Gcache.config = object;
                log.warn("config not exists , create new");
                config.createNewFile();
                Utils.writeConfig();
                log.warn("请在配置文件: " + config.getAbsolutePath() + " 中输入 phone: 手机号, password: 密码,acNameList: 空调名称(多个用逗号隔开), blinkerKeyList: 点灯的authkey(多个用逗号隔开,需要与空调名称一一对应)  ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    private Response doExecute(Request request) {
//        Call call = httpClient.newCall(request);
//        try {
//            Response resp = call.execute();
//            return resp;
//        } catch (IOException e) {
//            log.error("第三方请求失败，Body: {}", e);
//            e.printStackTrace();
//        }
//        return null;
//    }

    public String getDeviceId() {
        var uuid = UUID.randomUUID().toString();
        byte[] bytes = ConcurrentMessageDigest.digestMD5(uuid.getBytes(StandardCharsets.UTF_8));
        return MD5Encoder.encode(bytes).substring(0, 16);
    }


    private String getAppVersion() {
        var verList = List.of("8.10.0.91", "8.9.0.87", "8.9.0.86", "8.9.0.85", "8.8.0.78", "8.7.0.71", "8.6.0.63", "8.5.0.58", "8.4.0.52", "8.3.0.45", "8.2.0.34", "8.1.1.23", "8.1.0.16", "8.0.1.7", "8.0.0.3", "7.13.1.130", "7.12.0.117", "7.12.0.115", "7.11.1.1", "7.11.0.116", "7.11.0.115", "7.11.0.107", "7.10.1.1", "7.10.0.38", "7.9.1.2", "7.9.0.56", "7.9.0.53", "7.9.0.42", "7.8.0.59", "7.7.1.1", "7.7.0.129", "7.6.1.1", "7.6.0.60");

        var ran = new Random().nextInt(32);
        return verList.get(ran);
    }

    private String getDeviceName() {
        var devList = List.of("Samsung Galaxy S10", "Samsung Galaxy S20", "Samsung Galaxy Note 10", "Oppo R11 Plus", "Redmi Note 9", "Huawei Mate 30", "Huawei P30 Pro", "HUAWEI Mate S", "HUAWEI Mate 8", "HUAWEI Mate 9", "HUAWEI Mate 10", "HUAWEI Mate 20", "HUAWEI nova 2", "HUAWEI nova 2s", "HUAWEI nova 3e", "HUAWEI nova 3", "HUAWEI nova 3i", "HUAWEI nova 4", "Xiaomi MI 6", "Xiaomi MI 7", "Xiaomi MI 8", "Xiaomi MI 9", "Xiaomi MI 10", "Xiaomi MI 11", "Xiaomi MI 12");
        var ran = new Random().nextInt(24);
        return devList.get(ran);
    }

    private String getOsVer() {
        var osList = List.of("7", "8", "9", "10", "11", "12");
        var ran = new Random().nextInt(6);
        return osList.get(ran);
    }


}
