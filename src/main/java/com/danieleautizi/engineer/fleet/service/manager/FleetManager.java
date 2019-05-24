package com.danieleautizi.engineer.fleet.service.manager;

import com.danieleautizi.engineer.fleet.service.model.FleetRequest;
import com.danieleautizi.engineer.fleet.service.model.FleetResponse;

/**
 * Manager for {@link FleetRequest} operations.
 */
public interface FleetManager {

    /**
     * Find the minimum number of FEs which are required to help
     * the FM so that every scooter in each district of Stockholm is maintained.
     *
     * @param fleetRequest - configuration input to determine how many FE are needed
     * @return {@link FleetResponse}
     */
    FleetResponse findOptimizedEngineers(final FleetRequest fleetRequest);
}
