package com.management.progettodigestioneacquisti.dto;

import com.management.progettodigestioneacquisti.model.Acquisto;
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
    private List<Acquisto> acquisti;
}
