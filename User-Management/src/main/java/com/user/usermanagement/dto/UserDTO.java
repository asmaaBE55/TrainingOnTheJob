package com.user.usermanagement.dto;

import com.user.usermanagement.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
    private Integer numeroAcquisti;
    private BigDecimal importoTotaleSpeso;
    private User.TipoCliente tipoCliente;

}
/**
 * una DTO viene utilizzata per rappresentare una versione semplificata e serializzabile
 * di un'entità . Questo può essere utile quando si devono trasmettere dati tra componenti
 * con diversi formati, protocolli o interfacce.
 **/