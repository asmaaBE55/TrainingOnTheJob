package com.gestioneacquisti.model;
/**
 * L'entità StoricoAcquistiMapper è utilizzata per memorizzare l'elenco dei
 * prodotti acquistati da ogni cliente,
 * insieme al numero di volte che ogni prodotto è stato acquistato
 */

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "storico_acquisti")
public class StoricoAcquisti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany
    @JoinColumn(name = "id_storico_acquisti")
    private List<Acquisto> acquisti;
    private Integer numeroAcquisti;

}

