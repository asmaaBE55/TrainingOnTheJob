package com.gestioneacquisti.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
public class OrdineDto {
    private Long id;
    private LocalDateTime data;

    private List<AcquistoDto> acquisti = new ArrayList<>();
}
