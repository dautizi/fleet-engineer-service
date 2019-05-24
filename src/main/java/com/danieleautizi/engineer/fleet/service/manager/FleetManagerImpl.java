package com.danieleautizi.engineer.fleet.service.manager;

import com.danieleautizi.engineer.fleet.service.model.FleetRequest;
import com.danieleautizi.engineer.fleet.service.model.FleetResponse;
import com.danieleautizi.engineer.fleet.service.validator.FleetRequestValidator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FleetManagerImpl implements FleetManager {

    @NonNull
    private final FleetRequestValidator fleetRequestValidator;

    @Override
    public FleetResponse findOptimizedEngineers(final FleetRequest fleetRequest) {

        // Validate the request according with constraints
        fleetRequestValidator.validate(fleetRequest);

        int maxEngineerSaved = 0;
        int engineerCounter = 0;

        // Iterate over the districts to work the scooters
        for (int scootersToBeMaintained : fleetRequest.getScootersPerDistrict()) {

            // Calculate how many engineers are needed to maintain the scooters
            int onlyEngineersNeededPerDistrict = ((Double) Math.ceil((double) scootersToBeMaintained/fleetRequest.getScootersPerEngineer())).intValue();
            engineerCounter += onlyEngineersNeededPerDistrict;

            // Get the scooters which are not covered by manager maintenance (0 for negatives)
            int scootersLeftFromManagerMaintenance = Math.max(scootersToBeMaintained - fleetRequest.getScootersPerManager(), 0);

            // Calculate the engineers needed to maintain the scooters remaining from manager
            int engineersToHelpManager = ((Double) Math.ceil((double) scootersLeftFromManagerMaintenance
                                                             / fleetRequest.getScootersPerEngineer())).intValue();

            // Calculate the best engineer discount out of manager maintenance
            int engineersSaved = onlyEngineersNeededPerDistrict - engineersToHelpManager;

            // Save the best discount by manager ever :)
            maxEngineerSaved = Math.max(engineersSaved, maxEngineerSaved);
        }

        return FleetResponse.builder()
                            .fleetEngineers(engineerCounter - maxEngineerSaved)
                            .build();
    }
}