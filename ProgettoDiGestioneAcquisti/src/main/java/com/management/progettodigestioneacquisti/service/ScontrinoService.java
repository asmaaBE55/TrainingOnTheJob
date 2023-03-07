package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.exception.UserNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import com.management.progettodigestioneacquisti.repository.ScontrinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoService {

    private final ScontrinoRepository scontrinoRepository;
    private final ClienteRepository clienteRepository;
    private final AcquistoRepository acquistoRepository;

//    public Scontrino creaScontrino(Long idCliente,List<Acquisto> acquisti) {
//        Cliente cliente = clienteRepository.findById(idCliente)
//                .orElseThrow(() -> new UserNotFoundException("Cliente non trovato con id " + idCliente));
//
//        acquistoRepository.findByCliente(cliente);
//        String nomeProdotto="";
//        BigDecimal totale = BigDecimal.ZERO;
//        BigDecimal prezzoUnitario=BigDecimal.ZERO;
//        for (Acquisto acquisto : acquisti) {
//            nomeProdotto=nomeProdotto.concat(acquisto.getNomeProdottoAcquistato());
//            prezzoUnitario=prezzoUnitario.add(acquisto.getPrezzoDiAcquisto());
//            totale = totale.add(acquisto.getPrezzoDiAcquisto());
//        }
//
//        Scontrino scontrino = new Scontrino();
//        scontrino.setDataScontrino(LocalDateTime.now());
//        scontrino.setTotale(totale);
//        scontrino.setAcquisti(acquisti);
//        scontrino.setCliente(cliente);
//
//        return scontrinoRepository.save(scontrino);
//    }
//public Scontrino creaScontrino(Long idCliente) {
//    Cliente cliente = clienteRepository.findById(idCliente)
//            .orElseThrow(() -> new UserNotFoundException("Cliente non trovato con id " + idCliente));
//
//    List<Acquisto> acquisti = acquistoRepository.findByCliente(cliente);
//
//    BigDecimal totale = BigDecimal.ZERO;
//    StringBuilder nomeProdotto = new StringBuilder();
//    List<BigDecimal> prezziUnitari = new ArrayList<>();
//
//    for (Acquisto acquisto : acquisti) {
//        nomeProdotto.append(acquisto.getNomeProdottoAcquistato()).append(", ");
//        prezziUnitari.add(acquisto.getPrezzoDiAcquisto());
//        totale = totale.add(acquisto.getPrezzoDiAcquisto());
//    }
//
//    Scontrino scontrino = new Scontrino();
//    scontrino.setDataScontrino(LocalDateTime.now());
//    scontrino.setTotale(totale);
//    scontrino.setAcquisti(acquisti);
//    scontrino.setCliente(cliente);
//    scontrino.setNomeProdottoAcquistato(nomeProdotto.toString());
//    //scontrino.setPrezziUnitari((Map<String, BigDecimal>) prezziUnitari);
//
//    return scontrinoRepository.save(scontrino);
//}

    public Scontrino creaScontrino(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new UserNotFoundException("Cliente non trovato con id " + idCliente));

        List<Acquisto> acquisti = acquistoRepository.findByCliente(cliente);

        BigDecimal totale = BigDecimal.ZERO;
        StringBuilder nomeProdotto = new StringBuilder();

        for (Acquisto acquisto : acquisti) {
            BigDecimal prezzoUnitario = acquisto.getPrezzoDiAcquisto();
            int numeroAcquisti=acquisto.getQuantitaAcquistata();
            nomeProdotto.append(acquisto.getNomeProdottoAcquistato())
                    .append("\t")
                    .append("Qte: ")
                    .append(numeroAcquisti)
                    .append("\t")
                    .append("Prezzo: ")
                    .append(prezzoUnitario).append("€ , ");

            BigDecimal prezzoDiAcquisto = acquisto.getPrezzoDiAcquisto();
            totale = totale.add(prezzoDiAcquisto);
        }
        nomeProdotto.delete(nomeProdotto.length() - 2, nomeProdotto.length()); // rimuove l'ultimo ", "

        Scontrino scontrino = new Scontrino();
        scontrino.setDataScontrino(LocalDateTime.now());
        scontrino.setTotale(totale);
        scontrino.setAcquisti(acquisti);
        scontrino.setCliente(cliente);
        scontrino.setNomeProdottoAcquistato(nomeProdotto.toString());
        return scontrinoRepository.save(scontrino);
    }


    public Scontrino getScontrinoById(Long id) {
        return scontrinoRepository.findScontrinoById(id);
    }


}
