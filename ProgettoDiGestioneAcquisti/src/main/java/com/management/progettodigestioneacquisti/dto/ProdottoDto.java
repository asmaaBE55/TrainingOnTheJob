package com.management.progettodigestioneacquisti.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdottoDto {
    private Long id;
    private String nome;
    private int quantitaDisponibile;
    private BigDecimal prezzoUnitario;
    private List<AcquistoDto> acquisti;

}
