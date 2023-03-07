package com.management.progettodigestioneacquisti;

import com.management.progettodigestioneacquisti.service.ClienteService;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EasyMockCliente {
    @Test
    public void testCliente(){
        ClienteService clienteService= EasyMock
                .createNiceMock(ClienteService.class);
        EasyMock.expect(clienteService.getClienteById(1L)).andReturn(clienteService.getClienteById(1L));
        EasyMock.replay(clienteService);
    }
}
