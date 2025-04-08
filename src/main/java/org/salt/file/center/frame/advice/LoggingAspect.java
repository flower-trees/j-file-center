package org.salt.file.center.frame.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.salt.file.center.frame.enums.TheadLocalConstants;
import org.salt.file.center.frame.utils.ThreadUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* org.salt.file.center..*.*(..))")
    public void addTraceId(JoinPoint joinPoint) {
        setLogParam();
    }

    public static void setLogParam() {
        String traceId = ThreadUtil.get(TheadLocalConstants.TRACE_ID.getCode());
        MDC.put("traceId", traceId);
    }
}
