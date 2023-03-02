package com.gestioneacquisti.dto;

import com.gestioneacquisti.model.Prodotto;
import lombok.*;

import java.math.BigDecimal;

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


}
