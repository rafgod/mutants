package com.mercadolibre.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DNA_TABLE")
public class DNA {
    @Id
    @Column(name = "DNA_SEQUENCE")
    private String dnaSequence;
    @Column(name = "IS_MUTANT")
    private Boolean isMutant;

    public String getDnaSequence() {
	return dnaSequence;
    }

    public void setDnaSequence(String dnaSequence) {
	this.dnaSequence = dnaSequence;
    }

    public Boolean getIsMutant() {
	return isMutant;
    }

    public void setIsMutant(Boolean isMutant) {
	this.isMutant = isMutant;
    }

}
