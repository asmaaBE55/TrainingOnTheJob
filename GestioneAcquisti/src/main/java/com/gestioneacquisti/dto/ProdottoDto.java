package com.gestioneacquisti.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private List<AcquistoDto> acquisti = new ArrayList<>();
}
