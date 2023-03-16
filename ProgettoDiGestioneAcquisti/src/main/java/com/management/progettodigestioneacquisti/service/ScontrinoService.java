package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.exception.UserNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import com.management.progettodigestioneacquisti.repository.ScontrinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@CacheConfig(cacheNames = "scontrini")
public class ScontrinoService {
    private final ScontrinoRepository scontrinoRepository;
    private final ClienteRepository clienteRepository;
    private final AcquistoRepository acquistoRepository;

    public Scontrino creaScontrino(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new UserNotFoundException("Cliente non trovato con id " + idCliente));

        List<Acquisto> acquisti = acquistoRepository.findByCliente(cliente);

        BigDecimal totale = BigDecimal.ZERO;

        /*
          Si usa per manipolare in modo efficiente le stringhe, voglio che i prodotti nello scontrino siano più
          leggibili nome prodotto, il prezzo unitario del prodotto con il simbolo Euro e la qty acquistata di ogni prodotto.
         */

        StringBuilder nomeProdotto = new StringBuilder();

        for (Acquisto acquisto : acquisti) {
            BigDecimal prezzoUnitario = acquisto.getPrezzoDiAcquisto().divide(BigDecimal.valueOf(acquisto.getQuantitaAcquistata()), RoundingMode.HALF_UP);
            int numeroAcquisti = acquisto.getQuantitaAcquistata();
            nomeProdotto.append(acquisto.getNomeProdottoAcquistato())
                    .append(" x")
                    .append(numeroAcquisti)
                    .append(" Prezzo Unit: ")
                    .append(prezzoUnitario.doubleValue())
                    .append(numeroAcquisti)
                    .append("€ --- ");

            BigDecimal prezzoDiAcquisto = acquisto.getPrezzoDiAcquisto();
            totale = totale.add(prezzoDiAcquisto);
        }
        nomeProdotto.delete(nomeProdotto.length() - 4, nomeProdotto.length()); // rimuove l'ultimo "--- "

        Scontrino scontrino = new Scontrino();
        scontrino.setDataScontrino(LocalDateTime.now());
        scontrino.setTotale(totale);
        scontrino.setAcquisti(acquisti);
        scontrino.setCliente(cliente);
        scontrino.setNomeProdottoAcquistato(nomeProdotto.toString());
        return scontrinoRepository.save(scontrino);
    }

    @Cacheable(key = "#id")
    public Scontrino getScontrinoById(Long id) {
        return scontrinoRepository.findScontrinoById(id);
    }

}
