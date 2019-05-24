package com.danieleautizi.engineer.fleet.service.validator;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.when;

import com.danieleautizi.engineer.fleet.service.configuration.FleetProperties;
import com.danieleautizi.engineer.fleet.service.exception.BadRequestException;
import com.danieleautizi.engineer.fleet.service.model.FleetRequest;

import lombok.val;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class FleetRequestValidatorTest {

    /* Test Data */
    // District
    private static final int DISTRICT_MINIMUM_NUMBER = 1;
    private static final int DISTRICT_MAXIMUM_NUMBER = 100;

    // Scooter
    private static final int SCOOTER_MINIMUM_PER_DISTRICT = 0;
    private static final int SCOOTER_MAXIMUM_PER_DISTRICT = 1000;

    // Manager
    private static final int MANAGER_MINIMUM_SCOOTERS = 1;
    private static final int MANAGER_MAXIMUM_SCOOTERS = 999;

    // Engineer
    private static final int ENGINEER_MINIMUM_SCOOTERS = 1;
    private static final int ENGINEER_MAXIMUM_SCOOTERS = 1000;


    @Mock
    private MessageSource messageSource;

    private FleetRequestValidator fleetRequestValidator;

    @Before
    public void setup() {

        when(messageSource.getMessage(anyString(), anyVararg(), any(), any())).thenAnswer(returnsFirstArg());

        val district = new FleetProperties.District();
        district.setMinimumNumber(DISTRICT_MINIMUM_NUMBER);
        district.setMaximumNumber(DISTRICT_MAXIMUM_NUMBER);

        val scooter = new FleetProperties.Scooter();
        scooter.setMinimumPerDistrict(SCOOTER_MINIMUM_PER_DISTRICT);
        scooter.setMaximumPerDistrict(SCOOTER_MAXIMUM_PER_DISTRICT);

        val manager = new FleetProperties.Manager();
        manager.setMinimumScooters(MANAGER_MINIMUM_SCOOTERS);
        manager.setMaximumScooters(MANAGER_MAXIMUM_SCOOTERS);

        val engineer = new FleetProperties.Engineer();
        engineer.setMinimumScooters(ENGINEER_MINIMUM_SCOOTERS);
        engineer.setMaximumScooters(ENGINEER_MAXIMUM_SCOOTERS);

        val fleetProperties = new FleetProperties();
        fleetProperties.setDistrict(district);
        fleetProperties.setScooter(scooter);
        fleetProperties.setManager(manager);
        fleetProperties.setEngineer(engineer);

        fleetRequestValidator = new FleetRequestValidator(fleetProperties, messageSource);
    }

    @Test
    public void validate_success() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    /* District [begin] */
    @Test
    public void validate_success_districtsMatchLowerLimit() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {10})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test
    public void validate_success_districtsMatchUpperLimit() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new Random().ints(DISTRICT_MAXIMUM_NUMBER,
                                                                              SCOOTER_MINIMUM_PER_DISTRICT,
                                                                              SCOOTER_MAXIMUM_PER_DISTRICT)
                                                                        .toArray())
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_districtsLessThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_districtsMoreThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new Random().ints(DISTRICT_MAXIMUM_NUMBER + 1,
                                                                              SCOOTER_MINIMUM_PER_DISTRICT,
                                                                              SCOOTER_MAXIMUM_PER_DISTRICT)
                                                                        .toArray())
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }
    /* District [end] */


    /* Scooter [begin] */
    @Test
    public void validate_success_scootersPerDistrictMatchLowerLimit() {

        val fleetRequest = FleetRequest.builder()
                                             .scootersPerDistrict(new int[] {0, 15, 30})
                                             .scootersPerManager(12)
                                             .scootersPerEngineer(5)
                                             .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_scootersPerDistrictLessThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {-1, 15, 30})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_scootersPerDistrictMoreThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {SCOOTER_MAXIMUM_PER_DISTRICT + 1, 15, 30})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }
    /* Scooter [end] */


    /* Manager [begin] */
    @Test
    public void validate_success_scootersPerManagerMatchLowerLimit() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(MANAGER_MINIMUM_SCOOTERS)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test
    public void validate_success_scootersPerManagerMatchUpperLimit() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(MANAGER_MAXIMUM_SCOOTERS)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_scootersPerManagerLessThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(MANAGER_MINIMUM_SCOOTERS - 1)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_scootersPerManagerMoreThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(MANAGER_MAXIMUM_SCOOTERS + 1)
                                       .scootersPerEngineer(5)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }
    /* Manager [end] */


    /* Engineer [begin] */
    @Test
    public void validate_success_scootersPerEngineerMatchLowerLimit() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(ENGINEER_MINIMUM_SCOOTERS)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test
    public void validate_success_scootersPerEngineerMatchUpperLimit() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(ENGINEER_MAXIMUM_SCOOTERS)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_scootersPerEngineerLessThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(ENGINEER_MINIMUM_SCOOTERS - 1)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }

    @Test(expected = BadRequestException.class)
    public void validate_failure_scootersPerEngineerMoreThanRequired() {

        val fleetRequest = FleetRequest.builder()
                                       .scootersPerDistrict(new int[] {15, 10})
                                       .scootersPerManager(12)
                                       .scootersPerEngineer(ENGINEER_MAXIMUM_SCOOTERS + 1)
                                       .build();

        fleetRequestValidator.validate(fleetRequest);
    }
    /* Engineer [end] */
}
