package user.store.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.store.management.entity.Prodotto;
import user.store.management.entity.User;
import user.store.management.exception.InsufficientFundsException;
import user.store.management.service.ProdottoAcquistatoService;
import user.store.management.service.ProdottoService;
import user.store.management.service.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class ProdottoAcquistatoController {

    @Autowired
    UserService userService;
    @Autowired
    ProdottoService prodottoService;
    @Autowired
    private ProdottoAcquistatoService prodottoAcquistatoService;

    @PostMapping("/users/{userId}/prodotti/{prodottoId}")
    public ResponseEntity<String> compraProdotto(@PathVariable Long userId, @PathVariable Long prodottoId) {
        try {
            User user = userService.getUser(userId);
            Prodotto prodotto = prodottoService.getProductById(prodottoId);
            prodottoAcquistatoService.compraProdotto(user, prodotto);
            return ResponseEntity.ok("Prodotto acquistato con successo.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(e.getMessage());
        }
    }

}



