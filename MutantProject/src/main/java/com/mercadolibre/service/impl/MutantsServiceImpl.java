package com.mercadolibre.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.enums.MutantSequences;
import com.mercadolibre.model.DNA;
import com.mercadolibre.repositories.DNARepository;
import com.mercadolibre.service.MutantsService;

@Service
public class MutantsServiceImpl implements MutantsService {

    private static final Logger LOG = LoggerFactory.getLogger(MutantsServiceImpl.class);

    private static final int SECUENCES_DNA_LENGTH = 4;

    @Autowired
    private DNARepository dnaRepository;

    @Override
    public boolean isMutant(String[] dna) {
	Boolean isMutant = false;
	String dnaString = Arrays.toString(dna).replace(", ", "").replace("[", "").replace("]", "");
	Optional<DNA> dnaRecord = dnaRepository.findByDnaSequence(dnaString);
	if (dnaRecord.isPresent()) {
	    isMutant = dnaRecord.get().getIsMutant();
	} else {
	    isMutant = detectMutantDNA(dna);
	    DNA newDNARecord = new DNA();
	    newDNARecord.setDnaSequence(dnaString);
	    newDNARecord.setIsMutant(isMutant);
	    dnaRepository.save(newDNARecord);
	}

	return isMutant;
    }

    private static Boolean detectMutantDNA(String[] dnaMutant) {

	List<String> wordList = new ArrayList<>();
	int dnaLen = dnaMutant.length;
	for (int x = 0; x < dnaLen; x++) {
	    if (wordList.size() > 1) {
		return true;
	    }
	    StringBuilder vertical = new StringBuilder();
	    StringBuilder diagonal = new StringBuilder();
	    StringBuilder diagonalLeft = new StringBuilder();
	    if (dnaMutant[x].contains(MutantSequences.A.getSequence())) {
		wordList.add(MutantSequences.A.getSequence());
	    } else if (dnaMutant[x].contains(MutantSequences.T.getSequence())) {
		wordList.add(MutantSequences.T.getSequence());
	    } else if (dnaMutant[x].contains(MutantSequences.G.getSequence())) {
		wordList.add(MutantSequences.G.getSequence());
	    } else if (dnaMutant[x].contains(MutantSequences.C.getSequence())) {
		wordList.add(MutantSequences.C.getSequence());
	    }
	    if (x == 1) {
		for (int dr = 0; dr < dnaLen && dr + 1 < dnaLen; dr++) {
		    if (diagonal.toString().isEmpty() || diagonal.charAt(diagonal.length() - 1) == dnaMutant[dr].charAt(dr + 1)) {
			diagonal.append(dnaMutant[dr].charAt(dr + 1));
		    } else if (diagonal.length() < 4) {
			diagonal = new StringBuilder();
		    }
		}
	    } else if (x >= SECUENCES_DNA_LENGTH - 1) {
		int auxy = 0;
		for (int dl = x; dl >= 0; dl--) {
		    if (diagonalLeft.toString().isEmpty() || diagonalLeft.charAt(diagonalLeft.length() - 1) == dnaMutant[auxy].charAt(dl)) {
			diagonalLeft.append(dnaMutant[auxy].charAt(dl));
		    } else if (diagonalLeft.length() < SECUENCES_DNA_LENGTH) {
			diagonalLeft = new StringBuilder();
		    }
		    if (dl < SECUENCES_DNA_LENGTH && diagonalLeft.toString().isEmpty()) {
			break;
		    }
		    auxy++;
		}
		diagonalLeft = new StringBuilder();
		if (x == dnaLen) {
		    auxy = 1;
		    while (auxy <= dnaLen - SECUENCES_DNA_LENGTH) {
			for (int dl = x, z = auxy; dl >= 0; dl--, z++) {
			    if (diagonalLeft.toString().isEmpty() || diagonalLeft.charAt(diagonalLeft.length() - 1) == dnaMutant[z].charAt(dl)) {
				diagonalLeft.append(dnaMutant[z].charAt(dl));
			    } else if (diagonalLeft.length() < SECUENCES_DNA_LENGTH) {
				diagonalLeft = new StringBuilder();
			    }
			}
			auxy++;
		    }

		}
		if (diagonalLeft.length() >= SECUENCES_DNA_LENGTH) {
		    wordList.add(diagonalLeft.toString());
		}
	    }

	    for (int y = 0; y < dnaLen; y++) {
		if (vertical.toString().isEmpty() || vertical.charAt(vertical.length() - 1) == dnaMutant[y].charAt(x)) {
		    vertical.append(dnaMutant[y].charAt(x));
		} else if (vertical.length() < SECUENCES_DNA_LENGTH) {
		    vertical = new StringBuilder();
		} else {
		    break;
		}

		diagonal = new StringBuilder();
		if (x == 0) {
		    if (diagonal.toString().isEmpty()) {
			diagonal.append(dnaMutant[y].charAt(x));
		    }
		    for (int dr = 1; dr < dnaLen && dr + y < dnaLen; dr++) {
			if (diagonal.toString().isEmpty() || diagonal.charAt(diagonal.length() - 1) == dnaMutant[dr + y].charAt(dr)) {
			    diagonal.append(dnaMutant[dr + y].charAt(dr));
			} else if (diagonal.length() < SECUENCES_DNA_LENGTH) {
			    diagonal = new StringBuilder();
			} else {
			    break;
			}
		    }
		}

		if (diagonal.length() >= SECUENCES_DNA_LENGTH) {
		    wordList.add(diagonal.toString());
		}

		if (wordList.size() > 1) {
		    return true;
		}
		if (vertical.length() >= SECUENCES_DNA_LENGTH) {
		    wordList.add(vertical.toString());
		    break;
		}

	    }

	}

	return wordList.size() > 1;

    }

}
