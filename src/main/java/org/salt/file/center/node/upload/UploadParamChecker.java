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

package org.salt.file.center.node.upload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.frame.enums.ErrorCode;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;

@Slf4j
@NodeIdentity
public class UploadParamChecker extends FlowNode<Void, FileInfoDto> {

    @Override
    public Void process(FileInfoDto fileInfoDto) {

        if (fileInfoDto == null || fileInfoDto.getFile() == null) {
            log.warn("param is null");
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "param is null");
        }

        if (fileInfoDto.getFile().getSize() == 0) {
            log.warn("file size is 0");
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "file size is 0");
        }

        if (StringUtils.isEmpty(fileInfoDto.getFile().getOriginalFilename()) && StringUtils.isEmpty(fileInfoDto.getName())) {
            log.warn("file name is null");
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "file name is null");
        }

        if (StringUtils.isEmpty(fileInfoDto.getContentType())) {
            log.warn("file ContentType is null");
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "file ContentType is null");
        }

        return null;
    }
}
