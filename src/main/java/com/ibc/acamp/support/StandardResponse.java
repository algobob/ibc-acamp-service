package com.ibc.acamp.support;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class StandardResponse {
    private StatusResponse status;
    private String message;
    private JsonElement data;

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

    private JsonElement buildJson(Object data) {
        return new Gson().toJsonTree(data);
    }
}
