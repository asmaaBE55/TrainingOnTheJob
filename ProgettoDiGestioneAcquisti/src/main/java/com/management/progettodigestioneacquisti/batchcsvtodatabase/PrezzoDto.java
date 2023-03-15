package com.management.progettodigestioneacquisti.batchcsvtodatabase;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrezzoDto {
    private String eanProdotto;
    private BigDecimal prezzoFornitore;

}
/*
  PrezzoDto rappresenta i dati di un singolo record del csv che verranno mappati
  nell'entità Prodotto del database.
 */