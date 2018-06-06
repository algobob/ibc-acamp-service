package com.ibc.acamp.support;

import com.google.gson.Gson;

public class StandardResponse {
    private StatusResponse status;
    private String message;
    private String data;

    public StandardResponse(StatusResponse status, Object data){
        this(status);
        this.data = buildJson(data);
    }


    public StandardResponse(StatusResponse status){
        this.status = status;
    }

    public StandardResponse(StatusResponse status, String message){
        this(status);
        this.message = message;
    }

    private String buildJson(Object data) {
        return new Gson().toJson(data);
    }
}
