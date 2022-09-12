package com.yxd.xiaomi2meidi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.AotProxyHint;
import org.springframework.nativex.hint.ProxyBits;

/**
 * @author Administrator
 */


//@NativeHint(
//        types = {
//                @TypeHint(types = {ConsoleAppender.class, SizeAndTimeBasedRollingPolicy.class, PatternLayout.class, RollingFileAppender.class, PatternLayoutEncoder.class},
//                        access = {TypeAccess.PUBLIC_CONSTRUCTORS, PUBLIC_METHODS})
//        })
@AotProxyHint(targetClass=com.yxd.xiaomi2meidi.controller.AcController.class, proxyFeatures = ProxyBits.IS_STATIC)
@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
