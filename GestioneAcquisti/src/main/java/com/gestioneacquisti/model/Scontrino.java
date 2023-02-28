package com.gestioneacquisti.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "scontrini")
public class Scontrino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data_scontrino;

    private BigDecimal totale;

    @ManyToMany
    @JoinTable(name = "scontrino_acquisti",
            joinColumns = @JoinColumn(name = "scontrino_id"),
            inverseJoinColumns = @JoinColumn(name = "acquisti_id"))
    private List<Acquisto> acquisti = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    public void addAcquisto(Acquisto acquisto) {
        this.acquisti.add(acquisto);
    }
}
