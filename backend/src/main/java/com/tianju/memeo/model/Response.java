package com.tianju.memeo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class Response<T> implements Serializable {
    private Integer code;
    private String msg;
    private String timestamp;
    private Collection<T> data;

    public Response() {
        this.code = 2000;
        this.msg = "success";
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.data = new ArrayList<>();
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.data = new ArrayList<>();
    }

    // successful response - add an object
    public Response(T t) {
        this();
        data.add(t);
    }

    // successful response - add objects
    public Response(Collection<T> t) {
        this();
        data.addAll(t);
    }

    // add a certain object with code and message
    public Response(Integer code,  String msg, T t) {
        this(code, msg);
        data.add(t);
    }

    // add objects with code and message
    public Response(Integer code,  String msg, Collection<T> t) {
        this(code, msg);
        data.addAll(t);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }
}
