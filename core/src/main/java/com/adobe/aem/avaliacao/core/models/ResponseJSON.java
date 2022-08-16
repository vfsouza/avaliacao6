package com.adobe.aem.avaliacao.core.models;

public class ResponseJSON {
    int statusCode = 400;
    String msg = "An error has ocurred";

    public ResponseJSON(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public ResponseJSON() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
