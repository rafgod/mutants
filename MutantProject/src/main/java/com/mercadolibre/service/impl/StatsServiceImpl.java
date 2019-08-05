package com.mercadolibre.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.DTO.StatsMutantsResponse;
import com.mercadolibre.model.DNA;
import com.mercadolibre.repositories.DNARepository;
import com.mercadolibre.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatsServiceImpl.class);

    @Autowired
    private DNARepository dnaRepository;

    @Override
    public StatsMutantsResponse getMutantsStats() {
	StatsMutantsResponse res = new StatsMutantsResponse();
	try {
	    List<DNA> mutants = dnaRepository.findAllByIsMutant(true);
	    List<DNA> humans = dnaRepository.findAllByIsMutant(false);
	    res.setCount_human_dna(humans.size());
	    res.setCount_mutant_dna(mutants.size());
	    res.setRatio((res.getCount_human_dna() != 0) ? res.getCount_mutant_dna() / res.getCount_human_dna() : 0.0);
	} catch (Exception e) {
	    LOG.error("Something happend during the query: {}", e.getMessage());
	}

	return res;
    }

}
