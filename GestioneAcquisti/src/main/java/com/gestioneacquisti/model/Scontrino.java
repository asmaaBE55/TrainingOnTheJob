package com.gestioneacquisti.model;
/**
 * L'entità Scontrino rappresenta uno scontrino generato per un ordine effettuato da un cliente.
 * Ogni scontrino ha un ID univoco, una data di generazione,
 * un cliente associato un totale e una lista di ordini.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "scontrini")
public class Scontrino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate data;

    private Double totale;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scontrino_id")
    @JsonIgnore
    private Set<Acquisto> acquisti = new HashSet<>();
}
