package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquistoDao extends JpaRepository<Acquisto, Long> {
    Acquisto save(Acquisto acquisto);
}
