package com.danieleautizi.engineer.fleet.service.controller;

import com.danieleautizi.engineer.fleet.service.manager.FleetManager;
import com.danieleautizi.engineer.fleet.service.model.FleetRequest;
import com.danieleautizi.engineer.fleet.service.model.FleetResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/fleet")
public class FleetController {

    @Autowired
    private FleetManager fleetManager;

    // Considering the provided spec input I preferred to adopt POST instead of GET
    @PostMapping(value = "/engineers")
    public FleetResponse findEngineers(@RequestBody final FleetRequest fleetRequest) {

        LOG.debug("Get the minimum number of fleet engineers.");
        return fleetManager.findOptimizedEngineers(fleetRequest);
    }
}
