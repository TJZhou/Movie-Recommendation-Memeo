package com.tianju.memeo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Log the Http Request
 */

@Aspect
@Component
public class WebLogAspect {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(*  com.tianju.memeo.controller..*.*(..))")
    public void controllerLog(){}

    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        logger.info("##############          URL : " + request.getRequestURL().toString());
        logger.info("##############  HTTP_METHOD : " + request.getMethod());
        logger.info("##############           IP : " + request.getRemoteAddr());
        logger.info("############## REQUEST_BODY : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("############## CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }
}
