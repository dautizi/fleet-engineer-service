package com.danieleautizi.engineer.fleet.service.validator;

import com.danieleautizi.engineer.fleet.service.configuration.FleetProperties;
import com.danieleautizi.engineer.fleet.service.exception.BadRequestException;
import com.danieleautizi.engineer.fleet.service.model.FleetRequest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

/**
 * Helper class to validate {@link FleetRequest}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FleetRequestValidator {

    @NonNull
    private final FleetProperties fleetProperties;

    private final MessageSource messageSource;

    private String getMessage(final String messageKey, final Object... args) {

        return messageSource.getMessage(messageKey, args, messageKey, Locale.ROOT);
    }

    public void validate(final FleetRequest fleetRequest) {

        if (fleetRequest.getScootersPerDistrict() == null
            || fleetRequest.getScootersPerDistrict().length < fleetProperties.getDistrict()
                                                                             .getMinimumNumber()
            || fleetRequest.getScootersPerDistrict().length > fleetProperties.getDistrict()
                                                                             .getMaximumNumber()) {

            throw new BadRequestException(getMessage("maintenance.request.validator.district.error.notInRange",
                                                    fleetRequest.getScootersPerDistrict().length,
                                                    fleetProperties.getDistrict()
                                                                   .getMinimumNumber(),
                                                    fleetProperties.getDistrict()
                                                                   .getMaximumNumber()));
        }

        if (Arrays.stream(fleetRequest.getScootersPerDistrict())
                  .anyMatch(scooters -> scooters < fleetProperties.getScooter().getMinimumPerDistrict() ||
                                        scooters > fleetProperties.getScooter().getMaximumPerDistrict())) {

            throw new BadRequestException(getMessage("maintenance.request.validator.scooter.error.notInRange",
                                                     fleetProperties.getScooter()
                                                                    .getMinimumPerDistrict(),
                                                     fleetProperties.getScooter()
                                                                    .getMaximumPerDistrict()));
        }

        if (fleetRequest.getScootersPerManager() < fleetProperties.getManager()
                                                                  .getMinimumScooters()
            || fleetRequest.getScootersPerManager() > fleetProperties.getManager()
                                                                     .getMaximumScooters()) {

            throw new BadRequestException(getMessage("maintenance.request.validator.manager.error.notInRange",
                                                     fleetRequest.getScootersPerManager(),
                                                     fleetProperties.getManager()
                                                                    .getMinimumScooters(),
                                                     fleetProperties.getManager()
                                                                    .getMaximumScooters()));
        }

        if (fleetRequest.getScootersPerEngineer() < fleetProperties.getEngineer()
                                                                   .getMinimumScooters()
            || fleetRequest.getScootersPerEngineer() > fleetProperties.getEngineer()
                                                                      .getMaximumScooters()) {

            throw new BadRequestException(getMessage("maintenance.request.validator.engineer.error.notInRange",
                                                    fleetRequest.getScootersPerEngineer(),
                                                    fleetProperties.getEngineer()
                                                                   .getMinimumScooters(),
                                                    fleetProperties.getEngineer()
                                                                   .getMaximumScooters()));
        }
    }
}
