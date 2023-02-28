package com.gestioneacquisti.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "acquisti")
public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal prezzoDiAcquisto;
    private int quantitaAcquistata;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;
    @ManyToOne
    @JoinColumn(name = "id_storico_acquisti")
    private StoricoAcquisti storicoAcquisti;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scontrino_id")
    private Scontrino scontrino;


    public Acquisto(Prodotto prodotto, int quantitaDesiderata) {
        this.prodotto = prodotto;
        this.quantitaAcquistata = quantitaDesiderata;
    }
}
