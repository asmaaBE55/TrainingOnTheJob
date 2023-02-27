package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.StoricoAcquisti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoricoAcquistiDao extends JpaRepository<StoricoAcquisti, Long> {
}
