package com.gestioneacquisti.model;
/**
 * L'entità Ordine rappresenta un ordine effettuato da un cliente per acquistare uno o più prodotti.
 * Ogni ordine ha un ID univoco, un cliente associato e uno o più acquisti.
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
@Table(name = "ordini")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate data;

    private Double totale;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine_id")
    @JsonIgnore
    private Set<Acquisto> acquisti=new HashSet<>();

}
