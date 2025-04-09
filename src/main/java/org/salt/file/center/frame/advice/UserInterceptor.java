package org.salt.file.center.frame.advice;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.salt.file.center.frame.enums.TheadLocalConstants;
import org.salt.file.center.frame.utils.JsonUtil;
import org.salt.file.center.frame.utils.JwtUtil;
import org.salt.file.center.frame.utils.ThreadUtil;
import org.salt.file.center.frame.vo.Rsp;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        String token = request.getHeader("Authorization");

        log.info("req: {}, header: token:{}", request.getRequestURI(), request.getHeader("Authorization"));

        if (StringUtils.isEmpty(token)) {
            nologin(response);
            return false;
        }

        String jwtToken = token.substring(7);
        try {
            String userJson = JwtUtil.parseToken(jwtToken);
            if (userJson == null) {
                nologin(response);
                return false;
            }
            ThreadUtil.put(TheadLocalConstants.USER_KEY.getCode(), JsonUtil.fromJson(userJson, UserData.class));
        } catch (Exception e) {
            log.error("Failed to parse JWT token", e);
            nologin(response);
            return false;
        }

        return true;
    }

    private void nologin(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(Rsp.builder().code(401).msg("login fail").build())));
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable Exception ex) {
        ThreadUtil.clean();
    }

    @Data
    public static class UserData {

        @JsonProperty("user_id")
        private Long userId;
    }
}
