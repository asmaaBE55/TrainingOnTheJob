package user.store.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.store.management.entity.Prodotto;


public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
}
