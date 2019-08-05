package com.mercadolibre.DTO;

public class StatsMutantsResponse {

    private Integer count_mutant_dna;
    private Integer count_human_dna;
    private Double ratio;

    public Integer getCount_mutant_dna() {
	return count_mutant_dna;
    }

    public void setCount_mutant_dna(Integer count_mutant_dna) {
	this.count_mutant_dna = count_mutant_dna;
    }

    public Integer getCount_human_dna() {
	return count_human_dna;
    }

    public void setCount_human_dna(Integer count_human_dna) {
	this.count_human_dna = count_human_dna;
    }

    public Double getRatio() {
	return ratio;
    }

    public void setRatio(Double ratio) {
	this.ratio = ratio;
    }

}
