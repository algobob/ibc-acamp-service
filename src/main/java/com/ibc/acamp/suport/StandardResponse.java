package com.ibc.acamp.suport;

import com.google.gson.JsonElement;

public class StandardResponse {
    private StatusResponse status;
    private String message;
    private JsonElement data;

    public StandardResponse(StatusResponse status, JsonElement data){
        this.status = status;
        this.data = data;
    }
}
