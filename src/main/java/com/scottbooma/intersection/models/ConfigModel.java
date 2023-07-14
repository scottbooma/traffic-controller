package com.scottbooma.intersection.models;

import lombok.Data;

@Data
public class ConfigModel {

    private Integer greenTiming;

    private Integer yellowTiming;

    private Integer redTiming;

    public ConfigModel (Integer greenTiming, Integer yellowTiming, Integer redTiming) {
        this.greenTiming = greenTiming;
        this.yellowTiming = yellowTiming;
        this.redTiming = redTiming;
    }
}
