package com.management.progettodigestioneacquisti.model;
/**
 * L'entità StoricoAcquistiMapper è utilizzata per memorizzare l'elenco dei
 * prodotti acquistati da ogni cliente,
 * insieme al numero di volte che ogni prodotto è stato acquistato
 */

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "storico_acquisti")
public class StoricoAcquisti implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "quantita_acquistata")
    private int quantitaAcquistata;
    @Column(name = "nome_prodotto_acquistato")
    private String nomeProdotto;
    @Column(name = "data_acquisto")
    private LocalDate dataAcquisto;

}

