package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findClienteById(Long id);

}

