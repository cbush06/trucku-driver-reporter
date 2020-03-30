package com.trucku.driverreporter.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DriverLocation extends Location implements Serializable {

    private static final long serialVersionUID = -4996761407621462648L;

    public DriverLocation(Location location) {
        this.setLatitude(location.getLatitude());
        this.setLongitude(location.getLongitude());
    }

    private String driver;
}