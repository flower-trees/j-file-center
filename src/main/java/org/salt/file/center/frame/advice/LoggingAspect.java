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
