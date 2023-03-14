package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.StoricoAcquistiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StoricoAcquistiService {
    private final StoricoAcquistiRepository storicoAcquistiRepository;
    private final AcquistoRepository acquistoRepository;

    public StoricoAcquisti saveAcquisto(StoricoAcquisti storicoAcquisti) {
        return storicoAcquistiRepository.save(storicoAcquisti);
    }

    public String getAcquistiByClienteIdAndPeriodoAsString(Long clienteId, LocalDate dataInizio, LocalDate dataFine) {
        List<Acquisto> acquisti = acquistoRepository.findByClienteIdAndDataAcquistoBetween(clienteId, dataInizio, dataFine);
        StringBuilder sb = new StringBuilder();
        if (acquisti.isEmpty()) {
            sb.append("Non è stato fatto nessun acquisto nel periodo specificato.");
        } else {
            sb.append("Acquisti del cliente ").append(clienteId).append(" nel periodo ").append(dataInizio).append(" / ").append(dataFine).append(" sono:\n");
            for (Acquisto acquisto : acquisti) {
                sb.append("- Prodotto: ").append(acquisto.getNomeProdottoAcquistato())
                        .append(", Quantità: ").append(acquisto.getQuantitaAcquistata())
                        .append(", Prezzo pagato: ").append(acquisto.getPrezzoDiAcquisto()).append("€").append("\n");
            }
        }
        return sb.toString();
    }


}



