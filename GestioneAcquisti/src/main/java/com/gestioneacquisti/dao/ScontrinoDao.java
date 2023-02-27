package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.Scontrino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScontrinoDao extends JpaRepository<Scontrino, Long> {
}
