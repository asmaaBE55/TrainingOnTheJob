package com.management.progettodigestioneacquisti.validators;

import com.management.progettodigestioneacquisti.dto.ClienteDto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class ClienteValidator implements Validator {

    @Autowired
    private ClienteService clienteService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClienteDto cliente = (ClienteDto) target;
        // Controllo se il cliente ha un nome
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            errors.reject("nome", "Il nome del cliente non può essere vuoto.");
        }
        // Controllo se il cliente ha abbastanza budget per effettuare un acquisto
        if (cliente.getBudget().compareTo(BigDecimal.ZERO) < 0) {
            errors.reject("budget", "Il budget del cliente non può essere negativo.");
        }
        // Controllo se il cliente esiste già nel database
        if (clienteService.existsClienteById(cliente.getId())) {
            errors.reject("cliente", "Il cliente esiste già.");
        }
    }
}
