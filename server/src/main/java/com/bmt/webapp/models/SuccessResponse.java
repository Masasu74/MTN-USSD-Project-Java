package com.bmt.webapp.models;

public class SuccessResponse {
    private String message;
    private String status;
    private Object data;

    public SuccessResponse() {}

    public SuccessResponse(String message) {
        this.message = message;
        this.status = "success";
    }

    public SuccessResponse(String message, Object data) {
        this.message = message;
        this.status = "success";
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

