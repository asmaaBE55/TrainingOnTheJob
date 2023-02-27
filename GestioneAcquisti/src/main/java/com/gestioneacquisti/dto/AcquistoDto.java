package com.gestioneacquisti.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AcquistoDto {
    private Long id;
    private ProdottoDto prodotto;
    private int quantita;
    private BigDecimal prezzo;
}
