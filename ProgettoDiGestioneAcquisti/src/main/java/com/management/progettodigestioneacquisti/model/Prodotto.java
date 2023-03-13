package com.management.progettodigestioneacquisti.model;
/**
 * L'entità Prodotto rappresenta un prodotto che può essere acquistato.
 * Ogni prodotto ha un ID univoco e può essere acquistato in una o più quantità.
 */

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "prodotto_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "prezzo_unitario")
    private BigDecimal prezzoUnitario;
    @Column(name = "prezzo_scontato")
    private BigDecimal prezzoScontato;
    @Column(name = "quantita_disponibile")
    private int quantitaDisponibile;
    @Column(name = "sconto")
    private String scontato;
    @Column(name = "stato_sconto")
    private boolean statoSconto;
    @Lob
    @Column(name = "immagine")
    private byte[] immagine;
    @ManyToMany(mappedBy = "prodottiAcquistati", fetch = FetchType.EAGER)
    private List<Acquisto> acquisti;

}
