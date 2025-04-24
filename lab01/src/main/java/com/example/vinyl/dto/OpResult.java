package com.example.vinyl.dto;

public class OpResult {
    private int status;
    private String message;

    public OpResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


}
