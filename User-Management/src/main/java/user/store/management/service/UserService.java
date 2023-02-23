package user.store.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.store.management.entity.ProdottoAcquistato;
import user.store.management.entity.User;
import user.store.management.exception.UserNotFoundException;
import user.store.management.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //creazione di un nuovo cliente
    public User createUser(User user) {
        user.setTipoCliente(User.TipoCliente.NUOVO_CLIENTE);
        user.setNumeroAcquisti(0);
        user.setImportoTotaleSpeso(BigDecimal.ZERO);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Questo servizio ci permette di eliminare un certo cliente
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Questo metodo ci permette di modificare la tipologia del cliente in base all'importo totale di spese
     **/
    public void updateClientStatus(User user, BigDecimal importoTotaleSpeso) {
        BigDecimal sogliaGold = new BigDecimal("1000.00");
        BigDecimal sogliaPlatinum = new BigDecimal("5000.00");

        if (importoTotaleSpeso.compareTo(sogliaPlatinum) > 0) {
            user.setTipoCliente(User.TipoCliente.PLATINUM);
        } else if (importoTotaleSpeso.compareTo(sogliaGold) > 0) {
            user.setTipoCliente(User.TipoCliente.GOLD);
        } else {
            user.setTipoCliente(User.TipoCliente.AFFEZIONATO);
        }

        user.setImportoTotaleSpeso(importoTotaleSpeso);
        userRepository.save(user);
    }

    public void aggiornaBudget(User user, BigDecimal importoTotaleSpeso) {
        BigDecimal nuovoBudget = user.getBudget().subtract(importoTotaleSpeso); // sottrai l'importo totale speso dal budget
        user.setBudget(nuovoBudget); // aggiorna il campo budget
        userRepository.save(user);
    }

    public Set<ProdottoAcquistato> getProdottiAcquistati(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found" + userId));
        return user.getProdottiAcquistati();
    }

    public void updateNumeroAcquisti(User user) {
        user.setNumeroAcquisti(user.getNumeroAcquisti() + 1);
        userRepository.save(user);
    }

    public void updateImportoTotaleSpeso(User user, BigDecimal prezzo) {
        BigDecimal importoTotaleSpeso = user.getImportoTotaleSpeso();
        if (importoTotaleSpeso == null) {
            importoTotaleSpeso = BigDecimal.ZERO;
        }
        user.setImportoTotaleSpeso(importoTotaleSpeso.add(prezzo));
        userRepository.save(user);
    }

}


