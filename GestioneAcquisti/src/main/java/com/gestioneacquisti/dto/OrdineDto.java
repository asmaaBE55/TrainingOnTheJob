package com.gestioneacquisti.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrdineDto {
    private Long id;
    private LocalDateTime data;
    private Set<AcquistoDto> acquisti = new HashSet<>();
}
