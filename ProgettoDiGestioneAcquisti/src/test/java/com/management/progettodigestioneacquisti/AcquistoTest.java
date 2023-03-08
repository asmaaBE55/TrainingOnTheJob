package com.management.progettodigestioneacquisti;


import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.service.AcquistoService;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import com.management.progettodigestioneacquisti.service.StoricoAcquistiService;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import javax.validation.ValidationException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class AcquistoTest {

    @Mock
    private AcquistoService acquistoService;


    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdottoService prodottoService;

    @Mock
    private AcquistoRepository acquistoRepository;

    @Mock
    private StoricoAcquistiService storicoAcquistiService;

    private Cliente cliente;
    private Prodotto prodotto;
    private int quantitaDesiderata;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Mario");
        cliente.setCognome("Rossi");
        cliente.setBudget(new BigDecimal("430"));

        prodotto = new Prodotto();
        prodotto.setId(1L);
        prodotto.setNome("Panettone");
        prodotto.setPrezzoUnitario(new BigDecimal("10"));
        prodotto.setQuantitaDisponibile(28);

        quantitaDesiderata = 5;
    }

    @Test
    public void testCompraProdotto() throws ProductNotFoundException, InsufficientFundsException, ValidationException {
        Acquisto acquisto = new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setNomeProdottoAcquistato(prodotto.getNome());
        acquisto.setPrezzoDiAcquisto(prodotto.getPrezzoUnitario().multiply(new BigDecimal(quantitaDesiderata)));
        acquisto.setQuantitaAcquistata(quantitaDesiderata);

        StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
        storicoAcquisti.setProdotto(prodotto);
        storicoAcquisti.setCliente(cliente);
        storicoAcquisti.setNomeProdotto(prodotto.getNome());
        storicoAcquisti.setQuantitaAcquistata(quantitaDesiderata);

        EasyMock.expect(clienteService.getClienteById(1L)).andReturn(cliente);
        EasyMock.expect(prodottoService.getProdottoById(1L)).andReturn(prodotto);
        EasyMock.expect(acquistoRepository.save(acquisto)).andReturn(acquisto);
        EasyMock.expect(storicoAcquistiService.saveAcquisto(storicoAcquisti)).andReturn(storicoAcquisti);
        EasyMock.replay(clienteService, prodottoService, acquistoRepository, storicoAcquistiService);

        Acquisto result = acquistoService.compraProdotto(cliente, prodotto, quantitaDesiderata, null);
        assertEquals(acquisto, result);

        //Verifica che tutti i metodi definiti con EasyMock.expect siano stati chiamati una volta
        EasyMock.verify(clienteService, prodottoService, acquistoRepository, storicoAcquistiService);
    }
}

/**
 * Il metodo testCompraProdotto utilizza EasyMock per simulare il comportamento del metodo compraProdotto, utilizzando
 * EasyMock.expect viene definito il comportamento atteso della chiamata dei metodi.
 **/
