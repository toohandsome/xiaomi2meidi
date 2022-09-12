package com.yxd.xiaomi2meidi.aop;

import com.yxd.xiaomi2meidi.cache.Gcache;
import com.yxd.xiaomi2meidi.controller.AcController;
import com.yxd.xiaomi2meidi.start.AppStart;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 */
@Aspect
@Component
@Slf4j
public class TokenAspect {

    @Autowired
    AcController acController;

    @Autowired
    AppStart appStart;

    @Pointcut("@annotation(com.yxd.xiaomi2meidi.anotation.TokenCheck)")
    public void pointCut() {
    }


    @Before("pointCut()")
    public void around(JoinPoint joinPoint) {
        if (!StringUtils.hasText(Gcache.config.getAccessToken())) {
            if (StringUtils.hasText(Gcache.config.getPhone()) && StringUtils.hasText(Gcache.config.getPassword()) && StringUtils.hasText(Gcache.config.getAcNameList()) && StringUtils.hasText(Gcache.config.getBlinkerKeyList())) {
                acController.login();
            } else {
                appStart.run(new String[]{});

            }

        }
    }
}
