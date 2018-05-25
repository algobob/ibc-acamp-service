package com.ibc.acamp.support;

import com.google.gson.Gson;

public class JsonResponseBuilder {

    private String message;
    private StatusResponse status;
    private Object data;

    public JsonResponseBuilder(StatusResponse status){
        this.status = status;
    }

    public JsonResponseBuilder(StatusResponse status, String message){
        this(status);
        this.message = message;
    }

    public JsonResponseBuilder(StatusResponse status, Object data){
        this(status);
        this.data = data;
    }

    public JsonResponseBuilder(StatusResponse status, String message, Object data) {
        this(status, message);
        this.data = data;
    }

    public String build(){
        switch (status) {
            case SUCCESS:
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, data));
            case FAIL:
                return new Gson().toJson(new StandardResponse(StatusResponse.FAIL, message));
            case ERROR:
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, message));
            default:
                return null;
        }
    }
}
