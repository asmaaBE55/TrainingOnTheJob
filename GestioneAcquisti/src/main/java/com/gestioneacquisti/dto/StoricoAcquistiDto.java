package com.gestioneacquisti.dto;

import com.gestioneacquisti.model.Prodotto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoricoAcquistiDto {
    private Long id;
    private ClienteDto cliente;
    private Prodotto prodotto;
    private Integer numeroAcquisti;

}
