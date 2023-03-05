package com.management.progettodigestioneacquisti.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "scontrini")
public class Scontrino implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scontrino_id")
    private Long id;
    private LocalDateTime dataScontrino;

    private BigDecimal prezzoDiAcquisto;
    private BigDecimal totale;
    @Column(name = "nome_prodotto_acquistato")
    private String nomeProdottoAcquistato;
    @OneToOne
    @JoinColumn(name = "acquisto_id", referencedColumnName = "acquisto_id")
    private Acquisto acquisto;

    public void addAcquisto(Acquisto acquisto) {
        if (this.acquisto == null) {
            this.acquisto = acquisto;
        } else {
            throw new IllegalStateException("Il scontrino può contenere un solo acquisto.");
        }
    }

}
