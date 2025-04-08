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

package org.salt.file.center.node.db;

import lombok.extern.slf4j.Slf4j;
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.mapper.FileInfoMapper;
import org.salt.file.center.model.FileInfo;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
@NodeIdentity
public class FileStatusChange extends FlowNode<FileInfoDto, FileInfoDto> {

    @Autowired
    FileInfoMapper fileInfoMapper;

    @Override
    public FileInfoDto process(FileInfoDto fileInfoDto) {

        FileInfo fileInfo = new FileInfo();

        fileInfo.setId(fileInfoDto.getId());
        fileInfo.setFileStatus(fileInfoDto.getFileStatus());
        fileInfo.setUpdateId(1L);
        fileInfo.setUpdateTime(new Date());
        fileInfo.setUrl(fileInfoDto.getUrl());

        fileInfoMapper.updateById(fileInfo);

        return fileInfoDto;
    }
}