package com.tianju.memeo.model;

import java.io.Serializable;

public class Error implements Serializable {
    private String errorType;
    private String errorMsg;

    public Error(String errorType, String errorMsg) {
        this.errorType = errorType;
        this.errorMsg = errorMsg;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
