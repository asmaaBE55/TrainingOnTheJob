package user.store.management.dto;

import lombok.Data;
import user.store.management.entity.Prodotto;
import user.store.management.entity.User;

import java.math.BigDecimal;

@Data
public class ProdottoAcquistatoDTO {
    private Long id;
    private User user;
    private Prodotto prodotto;
    private BigDecimal prezzoAcquisto;

}
