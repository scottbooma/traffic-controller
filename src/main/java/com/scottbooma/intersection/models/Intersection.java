package com.scottbooma.intersection.models;

import com.scottbooma.intersection.enums.Colors;

import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Data
public class Intersection {
    @Id
    public String id;

    private Light lightGroupA;

    private Light lightGroupB;

    private boolean activeStatus;

    private ConfigModel timingConfig;

    public Intersection(Intersection intersection, boolean activeStatus, ConfigModel timingConfig) {
        this.id = intersection.getId();
        this.lightGroupA = intersection.getLightGroupA();
        this.lightGroupB = intersection.getLightGroupB();
        this.activeStatus = activeStatus;

        if (Objects.nonNull(timingConfig)) {
            this.timingConfig = timingConfig;
        }
    }

    public void startLights() {
        if (this.activeStatus) {
            this.lightGroupA.setState(Colors.RED);
            this.lightGroupB.setState(Colors.GREEN);
        }
    }

    public void stopLights() {
        if (!this.activeStatus) {
            this.lightGroupA.setState(null);
            this.lightGroupB.setState(null);
        }
    }
}