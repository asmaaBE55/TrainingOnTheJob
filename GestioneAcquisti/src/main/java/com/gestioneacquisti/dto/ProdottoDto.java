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
public class ProdottoDto {
    private Long id;
    private String nome;
    private int quantita;
    private BigDecimal prezzo;

    private List<Acquisto> acquisti = new ArrayList<>();
}
