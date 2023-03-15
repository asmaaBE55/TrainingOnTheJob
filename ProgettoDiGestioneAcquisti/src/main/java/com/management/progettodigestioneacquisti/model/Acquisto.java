package com.management.progettodigestioneacquisti.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "acquisti")
public class Acquisto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "acquisto_id")
    private Long id;
    @Column(name = "prezzo_di_acquisto")
    private BigDecimal prezzoDiAcquisto;
    @Column(name = "quantita_acquistata")
    private int quantitaAcquistata;
    @Column(name = "nome_prodotto_acquistato")
    private String nomeProdottoAcquistato;
    @Column(name = "data_acquisto")
    private LocalDate dataAcquisto;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_storico_acquisti")
    private StoricoAcquisti storicoAcquisti;

    @ManyToOne
    @JoinColumn(name = "scontrino_id")
    private Scontrino scontrino;
    @ManyToMany
    @JoinTable(name = "prodotti_acquistati",
            joinColumns = @JoinColumn(name = "acquisto_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
    private List<Prodotto> prodottiAcquistati;


}
