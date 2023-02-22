package com.user.usermanagement.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank(message = "Il campo nome è obbligatorio")
    private String nome;
    @NotNull
    @NotBlank(message = "Il campo cognome è obbligatorio")
    private String cognome;
    @NotNull
    @NotBlank(message = "Il campo email è obbligatorio")
    private String email;
    @NotNull
    // la @Past si applica per verificare se la data di nascita è valida o meno
    @Past(message = "La data di nascita dev'essere valida")
    private LocalDate dataDiNascita;
    private TipoCliente tipoCliente;
    private int numeroAcquisti;
    private BigDecimal importoTotaleSpeso;
    private String luogoDiNascita;

    public enum TipoCliente {
        NUOVO_CLIENTE,
        AFFEZIONATO,
        GOLD,
        PLATINUM
    }

}
