package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.FidelityCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FidelityCardRepository extends JpaRepository<FidelityCard, UUID> {
    FidelityCard findByClienteId(Long clienteId);
}
