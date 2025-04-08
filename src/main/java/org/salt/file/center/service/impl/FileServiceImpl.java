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

package org.salt.file.center.service.impl;

import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.node.db.FileLoad;
import org.salt.file.center.node.db.FileStatusChange;
import org.salt.file.center.node.db.FileUploadCreate;
import org.salt.file.center.node.read.DownloadResult;
import org.salt.file.center.node.read.ReadLocal;
import org.salt.file.center.node.upload.UploadParamChecker;
import org.salt.file.center.node.FileResult;
import org.salt.file.center.node.upload.UploadSaveLocal;
import org.salt.file.center.service.FileService;
import org.salt.file.center.vo.FileInfoVo;
import org.salt.function.flow.FlowEngine;
import org.salt.function.flow.FlowInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FlowEngine flowEngine;

    // file deal
    @Override
    public FileInfoVo upload(FileInfoDto fileInfoDto) {

        FlowInstance flowInstance =
                flowEngine.builder()
                        .next(UploadParamChecker.class)
                        .next(FileUploadCreate.class)
                        .next(UploadSaveLocal.class)
                        .next(FileStatusChange.class)
                        .next(FileResult.class)
                        .build();

        return flowEngine.execute(flowInstance, fileInfoDto);
    }

    @Override
    public FileInfoVo loadFileContent(FileInfoDto fileInfoDto) {

        FlowInstance flowInstance =
                flowEngine.builder()
                        .next(FileLoad.class)
                        .next(ReadLocal.class)
                        .next(FileResult.class)
                        .build();

        return flowEngine.execute(flowInstance, fileInfoDto);
    }

    @Override
    public ResponseEntity<ByteArrayResource> download(FileInfoDto fileInfoDto) {

        FlowInstance flowInstance =
                flowEngine.builder()
                        .next(FileLoad.class)
                        .next(ReadLocal.class)
                        .next(DownloadResult.class)
                        .build();

        return flowEngine.execute(flowInstance, fileInfoDto);
    }


    // DB
    @Override
    public FileInfoVo loadFile(Long fileId) {

        FlowInstance flowInstance =
                flowEngine.builder()
                        .next(FileLoad.class)
                        .next(FileResult.class)
                        .build();

        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setId(fileId);
        return flowEngine.execute(flowInstance, fileInfoDto);
    }
}