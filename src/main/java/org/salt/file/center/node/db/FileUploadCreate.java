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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.frame.enums.ErrorCode;
import org.salt.file.center.frame.enums.FileStatus;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.file.center.frame.utils.ConvertUtil;
import org.salt.file.center.mapper.FileInfoMapper;
import org.salt.file.center.model.FileInfo;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Slf4j
@NodeIdentity
public class FileUploadCreate extends FlowNode<FileInfoDto, FileInfoDto> {

    @Autowired
    FileInfoMapper fileInfoMapper;

    @Override
    public FileInfoDto process(FileInfoDto fileInfoDto) {

        // file name
        if (StringUtils.isEmpty(fileInfoDto.getName())) {
            fileInfoDto.setName(fileInfoDto.getFile().getOriginalFilename());
        }

        // file size
        fileInfoDto.setSize(fileInfoDto.getFile().getSize());

        // file version
        fileInfoDto.setVersion(1);

        // file sha1
        String sha1Hex;
        try {
            sha1Hex = DigestUtils.sha1Hex(getInputStream(fileInfoDto.getFile().getInputStream(), fileInfoDto.getFile()));
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "file stream error");
        }
        if (!StringUtils.equals(sha1Hex, fileInfoDto.getSha1())) {
            throw new ServiceException(ErrorCode.FILE_PARAM_ERROR.getCode(), "file sha1 error");
        }
        fileInfoDto.setSha1(sha1Hex);

        fileInfoDto.setFileStatus(FileStatus.UPLOADING.getCode());
        fileInfoDto.setUserId(1L);
        fileInfoDto.setCreateTime(new Date());

        FileInfo fileInfo = ConvertUtil.convert(fileInfoDto, FileInfo.class);

        fileInfoMapper.insert(fileInfo);

        fileInfoDto.setId(fileInfo.getId());

        return fileInfoDto;
    }

    private InputStream getInputStream(InputStream inputStream, MultipartFile file) throws IOException {
        if (inputStream instanceof ByteArrayInputStream) {
            inputStream.reset();
            return inputStream;
        }
        return file.getInputStream();
    }
}