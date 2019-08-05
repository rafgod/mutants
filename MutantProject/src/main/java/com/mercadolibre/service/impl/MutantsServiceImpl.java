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
	    saveDNA(isMutant, dnaString);
	}
	return isMutant;
    }

    private void saveDNA(Boolean isMutant, String dnaString) {
	DNA newDNARecord = new DNA();
	newDNARecord.setDnaSequence(dnaString);
	newDNARecord.setIsMutant(isMutant);
	dnaRepository.save(newDNARecord);
    }

    private Boolean detectMutantDNA(String[] dnaMutant) {

	List<String> wordList = new ArrayList<>();
	int dnaLen = dnaMutant.length;

	wordList.addAll(checkDiagonalLeftToRight(dnaMutant));
	if (wordList.size() > 1) {
	    return true;
	}
	wordList.addAll(checkDiagonalRightToLeft(dnaMutant));
	if (wordList.size() > 1) {
	    return true;
	}

	for (int x = 0; x < dnaLen; x++) {

	    if (dnaMutant[x].contains(MutantSequences.A.getSequence())) {
		wordList.add(MutantSequences.A.getSequence());
	    } else if (dnaMutant[x].contains(MutantSequences.T.getSequence())) {
		wordList.add(MutantSequences.T.getSequence());
	    } else if (dnaMutant[x].contains(MutantSequences.G.getSequence())) {
		wordList.add(MutantSequences.G.getSequence());
	    } else if (dnaMutant[x].contains(MutantSequences.C.getSequence())) {
		wordList.add(MutantSequences.C.getSequence());
	    }

	    if (wordList.size() > 1) {
		return true;
	    }

	    for (int y = 0; y < dnaLen; y++) {
		StringBuilder vertical = new StringBuilder();
		if (vertical.toString().isEmpty() || vertical.charAt(vertical.length() - 1) == dnaMutant[y].charAt(x)) {
		    vertical.append(dnaMutant[y].charAt(x));
		} else if (vertical.length() < SECUENCES_DNA_LENGTH) {
		    vertical = new StringBuilder();
		} else {
		    break;
		}
		if (vertical.length() >= SECUENCES_DNA_LENGTH) {
		    wordList.add(vertical.toString());
		    if (wordList.size() > 1) {
			return true;
		    }
		    break;
		}

	    }

	}

	return wordList.size() > 1;

    }

    private List<String> checkDiagonalRightToLeft(String[] dnaMutant) {
	StringBuilder diagonalLeft = new StringBuilder();
	int dnaLen = dnaMutant.length;
	List<String> wordList = new ArrayList<>();

	for (int yr = SECUENCES_DNA_LENGTH - 1; yr < dnaLen; yr++) {
	    int auxx = 0;
	    diagonalLeft = new StringBuilder();
	    for (int dl = yr; dl >= 0; dl--) {
		if (diagonalLeft.toString().isEmpty() || diagonalLeft.charAt(diagonalLeft.length() - 1) == dnaMutant[auxx].charAt(dl)) {
		    diagonalLeft.append(dnaMutant[auxx].charAt(dl));
		} else if (diagonalLeft.length() < SECUENCES_DNA_LENGTH) {
		    diagonalLeft = new StringBuilder();
		}
		if (dl < SECUENCES_DNA_LENGTH && diagonalLeft.toString().isEmpty()) {
		    break;
		}
		auxx++;
	    }
	    if (diagonalLeft.length() >= SECUENCES_DNA_LENGTH) {
		wordList.add(diagonalLeft.toString());
	    }
	}

	for (int auxy = 1; auxy <= dnaLen - SECUENCES_DNA_LENGTH; auxy++) {
	    diagonalLeft = new StringBuilder();
	    for (int dl = dnaLen - 1, z = auxy; dl >= 0 && z < dnaLen; dl--, z++) {
		if (diagonalLeft.toString().isEmpty() || diagonalLeft.charAt(diagonalLeft.length() - 1) == dnaMutant[z].charAt(dl)) {
		    diagonalLeft.append(dnaMutant[z].charAt(dl));
		} else if (diagonalLeft.length() < SECUENCES_DNA_LENGTH) {
		    diagonalLeft = new StringBuilder();
		}
	    }
	    if (diagonalLeft.length() >= SECUENCES_DNA_LENGTH) {
		wordList.add(diagonalLeft.toString());
	    }
	}

	return wordList;

    }

    private List<String> checkDiagonalLeftToRight(String[] dnaMutant) {
	StringBuilder diagonal = new StringBuilder();
	List<String> wordList = new ArrayList<>();
	int dnaLen = dnaMutant.length;
	for (int x = 0; x <= dnaLen - SECUENCES_DNA_LENGTH; x++) {
	    diagonal = new StringBuilder();
	    for (int dr = 0; dr < dnaLen && dr + x < dnaLen; dr++) {
		if (diagonal.toString().isEmpty() || diagonal.charAt(diagonal.length() - 1) == dnaMutant[dr + x].charAt(dr)) {
		    diagonal.append(dnaMutant[dr + x].charAt(dr));
		} else if (diagonal.length() < SECUENCES_DNA_LENGTH) {
		    diagonal = new StringBuilder();
		} else {
		    break;
		}
	    }
	    if (diagonal.length() >= SECUENCES_DNA_LENGTH) {
		wordList.add(diagonal.toString());
	    }
	    if (wordList.size() > 1) {
		return wordList;
	    }
	}

	for (int auxx = 1; auxx <= dnaLen - SECUENCES_DNA_LENGTH; auxx++) {
	    diagonal = new StringBuilder();
	    for (int dr = 0; dr + auxx < dnaLen; dr++) {
		if (diagonal.toString().isEmpty() || diagonal.charAt(diagonal.length() - 1) == dnaMutant[dr].charAt(dr + auxx)) {
		    diagonal.append(dnaMutant[dr].charAt(dr + auxx));
		} else if (diagonal.length() < 4) {
		    diagonal = new StringBuilder();
		} else {
		    break;
		}
	    }
	    if (diagonal.length() >= SECUENCES_DNA_LENGTH) {
		wordList.add(diagonal.toString());
	    }
	    if (wordList.size() > 1) {
		return wordList;
	    }
	}

	return wordList;
    }

}
