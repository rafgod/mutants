package com.mercadolibre.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.model.DNA;

@Repository
public interface DNARepository extends JpaRepository<DNA, Long> {

    Optional<DNA> findByDnaSequence(String dnaSecuence);

    List<DNA> findAllByIsMutant(boolean isMutant);

}
