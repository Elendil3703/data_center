package org.example.datacenter.exception;

import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException {
    private int code;

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
