package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Scontrino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcquistoDao extends JpaRepository<Acquisto, Long> {
}
