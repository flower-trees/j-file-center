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
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.frame.enums.ErrorCode;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
@NodeIdentity
public class DownloadResult extends FlowNode<ResponseEntity<ByteArrayResource>, FileInfoDto> {

    @Override
    public ResponseEntity<ByteArrayResource> process(FileInfoDto fileInfoDto) {
        if (fileInfoDto.getFileContent() == null) {
            log.warn("File content is null for URL: {}", fileInfoDto.getUrl());
            throw new ServiceException(ErrorCode.FILE_READ_ERROR.getCode(), "File content is null");
        }

        ByteArrayResource resource = new ByteArrayResource(fileInfoDto.getFileContent());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfoDto.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
