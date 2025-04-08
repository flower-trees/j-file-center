package org.salt.file.center.frame.advice;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.salt.file.center.frame.enums.TheadLocalConstants;
import org.salt.file.center.frame.utils.JsonUtil;
import org.salt.file.center.frame.utils.ThreadUtil;
import org.salt.file.center.frame.vo.Rsp;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;
import java.util.Collection;

@Slf4j
@RestControllerAdvice
public class RspBodyAdvice implements ResponseBodyAdvice<Object>, RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o != null && (
                o.getClass().getName().matches("com\\.salt\\..*\\.dto\\..*")
                || o.getClass().getName().matches("com\\.salt\\..*\\.vo\\..*")
                || o.getClass().getName().matches("com\\.salt\\..*\\.param\\..*")
                || o instanceof Collection)) {

            if (o instanceof Rsp) {
                return o;
            }
            return Rsp.builder().code(0).msg("success").data(o).build();
        }
        if (o == null) {
            return Rsp.builder().code(0).msg("success").build();
        }
        return o;
    }

    @Override
    public boolean supports(@NotNull MethodParameter methodParameter, @NotNull Type type, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @NotNull
    @Override
    public HttpInputMessage beforeBodyRead(@NotNull HttpInputMessage httpInputMessage, @NotNull MethodParameter methodParameter, @NotNull Type type, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        return httpInputMessage;
    }

    @NotNull
    @Override
    public Object afterBodyRead(@NotNull Object o, @NotNull HttpInputMessage httpInputMessage, @NotNull MethodParameter methodParameter, @NotNull Type type, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        log.info("Req url:{}, param:{}", ThreadUtil.get(TheadLocalConstants.TRACE_ID.getCode()), JsonUtil.toJson(o));
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, @NotNull HttpInputMessage httpInputMessage, @NotNull MethodParameter methodParameter, @NotNull Type type, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
