package com.management.progettodigestioneacquisti.validators;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ProdottoValidator implements Validator {

    @Autowired
    private ProdottoService prodottoService;
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProdottoDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProdottoDto prodottoDto = (ProdottoDto) target;

        if (prodottoDto.getEanProdotto() != null) {
            Prodotto prodotto;
            prodotto = prodottoService.getProdottoById(prodottoDto.getEanProdotto());
            int quantitaDisponibile = prodotto.getQuantitaDisponibile();

            if (quantitaDisponibile < prodottoDto.getQuantitaDisponibile()) {
                errors.rejectValue("quantita", "quantita.insufficiente", "La quantità richiesta supera quella disponibile.");
            } else {
                // Aggiorna la quantità disponibile del prodotto
                prodotto.setQuantitaDisponibile(quantitaDisponibile - prodottoDto.getQuantitaDisponibile());
                prodottoService.saveProdotto(prodotto);
            }
        }
        if (Objects.equals(prodottoDto.getNome(), "")) {
            errors.rejectValue("nome", "prodotto.nome.empty", "Il nome del prodotto non può essere vuoto");
        }

        if (prodottoDto.getPrezzoUnitario() == null || prodottoDto.getPrezzoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("prezzo", "prodotto.prezzo.invalid", "Il prezzo del prodotto deve essere maggiore di zero");
        }
        if (prodottoDto.getQuantitaDisponibile() == 0 || prodottoDto.getQuantitaDisponibile() < 0) {
            errors.rejectValue("quantitaDisponibile", "prodotto.quantitaDisponibile.invalid", "La quantità disponibile del prodotto deve essere maggiore o uguale a zero");
        }
        if (prodottoDto.getEanProdotto() != null && prodottoRepository.existsProdottoByEanProdotto(prodottoDto.getEanProdotto())) {
            Prodotto existingProdotto = prodottoRepository.findProdottoByEanProdotto(prodottoDto.getEanProdotto());
            if (!existingProdotto.getNome().equals(prodottoDto.getNome())) {
                if (prodottoRepository.existsByNome(prodottoDto.getNome())) {
                    errors.rejectValue("nome", "prodotto.nome.duplicate", "Il nome del prodotto è già in uso");
                }
            }
        } else {
            if (prodottoRepository.existsByNome(prodottoDto.getNome())) {
                errors.rejectValue("nome", "prodotto.nome.duplicate", "Il nome del prodotto è già in uso");
            }
        }
    }
}
