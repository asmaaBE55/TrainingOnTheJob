package com.gestioneacquisti.model;
/**
 * L'entità Prodotto rappresenta un prodotto che può essere acquistato.
 * Ogni prodotto ha un ID univoco e può essere acquistato in una o più quantità.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prodotti")
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private Double prezzo;
    private int quantita;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodotto_id")
    @JsonIgnore
    private Set<Acquisto> acquisti = new HashSet<>();
}
