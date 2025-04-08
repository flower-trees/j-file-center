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

package org.salt.file.center.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class FileInfo {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // file name
    private String name;

    // file size
    private Long size;

    // file version
    private Integer version;

    // file sha1
    private String sha1;

    // file content type: image/jpeg video/mp4 audio/mp3 application/pdf ...
    private String contentType;

    // file status: 0-updating 1-updating-failed 2-updated
    private Integer fileStatus;

    // user id
    private Long userId;

    // create time
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    // update id
    private Long updateId;

    // update time
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    // delete flag: 0-not-delete 1-delete
    private Integer deleteFlag;

    // file url
    private String url;

    // file source: 0-common
    private Integer source;
}

