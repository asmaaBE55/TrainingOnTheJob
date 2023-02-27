package com.gestioneacquisti.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private List<AcquistoDto> acquisti = new ArrayList<>();
    private BigDecimal totale;
}
