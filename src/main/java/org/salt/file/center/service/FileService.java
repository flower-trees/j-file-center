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

package org.salt.file.center.service;

import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.vo.FileInfoVo;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface FileService {

    FileInfoVo upload(FileInfoDto fileInfoDto);
    FileInfoVo loadFileContent(FileInfoDto fileInfoDto);
    ResponseEntity<ByteArrayResource> download(FileInfoDto fileInfoDto);

    FileInfoVo loadFile(Long fileId);
}