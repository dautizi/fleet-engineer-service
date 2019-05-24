package com.danieleautizi.engineer.fleet.service.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Fleet configuration to set constraints.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "fleet.constraints")
public class FleetProperties {

    private District district = new District();
    private Scooter scooter = new Scooter();
    private Manager manager = new Manager();
    private Engineer engineer = new Engineer();

    @Data
    @NoArgsConstructor
    public static class District {

        private int minimumNumber;
        private int maximumNumber;
    }

    @Data
    @NoArgsConstructor
    public static class Scooter {

        private int minimumPerDistrict;
        private int maximumPerDistrict;
    }

    @Data
    @NoArgsConstructor
    public static class Manager {

        private int minimumScooters;
        private int maximumScooters;
    }

    @Data
    @NoArgsConstructor
    public static class Engineer {

        private int minimumScooters;
        private int maximumScooters;
    }
}
