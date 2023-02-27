package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.ProdottoController;
import com.gestioneacquisti.dto.ProdottoDto;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.mapper.ProdottoMapper;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
@RequiredArgsConstructor
public class ProdottoControllerImpl implements ProdottoController {
    private final ProdottoService prodottoService;
    private final ProdottoMapper prodottoMapper;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdottoDto createProduct(@RequestBody ProdottoDto prodottoDto) {
        Prodotto prodotto = prodottoMapper.asEntity(prodottoDto);
        return prodottoMapper.asDTO(prodottoService.createProduct(prodotto));
    }

    @Override
    @GetMapping("/{id}")
    public ProdottoDto findById(@PathVariable Long id) throws ProductNotFoundException {
        Prodotto prodotto = prodottoService.findById(id).orElse(null);
        return prodottoMapper.asDTO(prodotto);
    }

    @Override
    @GetMapping
    public List<ProdottoDto> list() {
        return prodottoMapper.asDTOlist(prodottoService.getAllProducts());
    }
}
