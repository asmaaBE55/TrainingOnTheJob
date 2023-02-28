package com.gestioneacquisti.dto;


import com.gestioneacquisti.model.Acquisto;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Acquisto> acquisti = new ArrayList<>();

}
