package com.back.office.model.payload.dto;

public class CommonResponse {
    private String message;
    public CommonResponse(String message) {
        this.message = message;
    }
    public String getResponse() {
        return message;
    }
    public void setResponse(String message) {
        this.message = message;
    }
}
