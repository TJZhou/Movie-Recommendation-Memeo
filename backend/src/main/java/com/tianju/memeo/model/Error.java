package com.tianju.memeo.model;

import java.io.Serializable;

public class Error implements Serializable {
    String errorType;
    String errorMessages;

    public Error(String errorType, String errorMessages) {
        this.errorType = errorType;
        this.errorMessages = errorMessages;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }
}
