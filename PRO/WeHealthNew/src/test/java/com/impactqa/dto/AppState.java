package com.impactqa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppState {

    @JsonProperty("high-exposure")
    private String highExposure;
    @JsonProperty("low-exposure")
    private String lowExposure;
    @JsonProperty("no-exposure")
    private String noExposure;
    @JsonProperty("verified-positive")
    private String verifiedPositive;


    public String getHighExposure() {
        return highExposure;
    }


    public void setHighExposure(String highExposure) {
        this.highExposure = highExposure;
    }

    public String getLowExposure() {
        return lowExposure;
    }

    public void setLowExposure(String lowExposure) {
        this.lowExposure = lowExposure;
    }

    public String getNoExposure() {
        return noExposure;
    }

    public void setNoExposure(String noExposure) {
        this.noExposure = noExposure;
    }

    public void setVerifiedPositive(String verifiedPositive) {
        this.verifiedPositive = verifiedPositive;
    }
}
