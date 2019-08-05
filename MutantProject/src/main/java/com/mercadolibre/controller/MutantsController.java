package com.mercadolibre.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.DTO.BadRequestResponse;
import com.mercadolibre.DTO.MutantRequestDTO;
import com.mercadolibre.service.MutantsService;
import com.mercadolibre.validator.RequestValidator;

@RestController
public class MutantsController {

    private static final Logger LOG = LoggerFactory.getLogger(MutantsController.class);

    @Autowired
    private MutantsService mutantsService;

    @Autowired
    private RequestValidator requestValidator;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST)
    public ResponseEntity<Void> mutants(@RequestBody MutantRequestDTO dnaSequence) {

	try {
	    List<BadRequestResponse> errorList = requestValidator.validateMutantRequest(dnaSequence);
	    if (errorList.isEmpty()) {
		return new ResponseEntity<Void>(mutantsService.isMutant(dnaSequence.getDna()) ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	    } else {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	    }
	} catch (Exception e) {
	    LOG.error("Internal Server Error: {}", e.getMessage());
	    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

}
