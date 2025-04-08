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

package org.salt.file.center.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.service.FileService;
import org.salt.file.center.vo.FileInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件处理接口")
@Controller
@RequestMapping("/{version}/files")
@Slf4j
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation("file upload")
    @PostMapping("/upload")
    @ResponseBody
    public FileInfoVo upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "size", required = false) Long size,
            @RequestParam(value = "sha1", required = false) String sha1,
            @RequestParam(value = "contentType", required = false) String contentType,
            @PathVariable String version) {

        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setName(name);
        fileInfoDto.setSize(size);
        fileInfoDto.setSha1(sha1);
        fileInfoDto.setContentType(contentType);
        fileInfoDto.setFile(file);
        fileInfoDto.setApiVersion(version);
        return fileService.upload(fileInfoDto);
    }

    @ApiOperation("file load with content")
    @PostMapping("/load_file_content")
    @ResponseBody
    public FileInfoVo loadFileContent(Long fileId, @PathVariable String version) {
        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setId(fileId);
        fileInfoDto.setApiVersion(version);
        return fileService.loadFileContent(fileInfoDto);
    }

    @ApiOperation("file download")
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> download(@RequestBody FileInfoVo fileInfoVo, @PathVariable String version) {
        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setId(fileInfoVo.getId());
        fileInfoDto.setApiVersion(version);
        return fileService.download(fileInfoDto);
    }
}