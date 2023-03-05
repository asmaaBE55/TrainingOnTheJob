package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Acquisto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquistoRepository extends JpaRepository<Acquisto, Long> {
    Acquisto findAcquistoById(Long id);

}
