package com.gestioneacquisti.dto;

import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Prodotto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScontrinoDto {
    private Long id;
    private LocalDateTime data_scontrino;
    private ClienteDto cliente;
    private BigDecimal totale;
    private Acquisto acquisto;

    private List<Prodotto> prodotti = new ArrayList<>();


}
