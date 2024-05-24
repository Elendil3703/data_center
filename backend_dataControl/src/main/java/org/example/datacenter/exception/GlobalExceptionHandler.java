package org.example.datacenter.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 创建简化的错误信息
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        // 返回响应实体
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    // 这是一个内部类，用于标准化错误响应结构

    public static class ApiError {
        private HttpStatus status;
        private String message;

        public ApiError(HttpStatus status, String message) {
                this.status = status;
                this.message = message;
            }

            // Getters
            public HttpStatus getStatus() {
                return status;
            }

            public String getMessage() {
                return message;
            }

            // Setters
            public void setStatus(HttpStatus status) {
                this.status = status;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
}
