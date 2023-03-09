package com.management.progettodigestioneacquisti.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoricoAcquistiDto {
    private Long id;
    private Integer numeroAcquisti;
    private String nomeProdotto;
    private List<AcquistoDto> acquisti;
}
