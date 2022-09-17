package com.yxd.xiaomi2meidi.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxd.xiaomi2meidi.anotation.TokenCheck;
import com.yxd.xiaomi2meidi.cache.Gcache;
import com.yxd.xiaomi2meidi.corn.RefreshToken;
import com.yxd.xiaomi2meidi.entity.*;
import com.yxd.xiaomi2meidi.tracker.TrackManagerBuilder;
import com.yxd.xiaomi2meidi.tracker.login.LoginLogTrackHelper;
import com.yxd.xiaomi2meidi.util.IOTPWManager;
import com.yxd.xiaomi2meidi.util.RequestUtils;
import com.yxd.xiaomi2meidi.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.yxd.xiaomi2meidi.tracker.Common.HTTP_SERVER_IOT_SECRET;
import static com.yxd.xiaomi2meidi.util.Utils.*;

/**
 * @author Administrator
 */
@RestController
@Slf4j
public class AcController {

    @Autowired
    private OkHttpClient httpClient;
    @Autowired
    RefreshToken refreshToken;

    @GetMapping("/check")
    public Object check() {
        return "service is running .";
    }

    @GetMapping("/newToken")
    public void newToken() {
        refreshToken.configureTasks();
    }

    @GetMapping("/login")
    public MasRsp login() {
        LoginLogTrackHelper.INSTANCE.newTransaction("LoginTrack");
        LoginLogTrackHelper.INSTANCE.addTrackerMethod("getLoginId");

        RequestUtils requestUtils = RequestUtils.INSTANCE;
        Headers headers = setHeaderParams(TrackManagerBuilder.getTrackHeaders());
        Request request = new Request.Builder().url("https://mp-prod.smartmidea.net/mas/v5/app/proxy?alias=/v1/user/login/id/get")
                .headers(headers)
                .post(requestUtils.createBody("{\"loginAccount\":\"" + Gcache.config.getPhone() + "\"}"))
                .build();
        String resp = doExecute(httpClient, request);

        log.info("getLoginIdResp: " + resp);

        MasRsp<LoginIdRspData> loginId = JSON.parseObject(resp, new TypeReference<MasRsp<LoginIdRspData>>() {
        });
        String loginIdStr = loginId.getData().getLoginId();

        IotData iotData = new IotData(
                "900",
                Gcache.config.getPhone(),
                SecurityUtils.encodeSHA256(loginIdStr + SecurityUtils.encodeSHA256(Gcache.config.getPassword()) + IOTPWManager.decode(HTTP_SERVER_IOT_SECRET)),
                SecurityUtils.encodeMD5(SecurityUtils.encodeMD5(Gcache.config.getPassword())),
                loginIdStr,
                Gcache.config.getAppVersion(),
                Gcache.config.getDeviceId(),
                16746151
        );
        LoginLogTrackHelper.INSTANCE.addTrackerMethod("passwordLogin");
        Headers headers1 = setHeaderParams(TrackManagerBuilder.getTrackHeaders());
        Request request1 = new Request.Builder().url("https://mp-prod.smartmidea.net/mas/v5/app/proxy?alias=/mj/user/login")
                .headers(headers1)
                .post(RequestUtils.INSTANCE.createBody(
                        new CommonReqData(new MucData("46579c15", Gcache.config.getAppVersion(), Gcache.config.getDeviceId(), Gcache.config.getDeviceName(), Gcache.config.getOsVersion(), 2),
                                iotData,
                                null
                        )))
                .build();
        String resp1 = doExecute(httpClient, request1);

        log.info("loginResp: " + resp1);
        checkResp(resp1);
        MasRsp<LoginRspData> loginResp = JSON.parseObject(resp1, new TypeReference<MasRsp<LoginRspData>>() {
        });

//        ObjectMapper mapper = new ObjectMapper();
//        MasRsp<LoginRspData> loginResp = null;
//        try {
//            loginResp = mapper.readValue(resp1, new com.fasterxml.jackson.core.type.TypeReference<MasRsp<LoginRspData>>() {
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            try {
//                JavaType javaType = mapper.getTypeFactory().constructType(MasRsp.class, LoginRspData.class);
//                mapper.readValue(resp1, javaType);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                throw new RuntimeException("json 转换异常");
//            }
//
//        }


        LoginRspData data = loginResp.getData();
        String tokenPwd = data.getMdata().getTokenPwdInfo().getTokenPwd();
        Gcache.config.setTokenPwd(tokenPwd);
        String accessToken = data.getMdata().getAccessToken();
        Gcache.config.setAccessToken(accessToken);
        String uid = data.getUid();
        Gcache.config.setUid(uid);
        writeConfig();
        return loginResp;
    }


    @GetMapping("/getDeviceList")
    @TokenCheck
    public MasRsp getDeviceList() throws IOException {

        Map<String, String> trackHeaders = TrackManagerBuilder.getTrackHeaders();
        trackHeaders.put("accessToken", Gcache.config.getAccessToken());
        Headers headers2 = setHeaderParams(trackHeaders);
        Request request2 = new Request.Builder().url("https://mp-prod.smartmidea.net/mas/v5/app/proxy?alias=/v1/appliance/home/list/aggregate")
                .headers(headers2)
                .post(RequestUtils.INSTANCE.createBody(
                        "{\"cardType\":[{\"extra\":[\"uiTemplate\",\"cardOrder\"],\"query\":{\"homegroupId\":\"" + Gcache.config.getHomeId() + "\"},\"type\":\"appliance\"},{\"extra\":[],\"query\":{},\"type\":\"notActive\"},{\"extra\":[],\"query\":{\"homegroupId\":\"" + Gcache.config.getHomeId() + "\"},\"type\":\"lightGroup\"},{\"extra\":[],\"query\":{},\"type\":\"applianceType\"}]}"))
                .build();
        String resp2 = doExecute(httpClient, request2);
        log.info("getDeviceListResp: " + resp2);
        checkResp(resp2);

//        ObjectMapper mapper = new ObjectMapper();
//
//        MasRsp<ApplianceList> homegroupRespObj = mapper.readValue(resp2, new com.fasterxml.jackson.core.type.TypeReference<MasRsp<ApplianceList>>() {
//        });

        MasRsp<ApplianceList> homegroupRespObj = JSON.parseObject(resp2, new TypeReference<MasRsp<ApplianceList>>() {
        });
        List<Appliance> appliance = homegroupRespObj.getData().getAppliance();
        List<Device> deviceList = new ArrayList<>();
        for (Appliance appliance1 : appliance) {
            List<Room> roomList = appliance1.getRoomList();
            for (Room room : roomList) {
                List<Device> applianceList = room.getApplianceList();
                deviceList.addAll(applianceList);
            }
        }
        Gcache.config.setDeviceList(deviceList);
        writeConfig();
        return homegroupRespObj;
    }

    @GetMapping("/togglePower")
    @TokenCheck
    public void togglePower(String name, boolean state) throws IOException {

        log.info("togglePower: name: " + name + " , state: " + state);
        List<Device> deviceList = Gcache.config.getDeviceList();
        if (deviceList.isEmpty()) {
            log.error("当前无设备列表,尝试重新获取");
            getDeviceList();
        }
        deviceList = Gcache.config.getDeviceList();
        log.info("deviceList: " + JSON.toJSONString(deviceList));
        String sn = "";
        if (StringUtils.hasText(name)) {
            for (Device device : deviceList) {
                log.info("togglePower: device.getName(): " + device.getName() + " , name: " + name + " ,equals:  " + name.equals(device.getName()));
                if (name.equals(device.getName())) {
                    sn = device.getApplianceCode();
                }
            }
        } else {
            sn = deviceList.get(0).getApplianceCode();
            state = true;
            log.info("未指定空调名称,当前操作为打开第一个");
        }

        PowerControl powerControl = new PowerControl();
        powerControl.setPower(state);

        Map<String, String> trackHeaders = TrackManagerBuilder.getTrackHeaders();
        trackHeaders.put("accessToken", Gcache.config.getAccessToken());
        Headers headers2 = setHeaderParams(trackHeaders);
        Request request2 = new Request.Builder().url("https://mp-prod.smartmidea.net/mas/v5/app/proxy?alias=/v1/appliance/operation/togglePower/" + sn)
                .headers(headers2)
                .post(RequestUtils.INSTANCE.createBody(
                        powerControl))
                .build();
        String resp2 = doExecute(httpClient, request2);

        log.info(" togglePowerResp: " + resp2);
    }

    @TokenCheck
    public MasRsp getHomeList() {

        Map<String, String> homegroupTrackHeaders = TrackManagerBuilder.getTrackHeaders();
        homegroupTrackHeaders.put("accessToken", Gcache.config.getAccessToken());
        Headers homegroupHeader = setHeaderParams(homegroupTrackHeaders);
        Request homegroupReq = new Request.Builder().url("https://mp-prod.smartmidea.net/mas/v5/app/proxy?alias=/v1/homegroup/list/get")
                .headers(homegroupHeader)
                .post(RequestUtils.INSTANCE.createBody(
                        "{}"))
                .build();
        String homeGroupResp = doExecute(httpClient, homegroupReq);
        log.info("homeListResp: " + homeGroupResp);
        checkResp(homeGroupResp);
        MasRsp<HomeList> homegroupRespObj = JSON.parseObject(homeGroupResp, new TypeReference<MasRsp<HomeList>>() {
        });
        List<Home> homeList = homegroupRespObj.getData().getHomeList();
        if (homeList != null && !homeList.isEmpty()) {
            Gcache.config.setHomeId(homeList.get(0).getHomegroupId());
        } else {
            log.error("未获取到家庭信息");
        }
        writeConfig();
        return homegroupRespObj;
    }


}
