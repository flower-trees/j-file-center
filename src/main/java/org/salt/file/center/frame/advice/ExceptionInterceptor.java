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

import lombok.extern.slf4j.Slf4j;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.file.center.frame.vo.Rsp;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(ServiceException.class)
    public Rsp<?> errorHandler(ServiceException e){
        log.error("system error:", e);
        return Rsp.builder().code(e.getCode()).msg(e.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public Rsp<?> handleException(Exception e) {
        log.error("system error:", e);
        return Rsp.builder().code(500).msg("system error!").build();
    }
}
