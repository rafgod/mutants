package com.mercadolibre.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.helper.MutantsHelper;
import com.mercadolibre.model.DNA;
import com.mercadolibre.repositories.DNARepository;
import com.mercadolibre.service.MutantsService;

@Service
public class MutantsServiceImpl implements MutantsService {

    private static final Logger LOG = LoggerFactory.getLogger(MutantsServiceImpl.class);

    @Autowired
    private MutantsHelper mutantsHelper;

    @Autowired
    private DNARepository dnaRepository;

    @Override
    public boolean isMutant(String[] dna) {
	Boolean isMutant = false;
	String dnaString = Arrays.toString(dna).replace(", ", "").replace("[", "").replace("]", "");
	Optional<DNA> dnaRecord = dnaRepository.findByDnaSequence(dnaString);
	if (dnaRecord.isPresent()) {
	    LOG.info("DNA sequence found in DB");
	    isMutant = dnaRecord.get().getIsMutant();
	} else {
	    isMutant = mutantsHelper.detectMutantDNA(dna);
	    mutantsHelper.saveDNA(isMutant, dnaString);
	}
	return isMutant;
    }

}
