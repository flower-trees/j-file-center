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

package org.salt.file.center.node;

import lombok.extern.slf4j.Slf4j;
import org.salt.file.center.dto.FileInfoDto;
import org.salt.file.center.frame.utils.ConvertUtil;
import org.salt.file.center.vo.FileInfoVo;
import org.salt.function.flow.node.FlowNode;
import org.salt.function.flow.node.register.NodeIdentity;

@Slf4j
@NodeIdentity
public class FileResult extends FlowNode<FileInfoVo, FileInfoDto> {
    @Override
    public FileInfoVo process(FileInfoDto fileInfoDto) {
        if (fileInfoDto.getException() != null) {
            throw fileInfoDto.getException();
        }
        return ConvertUtil.convert(fileInfoDto, FileInfoVo.class);
    }
}