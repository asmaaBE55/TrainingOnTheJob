package com.management.progettodigestioneacquisti.dto;

import com.management.progettodigestioneacquisti.model.Cliente;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FidelityCardDto {
    private Long id;
    private int puntiAccumulati;
    private Cliente cliente;
    private String nomeProprietarioCard;


}
