package org.salt.file.center.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(description = "Document information")
public class FileInfoVo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "Primary key ID")
    private Long id;

    @ApiModelProperty(value = "Document name")
    private String name;

    @ApiModelProperty(value = "Document size in bytes")
    private Long size;

    @ApiModelProperty(value = "Document version number")
    private Integer version;

    @ApiModelProperty(value = "Document checksum using SHA1 algorithm")
    private String sha1;

    @ApiModelProperty(value = "MIME type of the document")
    private String contentType;

    @ApiModelProperty(value = "Document status")
    private Integer fileStatus;

    @ApiModelProperty(value = "Creator user ID")
    private Long userId;

    @ApiModelProperty(value = "Creation time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "Updater user ID")
    private Long updateId;

    @ApiModelProperty(value = "Update time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "Logical delete flag")
    private Integer deleteFlag;

    @ApiModelProperty(value = "Download URL")
    private String url;

    @ApiModelProperty(value = "Source of entry: 0 - default legacy version, 1 - audio/video entry, 2 - multi-url entry")
    private Integer source;
}