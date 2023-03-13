package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.FidelityCard;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import com.management.progettodigestioneacquisti.repository.FidelityCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Transactional
@Service
public class FidelityCardService {
    private final FidelityCardRepository fidelityCardRepository;
    private final ClienteRepository clienteRepository;



    public FidelityCard creaFidelityCard(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente non trovato con ID " + idCliente));

        FidelityCard fidelityCard = new FidelityCard();
        fidelityCard.setCliente(cliente);
        fidelityCard.setNomeProprietarioCard(cliente.getNome() + " " + cliente.getCognome());
        fidelityCard.setPuntiAccumulati(0);

        return fidelityCardRepository.save(fidelityCard);
    }

    public void aggiornaPunti(FidelityCard fidelityCard, BigDecimal prezzoTotale) {
        int puntiAggiunti = prezzoTotale.divide(new BigDecimal(10), RoundingMode.HALF_UP).intValue();//un punto per ogni 10 euro spesi
        int puntiAttuali = fidelityCard.getPuntiAccumulati();
        int nuoviPunti = puntiAttuali + puntiAggiunti;
        fidelityCard.setPuntiAccumulati(nuoviPunti);
        fidelityCardRepository.save(fidelityCard);
    }
    public FidelityCard getFidelityCardByClienteId(Long clienteId) {
        return fidelityCardRepository.findByClienteId(clienteId);
    }

}
