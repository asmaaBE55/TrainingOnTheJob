package com.management.progettodigestioneacquisti;


import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testSalvaCliente1() {
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

    @Test
    public void testSalvaCliente3() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jack");
        cliente.setCognome("jk");
        cliente.setEmail("jason.jk@nttdata.com");
        cliente.setTipoCliente(Cliente.TipoCliente.NUOVO_CLIENTE);
        cliente.setImportoTotaleSpeso(new BigDecimal("0"));
        cliente.setBudget(new BigDecimal("0.00"));

        Cliente clienteSalvato = clienteRepository.save(cliente);
        assertNotNull(clienteSalvato.getId());
    }

    @Test
    public void testSalvaCliente4() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mattia");
        cliente.setCognome("Mt");
        cliente.setEmail("mattia.mt@nttdata.com");
        cliente.setTipoCliente(Cliente.TipoCliente.NUOVO_CLIENTE);
        cliente.setImportoTotaleSpeso(new BigDecimal("0"));
        cliente.setBudget(new BigDecimal("10000.00"));

        Cliente clienteSalvato = clienteRepository.save(cliente);
        assertNotNull(clienteSalvato.getId());
    }
}
