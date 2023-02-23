package user.store.management.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdottoDTO {
    private Long id;
    private String nome;
    private String descrizione;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal prezzo;
    private Integer quantity;
}
