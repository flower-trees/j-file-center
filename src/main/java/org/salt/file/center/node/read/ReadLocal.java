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

package org.salt.file.center.node.read;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.frame.enums.ErrorCode;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@NodeIdentity
public class ReadLocal extends FlowNode<FileInfoDto, FileInfoDto> {

    @Override
    public FileInfoDto process(FileInfoDto fileInfoDto) {
        try {

            String fileUrl = fileInfoDto.getUrl();
            if (StringUtils.isEmpty(fileUrl)) {
                log.warn("File URL is null or empty");
                throw new ServiceException(ErrorCode.FILE_READ_ERROR.getCode(), "File URL is null or empty");
            }

            Path filePath = Paths.get(fileUrl);

            if (!Files.exists(filePath)) {
                log.warn("File does not exist: {}", fileUrl);
                throw new ServiceException(ErrorCode.FILE_READ_ERROR.getCode(), "File does not exist");
            }

            // 读取文件内容
            byte[] fileContent = Files.readAllBytes(filePath);
            fileInfoDto.setFileContent(fileContent);
        } catch (IOException e) {
            log.error("File read failed: {}", e.getMessage());
            fileInfoDto.setException(new ServiceException(ErrorCode.FILE_READ_ERROR.getCode(), "File read failed"));
        }
        return fileInfoDto;
    }
}
