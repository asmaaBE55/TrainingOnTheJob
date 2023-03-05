package com.management.progettodigestioneacquisti.dto;

import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcquistoDto {
    private Long id;
    private ProdottoDto prodotto;
    private int quantitaAcquistata;
    private BigDecimal prezzoDiAcquisto;
    private String nomeProdottoAcquistato;
    private Cliente cliente;
    private Scontrino scontrino;
    private StoricoAcquisti storicoAcquisti;
    private List<Prodotto> prodottiAcquistati;

}
