package com.example.vinyl.controllers;

import io.micrometer.common.lang.Nullable;

public class OpResult {
    @Nullable
    public String error;

    public boolean result;

    public OpResult(boolean result, String error) {
        this.result = result;
        this.error = error;
    }

    public OpResult(boolean result) {
        this.result = result;
    }
}
