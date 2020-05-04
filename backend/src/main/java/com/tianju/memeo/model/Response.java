package com.tianju.memeo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class Response implements Serializable {
    private Integer code;
    private String msg;
    private String timestamp;
    private Collection<Object> data;

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

    // successful response - add a certain object
    public Response(Object obj) {
        this();
        data.add(obj);
    }

    // successful response - add objects
    public Response(Collection<Object> obj) {
        this();
        data.addAll(obj);
    }

    // add a certain object with code and message
    public Response(Integer code,  String msg, Object obj) {
        this(code, msg);
        data.add(obj);
    }

    // add objects with code and message
    public Response(Integer code,  String msg, Collection<Object> obj) {
        this(code, msg);
        data.addAll(obj);
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

    public Collection<Object> getData() {
        return data;
    }

    public void setData(Collection<Object> data) {
        this.data = data;
    }
}
