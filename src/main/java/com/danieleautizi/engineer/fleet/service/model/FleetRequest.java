package com.danieleautizi.engineer.fleet.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleetRequest {

    @JsonProperty(value = "scooters")
    private int[] scootersPerDistrict;

    @JsonProperty(value = "C")
    private int scootersPerManager;

    @JsonProperty(value = "P")
    private int scootersPerEngineer;
}
