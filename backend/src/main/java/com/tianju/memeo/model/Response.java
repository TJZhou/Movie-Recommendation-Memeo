package com.tianju.memeo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class Response implements Serializable {
    private int code;
    private String msg;
    private String timestamp;
    private Collection<Object> data;

    public Response() {
        this.code = 2000;
        this.msg = "success";
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.data = new ArrayList<>();
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.data = new ArrayList<>();
    }

    // successful response - add dataset of movies
    public Response(Collection<Object> obj) {
        this();
        data.addAll(obj);
    }

    // successful response - add certain object
    public Response(Object obj) {
        this();
        data.add(obj);
    }

    // unsuccessful response - return error message
    public Response(int code, String msg, Collection<Error> errors) {
        this(code, msg);
        data.addAll(errors);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Collection<Object> getData() {
        return data;
    }

    public void setData(Collection<Object> data) {
        this.data = data;
    }
}
