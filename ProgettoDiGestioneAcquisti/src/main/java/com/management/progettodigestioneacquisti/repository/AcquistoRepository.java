package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquistoRepository extends JpaRepository<Acquisto, Long> {
    Acquisto findAcquistoById(Long id);
    List<Acquisto> findByCliente(Cliente cliente);


}
