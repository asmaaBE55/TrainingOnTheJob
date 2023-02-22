package com.user.usermanagement.service;

import com.user.usermanagement.entity.User;
import com.user.usermanagement.exception.UserNotFoundException;
import com.user.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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


}


