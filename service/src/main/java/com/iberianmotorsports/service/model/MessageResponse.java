package com.iberianmotorsports.service.model;

import java.io.Serializable;

public class MessageResponse implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
