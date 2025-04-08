package org.salt.file.center.frame.enums;

import lombok.Getter;

@Getter
public enum FileStatus {

    UPLOADING(1, "uploading"),
    UPLOADED(2, "uploaded"),
    UPLOAD_FAIL(101, "upload fail"),
    ;

    private final Integer code;
    private final String description;

    FileStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
