package com.management.progettodigestioneacquisti.dto;

import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScontrinoDto {
    private Long id;
    private LocalDateTime dataScontrino;
    private BigDecimal prezzoDiAcquisto;
    private String nomeProdottoAcquistato;
    private Acquisto acquisto;
    private BigDecimal totale;
    private Cliente cliente;
    private List<AcquistoDto> acquisti;

}
