package com.yxd.xiaomi2meidi.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yxd.xiaomi2meidi.controller.AcController;
import com.yxd.xiaomi2meidi.util.RequestUtils;

import javax.net.SocketFactory;

import com.yxd.xiaomi2meidi.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

@Slf4j
public class MqttProviderConfig {

    public String authKey = "";
    public String iotId = "";
    public String iotToken = "";
    public String hostUrl = "";
    public String deviceName = "";
    public String chineseName = "";
    public String uuid = "";
    public String productKey = "";
    public boolean isAliyun = false;

    /**
     * 客户端对象
     */
    private MqttClient mqttClient = null;


    public void nomalMqttConnect() throws MqttException {

        log.info(chineseName + " hostUrl: " + hostUrl);
        log.info(chineseName + " deviceName: " + deviceName);
        mqttClient = new MqttClient(hostUrl, deviceName, new MemoryPersistence());

        //连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        options.setSocketFactory(SocketFactory.getDefault());
        options.setCleanSession(true);
//            options.sett
        //设置连接用户名
        options.setUserName(iotId);
        //设置连接密码
        options.setPassword(iotToken.toCharArray());
        //设置超时时间，单位为秒
        options.setConnectionTimeout(2);
        //设置心跳时间 单位为秒，表示服务器每隔 1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        options.setKeepAliveInterval(60);
        //设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
        options.setWill("willTopic", (deviceName + "与服务器断开连接").getBytes(), 1, false);

        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                var msgStr = new String(message.getPayload());
                log.info(chineseName + " 接收到消息: topic: " + topic + " , " + msgStr);
                var mess = "{\"deviceType\":\"OwnApp\",\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"" + uuid + "\",\"data\":{\"state\":\"online\",\"timer\":\"000\",\"version\":\"0.1.0\"}}";

                if (isAliyun) {

                    if (topic.contains("rrpc")) {
                        String[] split = topic.split("/");
                        if (msgStr.contains("\"get\": \"state\"")) {

                            mess = "{\"data\":{\"pState\":\"off\"},\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"MIOT_r\",\"deviceType\":\"vAssistant\"}";
                        } else if (msgStr.contains("set")) {
                            mess = "{\"data\":{\"pState\":\"on\"},\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"MIOT_r\",\"deviceType\":\"vAssistant\"}";
                        }
                        var messageId = split[split.length - 1];
                        var pubTopic = "/sys/" + productKey + "/" + deviceName + "/rrpc/response/" + messageId;
                        log.info(chineseName + " pubTopic:" + pubTopic);
                        log.info(chineseName + " message:" + mess);
                        mqttClient.publish(pubTopic, mess.getBytes(), 1, false);
                    } else {
                        log.info(chineseName + " message:" + mess);
                        mqttClient.publish("/" + productKey + "/" + deviceName + "/s", mess.getBytes(), 1, false);
                    }

                } else {
                    // {"fromDevice":"ServerSender","data":{"get":"state","from":"MIOT","messageId":"6313979217003ea0"}}
                    if (msgStr.contains("fromDevice")) {
                        JSONObject parse = (JSONObject) JSON.parse(msgStr);
                        JSONObject data = (JSONObject) parse.get("data");
                        String fromDevice = parse.getString("fromDevice");
                        if ("ServerReceiver".equals(fromDevice)) {
                            mess = "{\"deviceType\":\"OwnApp\",\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"" + uuid + "\",\"data\":{\"state\":\"online\",\"timer\":\"000\",\"version\":\"0.1.0\"}}";
                        } else {
                            if (msgStr.contains("\"get\":\"state\"")) {
                                mess = "{\"data\":{\"pState\":\"off\",\"messageId\":\"" + data.getString("messageId") + "\"},\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"ServerReceiver\",\"deviceType\":\"vAssistant\"}";
                            } else if (msgStr.contains("set")) {

                                var acController = SpringUtil.getBean(AcController.class);


                                // 开
                                if (msgStr.contains("true")) {
                                    mess = "{\"data\":{\"pState\":\"on\",\"messageId\":\"" + data.getString("messageId") + "\"},\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"ServerReceiver\",\"deviceType\":\"vAssistant\"}";
                                    acController.togglePower(chineseName, true);
                                }
                                // 关
                                else if (msgStr.contains("false")) {
                                    mess = "{\"data\":{\"pState\":\"off\",\"messageId\":\"" + data.getString("messageId") + "\"},\"fromDevice\":\"" + deviceName + "\",\"toDevice\":\"ServerReceiver\",\"deviceType\":\"vAssistant\"}";
                                    acController.togglePower(chineseName, false);
                                }
                            }
                        }
                        log.info(chineseName + " message:" + mess);
                        mqttClient.publish("/device/" + deviceName + "/s", mess.getBytes(), 1, false);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.info(chineseName + " 消息发送成功! " + ((token == null || token.getResponse() == null) ? "null"
                        : token.getResponse().getKey()));
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                try {
                    mqttClient.subscribe(new String[]{"/device/" + deviceName + "/r", "/device/ServerSender/r", "/sys/" + productKey + "/" + deviceName + "/rrpc/request/+"});
                } catch (MqttException e) {
                    e.printStackTrace();
                }

//                Request request1 = new Request.Builder().url("https://iot.diandeng.tech/api/v1/user/device/voice_assistant")
//                        .addHeader("Content-Type", "application/json")
//                        .post(RequestUtils.INSTANCE.createBody("{\"token\":\"" + iotToken + "\",\"miType\":\"outlet\"}"))
//                        .build();

                HttpRequest request = HttpRequest.newBuilder(URI.create("https://iot.diandeng.tech/api/v1/user/device/voice_assistant"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString("{\"token\":\"" + iotToken + "\",\"miType\":\"outlet\"}", Charset.defaultCharset()))
                        .build();
                HttpResponse<String> response = null;
                try {
                    response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String s = response.body();

                log.info(chineseName + " 设备注册结果:" + s);

                try {

                    HttpRequest request3 = HttpRequest.newBuilder(URI.create("https://iot.diandeng.tech/api/v1/user/device/heartbeat?deviceName=" + deviceName + "&key=" + authKey + "&heartbeat=600"))
                            .GET()
                            .build();
                    HttpResponse<String> response3 = HttpClient.newBuilder().build().send(request3, HttpResponse.BodyHandlers.ofString());
                    s = response3.body();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info(chineseName + " 心跳结果:" + s);
            }
        });
        mqttClient.connect(options);
    }

    public void aliMqtt() {
//        LinkKitInitParams params = new LinkKitInitParams();
///**
// * 设置MQTT初始化参数
// */
//        IoTMqttClientConfig config = new IoTMqttClientConfig();
//        config.productKey = productKey;
//        config.deviceName = deviceName;
//        config.deviceSecret = ds;
//        /*
//         *是否接受离线消息
//         *对应MQTT的cleanSession字段
//         */
//        config.receiveOfflineMsg = false;
//        params.mqttClientConfig = config;
///**
// *设置初始化设备认证信息
// */
//        DeviceInfo deviceInfo = new DeviceInfo();
//        deviceInfo.productKey = productKey;
//        deviceInfo.deviceName = deviceName;
//        deviceInfo.deviceSecret = ds;
//        params.deviceInfo = deviceInfo;
//
///**
// *设置设备当前的初始状态值，属性需要和物联网平台创建的物模型属性一致
// *如果此处值为空，物模型就无当前设备相关属性的初始值。
// *调用物模型上报接口后，物模型会有相关数据缓存。
// */
//        Map propertyValues = new HashMap(); // 示例// propertyValues.put(“LightSwitch”, new ValueWrapper.BooleanValueWrapper(0));params.propertyValues = propertyValues;
//        LinkKit.getInstance().init(params, new ILinkKitConnectListener() {
//
//
//            @Override
//            public void onError(com.aliyun.alink.linksdk.tools.AError aError) {
//                ALog.e("TAG", "" + aError);
//            }
//
//            @Override
//            public void onInitDone(InitResult initResult) {
//                ALog.i("TAG", "onInitDone result=" + initResult);
//            }
//        });
    }

//    private Response doExecute(Request request) {
//        Call call = httpClient.newCall(request);
//        try {
//            Response resp = call.execute();
//            return resp;
//        } catch (IOException e) {
//            log.error(chineseName + " 第三方请求失败，Body: {}", e);
//            e.printStackTrace();
//        }
//        return null;
//    }

}