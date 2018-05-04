package com.ibc.acamp.suport;

public enum StatusResponse {

    SUCCESS("Success"),
    ERROR("Error");

    StatusResponse(String status){
        this.status = status;
    }

    private String status;
}
