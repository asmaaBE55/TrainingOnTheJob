package com.management.progettodigestioneacquisti.model;
/**
 * L'entità Prodotto rappresenta un prodotto che può essere acquistato.
 * Ogni prodotto ha un ID univoco e può essere acquistato in una o più quantità.
 */

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prodotti")
public class Prodotto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prodotto_id")
    private Long id;
    private String nome;
    private BigDecimal prezzoUnitario;
    private int quantitaDisponibile;
    @Column(name = "sconto")
    private String scontato;
    @Lob
    @Column(name = "immagine")
    private byte[] immagine;
    @ManyToMany(mappedBy = "prodottiAcquistati", fetch = FetchType.EAGER)
    private List<Acquisto> acquisti;

}
