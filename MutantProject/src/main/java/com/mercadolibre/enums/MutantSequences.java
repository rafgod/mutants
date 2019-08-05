package com.mercadolibre.enums;

public enum MutantSequences {

    A("AAAA"), T("TTTT"), G("GGGG"), C("CCCC");

    private final String sequence;

    private MutantSequences(String sequence) {
	this.sequence = sequence;
    }

    public String getSequence() {
	return sequence;
    }

}
