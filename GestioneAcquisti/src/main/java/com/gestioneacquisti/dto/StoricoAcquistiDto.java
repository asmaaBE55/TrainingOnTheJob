package com.gestioneacquisti.dto;

import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoricoAcquistiDto {
    private Long id;
    private String nome_prodotto;
    private Cliente cliente;
    private Acquisto acquisto;
    private Integer numeroAcquisti;

}
