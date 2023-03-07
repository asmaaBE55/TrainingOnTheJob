package com.management.progettodigestioneacquisti;

import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import com.management.progettodigestioneacquisti.service.ClienteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void testTrovaClientePerId() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Mario");
        cliente.setCognome("Rossi");

        Mockito.when(clienteRepository.findClienteById(id)).thenReturn(cliente);

        Cliente clienteTrovato = clienteService.getClienteById(id);

        assertEquals(cliente, clienteTrovato);
    }
}
