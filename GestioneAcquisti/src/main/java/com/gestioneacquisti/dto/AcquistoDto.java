package com.gestioneacquisti.dto;

import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.model.StoricoAcquisti;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private String nome_prodotto_acquistato;
    private Cliente cliente;

    private List<Prodotto> prodotti = new ArrayList<>();
    private List<Scontrino> scontrini = new ArrayList<>();
    private StoricoAcquisti storicoAcquisti;
    private List<Prodotto> prodottiAcquistati = new ArrayList<>();

}
