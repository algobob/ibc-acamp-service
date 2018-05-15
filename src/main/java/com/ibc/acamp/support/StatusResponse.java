package com.ibc.acamp.support;

public enum StatusResponse {

    SUCCESS("Success"),
    ERROR("Error");

    StatusResponse(String status){
        this.status = status;
    }

    private String status;
}
