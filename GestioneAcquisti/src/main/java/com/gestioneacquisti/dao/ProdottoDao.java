package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoDao extends JpaRepository<Prodotto, Long> {
}
