package com.management.progettodigestioneacquisti;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testSalvaCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Dario");
        cliente.setCognome("Verde");
        cliente.setEmail("dario.verde@nttdata.com");
        cliente.setTipoCliente(Cliente.TipoCliente.NUOVO_CLIENTE);
        cliente.setImportoTotaleSpeso(new BigDecimal("0"));
        cliente.setBudget(new BigDecimal("200.00"));

        Cliente clienteSalvato = clienteRepository.save(cliente);
        assertNotNull(clienteSalvato.getId());
    }
    @Test
    public void testSalvaCliente2() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario");
        cliente.setCognome("Rossi");
        cliente.setEmail("mario.rossi@nttdata.com");
        cliente.setTipoCliente(Cliente.TipoCliente.NUOVO_CLIENTE);
        cliente.setImportoTotaleSpeso(new BigDecimal("0"));
        cliente.setBudget(new BigDecimal("500.00"));

        Cliente clienteSalvato = clienteRepository.save(cliente);
        assertNotNull(clienteSalvato.getId());
    }
}
