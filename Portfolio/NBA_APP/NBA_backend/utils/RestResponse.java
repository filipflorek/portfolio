package com.florek.NBA_backend.utils;

import org.springframework.http.HttpStatus;

public class RestResponse<T> {
    private HttpStatus status;
    private String message;
    private T result;

    public RestResponse(HttpStatus status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public RestResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
