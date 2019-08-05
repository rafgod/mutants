package com.mercadolibre.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.DTO.StatsMutantsResponse;
import com.mercadolibre.service.StatsService;

@RestController
public class StatsController {
    private static final Logger LOG = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private StatsService statsService;

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public ResponseEntity<StatsMutantsResponse> getMutantsStats() {
	StatsMutantsResponse res = statsService.getMutantsStats();
	return new ResponseEntity<StatsMutantsResponse>(res, res != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
