package com.gestioneacquisti.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StoricoAcquistiDto {
    private Long id;
    private ClienteDto cliente;
    private Map<ProdottoDto, Integer> acquisti;
}
