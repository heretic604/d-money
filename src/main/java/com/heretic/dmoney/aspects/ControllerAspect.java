package com.heretic.dmoney.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class ControllerAspect {
    private final static String REQUEST_LOG_PATTERN = "{} -> {}; args: {} uri: {}";
    private final static String RESPONSE_LOG_PATTERN = "{} -> {}: {}, response: {}";
    private final static String BLANK = "";


    @Pointcut("execution(* com.heretic.dmoney.controllers..*(..)) && !@annotation(com.heretic.dmoney.aspects.ExcludeLogging)")
    public void getPointCut() {
    }

    @Before("getPointCut()")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(REQUEST_LOG_PATTERN,
                request.getMethod(),
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                request.getRequestURI());
    }

    @AfterReturning(pointcut = "getPointCut()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(RESPONSE_LOG_PATTERN,
                request.getMethod(),
                joinPoint.getSignature().toShortString(),
                request.getRequestURI(),
                Optional.ofNullable(response).orElse(BLANK));
    }
}