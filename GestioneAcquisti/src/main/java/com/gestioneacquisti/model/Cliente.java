package com.gestioneacquisti.model;
/**
 * L'entità Cliente rappresenta un cliente che può effettuare ordini.
 * Ogni cliente ha un ID univoco e può avere uno o più ordini.
 */

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clienti")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @NotNull
    @NotBlank(message = "Il campo cognome è obbligatorio")
    private String cognome;
    @NotNull
    @NotBlank(message = "Il campo email è obbligatorio")
    private String email;
    @Enumerated(EnumType.STRING)
//Ci permette di specificare che il campo tipoCliente deve essere mappato come un'enumerazione.
    private TipoCliente tipoCliente;
    @Column(name = "budget", nullable = false)
    private BigDecimal budget;
    private BigDecimal importoTotaleSpeso;
    private int numeroAcquisti;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Acquisto> acquisti;



    public enum TipoCliente {
        NUOVO_CLIENTE,
        AFFEZIONATO,
        GOLD,
        PLATINUM
    }
}