package com.management.progettodigestioneacquisti.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fidelity_card")
public class FidelityCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Long id;
    @Column(name = "punti_accumulati")
    private int puntiAccumulati;
    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;
    @Column(name = "nome_proprietario_card")
    private String nomeProprietarioCard;
}


