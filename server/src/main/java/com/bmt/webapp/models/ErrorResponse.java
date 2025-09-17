package com.bmt.webapp.models;

public class ErrorResponse {
    private String error;
    private String field;

    public ErrorResponse() {}

    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse(String error, String field) {
        this.error = error;
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
