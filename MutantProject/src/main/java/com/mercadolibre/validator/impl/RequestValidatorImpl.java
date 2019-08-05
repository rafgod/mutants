package com.mercadolibre.validator.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mercadolibre.DTO.BadRequestResponse;
import com.mercadolibre.DTO.MutantRequestDTO;
import com.mercadolibre.validator.RequestValidator;

@Component
public class RequestValidatorImpl implements RequestValidator {

    private static final Logger LOG = LoggerFactory.getLogger(RequestValidatorImpl.class);

    @Override
    public List<BadRequestResponse> validateMutantRequest(MutantRequestDTO request) {
	List<BadRequestResponse> response = new ArrayList<BadRequestResponse>();
	BadRequestResponse badRequestResponse = new BadRequestResponse();
	if (request.getDna() == null) {
	    badRequestResponse.setName("DNA");
	    badRequestResponse.setDescription("DNA sequence must not be empty");
	    response.add(badRequestResponse);
	    LOG.error("DNA sequence must not be empty");
	} else if (request.getDna().length == 0) {
	    badRequestResponse.setName("DNA");
	    badRequestResponse.setDescription("DNA sequence must not be empty");
	    response.add(badRequestResponse);
	    LOG.error("DNA sequence must not be empty");
	} else {

	    String dnaString = Arrays.toString(request.getDna());
	    dnaString = dnaString.replace(", ", "").replace("[", "").replace("]", "");
	    Pattern pattern = Pattern.compile("[ATCG]+", Pattern.CASE_INSENSITIVE);
	    if (!pattern.matcher(dnaString.toUpperCase()).matches()) {
		badRequestResponse.setName("DNA");
		badRequestResponse.setDescription("DNA can only contains this characters ATCG");
		response.add(badRequestResponse);
		LOG.error("DNA can only contains this characters ATCG");
	    } else {
		int len = request.getDna().length;
		for (String dna : request.getDna()) {
		    if (dna.length() != len) {
			badRequestResponse = new BadRequestResponse();
			badRequestResponse.setName("DNA");
			badRequestResponse.setDescription("The DNA sequence is not formulated correctly");
			response.add(badRequestResponse);
			LOG.error("The DNA sequence is not formulated correctly");
			break;
		    }
		}
	    }
	}

	return response;
    }

}
