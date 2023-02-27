package com.gestioneacquisti.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScontrinoDto {
    private Long id;
    private LocalDateTime data;
    private ClienteDto cliente;
    private Set<AcquistoDto> acquisti=new HashSet<>();
    private BigDecimal totale;
}
