package com.gestioneacquisti.model;
/**
 * L'entità Prodotto rappresenta un prodotto che può essere acquistato.
 * Ogni prodotto ha un ID univoco e può essere acquistato in una o più quantità.
 */

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prodotti")
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private BigDecimal prezzo;
    private int quantita;
}
