package com.yxd.xiaomi2meidi.entity.contrl;

import com.alibaba.fastjson2.annotation.JSONField;

public class Command2control {

    @JSONField(ordinal = 1)
    private String power = "on";
    @JSONField(ordinal = 2)
    private int temperature = 27;
    @JSONField(ordinal = 3)
    private float small_temperature = 0.5f;
    @JSONField(ordinal = 4)
    private String mode = "cool";
    @JSONField(ordinal = 5)
    private String wind_swing_lr = "off";
    @JSONField(ordinal = 6)
    private String wind_swing_lr_under = "off";
    @JSONField(ordinal = 7)
    private String wind_swing_ud = "off";
    @JSONField(ordinal = 8)
    private int wind_speed = 102;
    @JSONField(ordinal = 9)
    private String comfort_sleep = "off";
    @JSONField(ordinal = 10)
    private String buzzer = "on";

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public float getSmall_temperature() {
        return small_temperature;
    }

    public void setSmall_temperature(float small_temperature) {
        this.small_temperature = small_temperature;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWind_swing_lr() {
        return wind_swing_lr;
    }

    public void setWind_swing_lr(String wind_swing_lr) {
        this.wind_swing_lr = wind_swing_lr;
    }

    public String getWind_swing_lr_under() {
        return wind_swing_lr_under;
    }

    public void setWind_swing_lr_under(String wind_swing_lr_under) {
        this.wind_swing_lr_under = wind_swing_lr_under;
    }

    public String getWind_swing_ud() {
        return wind_swing_ud;
    }

    public void setWind_swing_ud(String wind_swing_ud) {
        this.wind_swing_ud = wind_swing_ud;
    }

    public int getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(int wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getComfort_sleep() {
        return comfort_sleep;
    }

    public void setComfort_sleep(String comfort_sleep) {
        this.comfort_sleep = comfort_sleep;
    }

    public String getBuzzer() {
        return buzzer;
    }

    public void setBuzzer(String buzzer) {
        this.buzzer = buzzer;
    }
}
