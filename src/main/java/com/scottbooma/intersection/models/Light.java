package com.scottbooma.intersection.models;

import com.scottbooma.intersection.enums.Colors;

import lombok.Data;

@Data
public class Light {
    private Colors state;

    public Light(Colors state) {
        this.state = state;
    }

}
