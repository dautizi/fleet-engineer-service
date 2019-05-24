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
public class FleetResponse {

    @JsonProperty(value = "fleet_engineers")
    private int fleetEngineers;
}
