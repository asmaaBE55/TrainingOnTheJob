package user.store.management.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "prodottoacquistato")
public class ProdottoAcquistato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)//load it on-demand(fetch when needed) //"EAGER = fetch immediately"
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;
    private BigDecimal prezzoAcquisto;


}
