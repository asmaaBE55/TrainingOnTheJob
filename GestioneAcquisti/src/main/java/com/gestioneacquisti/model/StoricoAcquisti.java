package com.gestioneacquisti.model;
/**
 * L'entità StoricoAcquistiMapper è utilizzata per memorizzare l'elenco dei
 * prodotti acquistati da ogni cliente,
 * insieme al numero di volte che ogni prodotto è stato acquistato
 */

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "storico_acquisti")
public class StoricoAcquisti {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nomeProdotto;

    private Integer numeroAcquisti;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
