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
import org.salt.file.center.frame.enums.FileStatus;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@NodeIdentity
public class UploadSaveLocal extends FlowNode<FileInfoDto, FileInfoDto> {

    @Value("${file.local.base-path:/tmp/uploads}")
    private String basePath;

    @Override
    public FileInfoDto process(FileInfoDto fileInfoDto) {
        try {
            MultipartFile file = fileInfoDto.getFile();

            String filename = StringUtils.isNotBlank(file.getOriginalFilename()) ?
                    file.getOriginalFilename() : file.getName();
            Path directory = Paths.get(basePath, LocalDate.now().toString());
            Files.createDirectories(directory);

            String uniqueName = filename + "_" + UUID.randomUUID();
            Path targetPath = directory.resolve(uniqueName);

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            fileInfoDto.setUrl(targetPath.toString());
            fileInfoDto.setFileStatus(FileStatus.UPLOADED.getCode());

        } catch (IOException e) {
            log.error("File save failed: {}", e.getMessage());
            fileInfoDto.setFileStatus(FileStatus.UPLOAD_FAIL.getCode());
            fileInfoDto.setException(new ServiceException(ErrorCode.FILE_UPLOAD_ERROR.getCode(), "File save failed"));
        }
        return fileInfoDto;
    }
}
