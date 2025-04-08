package org.salt.file.center.frame.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    Integer code;

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
