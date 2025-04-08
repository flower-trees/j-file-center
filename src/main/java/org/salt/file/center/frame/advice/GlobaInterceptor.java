/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.salt.file.center.frame.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.salt.file.center.frame.enums.TheadLocalConstants;
import org.salt.file.center.frame.utils.JsonUtil;
import org.salt.file.center.frame.utils.SnowUtil;
import org.salt.file.center.frame.utils.ThreadUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class GlobaInterceptor implements HandlerInterceptor {

    public static final String REQUEST_ID = "Request-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        String requestId = request.getHeader(REQUEST_ID);
        if (StringUtils.isBlank(requestId)) {
            requestId = String.valueOf(SnowUtil.next());
        }
        log.info("preHandle, requestId:{}", requestId);
        ThreadUtil.put(TheadLocalConstants.TRACE_ID.getCode(), requestId);
        LoggingAspect.setLogParam();
        response.setHeader(REQUEST_ID, requestId);

        if ("GET".equals(request.getMethod())) {
            log.info("preHandle, Req url:{}, param:{}", request.getRequestURI(), JsonUtil.toJson(request.getParameterMap()));
            ThreadUtil.put(TheadLocalConstants.REQUEST_URL.getCode(), request.getRequestURI());
        } else {
            ThreadUtil.put(TheadLocalConstants.REQUEST_URL.getCode(), request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
    }
}
