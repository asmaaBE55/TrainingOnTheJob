package com.management.progettodigestioneacquisti.model;
/**
 * L'entità Prodotto rappresenta un prodotto che può essere acquistato.
 * Ogni prodotto ha un ID univoco e può essere acquistato in una o più quantità.
 */

import com.management.progettodigestioneacquisti.generator.EanGenerator;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "prezzo_unitario")
    private BigDecimal prezzoUnitario;
    @Column(name = "prezzo_scontato")
    private BigDecimal prezzoScontato;
    @Column(name = "quantita_fornita")
    private int quantitaFornitaDallAzienda;
    @Transient
    @Column(name = "quantita_disponibile")
    private int quantitaDisponibile;
    @Transient
    @Column(name = "quantita_acquistata")
    private int quantitaAcquistata;
    @Column(name = "sconto")
    private String scontato;
    @Column(name = "stato_sconto")
    private boolean statoSconto;
    @Lob
    @Column(name = "immagine")
    private byte[] immagine;
    @ManyToMany(mappedBy = "prodottiAcquistati", fetch = FetchType.EAGER)
    private List<Acquisto> acquisti;
    @Column(name = "ean_prodotto")
    private String eanProdotto;
    @Column(name = "prezzo_fornitore")
    private BigDecimal prezzoFornitore;

    @PrePersist
    private void generateEan() {
        this.eanProdotto = EanGenerator.generateEan();
    }

}
