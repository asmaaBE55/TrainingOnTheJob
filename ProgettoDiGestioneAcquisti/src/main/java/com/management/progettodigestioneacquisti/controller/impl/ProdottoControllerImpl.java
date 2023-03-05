package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.ProdottoController;
import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.mapper.ProdottoMapper;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import com.management.progettodigestioneacquisti.validators.ProdottoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/prodotti")
@RequiredArgsConstructor
public class ProdottoControllerImpl implements ProdottoController {
    private final ProdottoService prodottoService;
    private final ProdottoMapper prodottoMapper;
    private final ProdottoValidator prodottoValidator;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdottoDto createProduct(@RequestBody @Valid ProdottoDto prodottoDto, BindingResult result) {
        prodottoValidator.validate(prodottoDto, result);
        if (result.hasErrors()) {
            throw new ValidationException("Dati prodotto non validi " + result);
        }
        Prodotto prodotto = prodottoMapper.asEntity(prodottoDto);
        return prodottoMapper.asDTO(prodottoService.createProduct(prodotto));
    }

    @Override
    @GetMapping("/{id}")
    public ProdottoDto findById(@PathVariable Long id) throws ProductNotFoundException {
        Prodotto prodotto = prodottoService.getProdottoById(id);
        return prodottoMapper.asDTO(prodotto);
    }

    @Override
    @GetMapping
    public List<ProdottoDto> list() {
        return prodottoMapper.asDTOlist(prodottoService.getAllProducts());
    }

}
