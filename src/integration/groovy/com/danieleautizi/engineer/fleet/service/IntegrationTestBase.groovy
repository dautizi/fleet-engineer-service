package com.danieleautizi.engineer.fleet.service

import com.danieleautizi.engineer.fleet.service.controller.FleetController
import com.danieleautizi.engineer.fleet.service.manager.FleetManager
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification


@Slf4j(value = "LOG")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = [EngineerFleetServiceApplication])
class IntegrationTestBase extends Specification {

    @LocalServerPort
    private final int serverPort

    @Autowired
    protected final ObjectMapper objectMapper

    @Autowired
    protected final FleetController fleetController
    @Autowired
    protected final FleetManager fleetManager

    @Shared
    def DISTRICT_MINIMUM_NUMBER = 1
    @Shared
    def DISTRICT_MAXIMUM_NUMBER = 100

    @Shared
    def SCOOTER_MINIMUM_PER_DISTRICT = 0
    @Shared
    def SCOOTER_MAXIMUM_PER_DISTRICT = 1000

    @Shared
    def MANAGER_MINIMUM_SCOOTERS = 1
    @Shared
    def MANAGER_MAXIMUM_SCOOTERS = 999

    @Shared
    def ENGINEER_MINIMUM_SCOOTERS = 1
    @Shared
    def ENGINEER_MAXIMUM_SCOOTERS = 1000

    void setup() {

        RestAssured.port = serverPort
    }

}
