package com.impactqa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeHealthResponseDto {

    private Messaging messaging;


    public Messaging getMessaging() {
        return messaging;
    }


    public void setMessaging(Messaging messaging) {
        this.messaging = messaging;
    }


}
