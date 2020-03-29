package com.trucku.driverreporter.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Location {

    protected BigDecimal latitude;
    protected BigDecimal longitude;
    
}