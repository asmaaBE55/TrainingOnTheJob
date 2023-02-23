package user.store.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.store.management.entity.Prodotto;
import user.store.management.entity.ProdottoAcquistato;
import user.store.management.entity.User;
import user.store.management.exception.InsufficientFundsException;
import user.store.management.exception.ProductNotFoundException;
import user.store.management.repository.ProdottoAcquistatoRepository;

import java.math.BigDecimal;

@Service
public class ProdottoAcquistatoService {

    @Autowired
    private ProdottoAcquistatoRepository prodottoAcquistatoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProdottoService prodottoService;

    public void compraProdotto(User user, Prodotto prodotto) throws InsufficientFundsException, ProductNotFoundException {
        BigDecimal prezzo = prodotto.getPrezzo();
        BigDecimal budget = user.getBudget();
        if (budget.compareTo(prezzo) < 0) {
            throw new InsufficientFundsException("Fondi insufficienti per acquistare il prodotto.");
        }
        ProdottoAcquistato prodottoAcquistato = new ProdottoAcquistato();
        prodottoAcquistatoRepository.save(prodottoAcquistato);
        user.setBudget(budget.subtract(prezzo));
        userService.updateClientStatus(user, user.getImportoTotaleSpeso());
        prodottoService.updateProdottoQuantita(prodotto);
        userService.updateNumeroAcquisti(user);
        userService.updateImportoTotaleSpeso(user, prezzo);
    }

}


