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
import org.salt.file.center.frame.enums.ErrorCode;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.file.center.frame.utils.ConvertUtil;
import org.salt.file.center.mapper.FileInfoMapper;
import org.salt.file.center.model.FileInfo;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@NodeIdentity
public class FileLoad extends FlowNode<FileInfoDto, FileInfoDto> {

    @Autowired
    FileInfoMapper fileInfoMapper;

    @Override
    public FileInfoDto process(FileInfoDto fileInfoDto) {

        if (fileInfoDto == null || fileInfoDto.getId() == null) {
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "File read failed");
        }

        FileInfo fileInfo = fileInfoMapper.selectById(fileInfoDto.getId());

        if (fileInfo == null) {
            throw new ServiceException(ErrorCode.FILE_READ_ERROR.getCode(), "File read failed");
        }

        return ConvertUtil.convert(fileInfo, FileInfoDto.class);
    }
}