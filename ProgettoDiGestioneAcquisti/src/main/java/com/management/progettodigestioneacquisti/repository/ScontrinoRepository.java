package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Scontrino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScontrinoRepository extends JpaRepository<Scontrino, Long> {
    Scontrino findScontrinoById(Long id);
}
