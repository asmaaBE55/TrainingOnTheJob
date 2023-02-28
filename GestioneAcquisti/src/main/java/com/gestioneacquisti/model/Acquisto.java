package com.gestioneacquisti.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "acquisti")
public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "acquisto_id")
    private Long id;
    @Column(name = "prezzo_di_acquisto")
    private BigDecimal prezzoDiAcquisto;
    @Column(name = "quantita_acquistata")
    private int quantitaAcquistata;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "acquisto_prodotto",
            joinColumns = @JoinColumn(name = "acquisto_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
    private List<Prodotto> prodotti=new ArrayList<>();
}
