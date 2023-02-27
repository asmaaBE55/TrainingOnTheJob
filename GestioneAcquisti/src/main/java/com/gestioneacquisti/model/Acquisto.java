package com.gestioneacquisti.model;
/**
 * L'entità Acquisto rappresenta l'acquisto di un prodotto in una determinata quantità.
 * Ogni acquisto ha un ID univoco, un prodotto associato e una quantità.
 */

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "acquisti")
public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idProdotto;

    private Double prezzo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;
}
