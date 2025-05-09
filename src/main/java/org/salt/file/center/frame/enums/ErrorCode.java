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

package org.salt.file.center.frame.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(0),
    FILE_PARAM_ERROR(10001),
    FILE_UPLOAD_ERROR(10002),
    FILE_READ_ERROR(10003);

    private Integer code;

    ErrorCode(Integer code) {
        this.code = code;
    }
}
