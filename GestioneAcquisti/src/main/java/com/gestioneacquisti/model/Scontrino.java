package com.gestioneacquisti.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime data_scontrino;

    private BigDecimal prezzo_acquisto;
    private BigDecimal totale;
    @Column(name = "nome_prodotto_acquistato")
    private String nome_prodotto_acquistato;
    @OneToOne
    @JoinColumn(name = "acquisto_id")
    private Acquisto acquisto;

    public void addAcquisto(Acquisto acquisto) {
        if (this.acquisto == null) {
            this.acquisto = acquisto;
        } else {
            throw new IllegalStateException("Il scontrino può contenere un solo acquisto.");
        }
    }

}
