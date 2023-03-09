package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AcquistoRepository extends JpaRepository<Acquisto, Long> {
    List<Acquisto> findByClienteIdAndDataAcquistoBetween(Long clienteId, LocalDate dataInizio, LocalDate dataFine);

    List<Acquisto> findByCliente(Cliente cliente);

}
