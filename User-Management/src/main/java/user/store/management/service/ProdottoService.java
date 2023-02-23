package user.store.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.store.management.dto.ProdottoDTO;
import user.store.management.entity.Prodotto;
import user.store.management.repository.ProdottoRepository;
import user.store.management.repository.UserRepository;

import java.util.List;

@Service
public class ProdottoService {
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private UserRepository userRepository;

    public Prodotto createProduct(Prodotto prodotto) {
        prodotto.setId(prodotto.getId());
        prodotto.setNome(prodotto.getNome());
        prodotto.setPrezzo(prodotto.getPrezzo());
        prodotto.setQuantity(prodotto.getQuantity());
        return prodottoRepository.save(prodotto);
    }

    public Prodotto getProductById(Long id) {
        return prodottoRepository.getReferenceById(id);
    }

    public List<Prodotto> getAllProducts() {
        return prodottoRepository.findAll();
    }

    public Prodotto updateProduct(Prodotto product, ProdottoDTO productDto) {
        product.setNome(productDto.getNome());
        product.setPrezzo(productDto.getPrezzo());
        product.setQuantity(productDto.getQuantity());
        return prodottoRepository.save(product);
    }

    public void deleteProduct(Prodotto product) {
        prodottoRepository.delete(product);
    }

    public void updateProdottoQuantita(Prodotto prodotto) {

        // Aggiorno la quantità del prodotto
        prodotto.setQuantity(prodotto.getQuantity() - 1);

        // Salvo il prodotto aggiornato nel database
        prodottoRepository.save(prodotto);
    }


}
