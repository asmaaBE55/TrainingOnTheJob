package com.gestioneacquisti.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClienteDto {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String tipoCliente;
    private BigDecimal budget;
    private BigDecimal importoTotaleSpeso;
    private int numeroAcquisti;
    @JsonIgnore
    private Set<OrdineDto> ordini = new HashSet<>();
}
