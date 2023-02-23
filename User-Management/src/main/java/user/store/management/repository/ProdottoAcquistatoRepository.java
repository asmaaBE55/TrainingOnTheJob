package user.store.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.store.management.entity.ProdottoAcquistato;

@Repository
public interface ProdottoAcquistatoRepository extends JpaRepository<ProdottoAcquistato, Long> {

}