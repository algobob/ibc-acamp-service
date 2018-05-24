package com.ibc.acamp.support;

public enum StatusResponse {

    SUCCESS("Success"),
    FAIL("Fail"),
    ERROR("Error");

    StatusResponse(String status){
        this.status = status;
    }

    private String status;
}
