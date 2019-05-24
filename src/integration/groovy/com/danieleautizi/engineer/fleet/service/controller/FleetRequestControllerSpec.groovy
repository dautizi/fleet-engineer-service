package com.danieleautizi.engineer.fleet.service.controller

import com.danieleautizi.engineer.fleet.service.IntegrationTestBase
import com.danieleautizi.engineer.fleet.service.model.FleetRequest
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo

@Unroll
class FleetRequestControllerSpec extends IntegrationTestBase {

    def 'Find optimized engineers when #scenario'() {

        def fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(scootersPerDistrict)
                                       .scootersPerManager(scootersPerManager)
                                       .scootersPerEngineer(scootersPerEngineer)
                                       .build()

        expect:
            given().body(objectMapper.writeValueAsString(fleetRequest))
                   .contentType(ContentType.JSON)
                   .post("/fleet/engineers")
                   .then().log().all()
                   .spec(expectedResponse)

        where:
            //@formatter:off
            scenario            | scootersPerDistrict   | scootersPerManager | scootersPerEngineer | expectedResponse
            "valid request - provided "+
            "example 1"         | (int[])[15, 10]       | 12                 | 5                   |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.OK.value())
                                             .expectBody("fleet_engineers", equalTo(3))
                                             .build()

            "valid request - provided "+
            "example 2"         | (int[])[11, 15, 13]   | 9                  | 5                   |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.OK.value())
                                             .expectBody("fleet_engineers", equalTo(7))
                                             .build()

            "invalid request - less districts "+
            "than required"     | (int[])[]             | 9                  | 5                   |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given 0 districts are not in the acceptable range 1-100"))
                                             .build()

            "invalid request - more districts "+
            "than required"     | new Random().ints(DISTRICT_MAXIMUM_NUMBER + 1,
                                                    SCOOTER_MINIMUM_PER_DISTRICT,
                                                    SCOOTER_MAXIMUM_PER_DISTRICT)
                                              .toArray() | 9                  | 5                   |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given 101 districts are not in the acceptable range 1-100"))
                                             .build()

            "invalid request - less scooters per district "+
            "than required"     | (int[])[-1, 15, 30]    | 9                  | 5                   |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given scooters per district are not in the acceptable range 0-1,000"))
                                             .build()

            "invalid request - more scooters per district "+
            "than required"     | (int[])[SCOOTER_MAXIMUM_PER_DISTRICT + 1] | 9 | 5                 |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given scooters per district are not in the acceptable range 0-1,000"))
                                             .build()

            "invalid request - less scooters per manager "+
            "than required"     | (int[])[99, 15, 30]    | MANAGER_MINIMUM_SCOOTERS - 1 | 5         |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given maintainable scooters per manager (0) are not in the acceptable range 1-999"))
                                             .build()

            "invalid request - more scooters per manager "+
            "than required"     | (int[])[99, 15, 30]    | MANAGER_MAXIMUM_SCOOTERS + 1 | 5         |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given maintainable scooters per manager (1,000) are not in the acceptable range 1-999"))
                                             .build()

            "invalid request - less scooters per engineer "+
            "than required"     | (int[])[99, 15, 30]    | 9                   | ENGINEER_MINIMUM_SCOOTERS - 1 |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given maintainable scooters per engineer (0) are not in the acceptable range 1-1,000"))
                                             .build()

            "invalid request - more scooters per engineer "+
            "than required"     | (int[])[99, 15, 30]    | 9                   | ENGINEER_MAXIMUM_SCOOTERS + 1 |
                    new ResponseSpecBuilder().expectStatusCode(HttpStatus.BAD_REQUEST.value())
                                             .expectBody("message", equalTo("The given maintainable scooters per engineer (1,001) are not in the acceptable range 1-1,000"))
                                             .build()

            //@formatter:on
    }
}
