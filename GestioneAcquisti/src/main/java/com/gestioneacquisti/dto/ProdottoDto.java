package com.gestioneacquisti.dto;

import com.gestioneacquisti.model.Acquisto;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProdottoDto {
    private Long id;
    private String nome;
    private int quantita;
    private BigDecimal prezzo;
    private Set<AcquistoDto> acquisti=new HashSet<>();
}
