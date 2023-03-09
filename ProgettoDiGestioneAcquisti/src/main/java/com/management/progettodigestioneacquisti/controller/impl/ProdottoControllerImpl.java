package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.ProdottoController;
import com.management.progettodigestioneacquisti.converterimage.Base64ImageConverter;
import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.mapper.ProdottoMapper;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import com.management.progettodigestioneacquisti.validators.ProdottoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
    public ProdottoDto createProduct(@RequestBody @Valid ProdottoDto prodottoDto, @RequestParam("file") MultipartFile file, BindingResult result) throws IOException {
        prodottoValidator.validate(prodottoDto, result);
        if (result.hasErrors()) {
            throw new ValidationException("Dati prodotto non validi " + result);
        }
        Prodotto prodotto = prodottoMapper.asEntity(prodottoDto);
        return prodottoMapper.asDTO(prodottoService.createProduct(prodotto, file));
    }


    @Override
    @GetMapping("/{id}")
    public ProdottoDto findById(@PathVariable Long id) throws ProductNotFoundException {
        Prodotto prodotto = prodottoService.getProdottoById(id);
        return prodottoMapper.asDTO(prodotto);
    }

    @Override
    @GetMapping
    public List<ProdottoDto> list() throws IOException {
        List<Prodotto> prodotti = prodottoService.getAllProducts();
        List<ProdottoDto> prodottoDtoList = new ArrayList<>();
        String uploadDirectory = "src/main/resources/images/";

        for (Prodotto prodotto : prodotti) {
            String imageBase64 = String.valueOf(Base64ImageConverter.convert(Arrays.toString(prodotto.getImmagine())));
            Paths.get(uploadDirectory + "/images/" + (imageBase64));
            prodottoDtoList.add(prodottoService.asDtoConImmagine(prodotto));
        }
        return prodottoDtoList;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Prodotto prodotto = prodottoService.getProdottoById(id);
        if (prodotto == null) {
            return ResponseEntity.notFound().build();
        }
        prodottoService.deleteProdotto(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/{id}")
    public ProdottoDto updateProdotto(@PathVariable Long id, @RequestParam Double sconto) throws ChangeSetPersister.NotFoundException {
        Prodotto prodotto = prodottoService.getProdottoById(id);

        if (prodotto == null) {
            throw new ChangeSetPersister.NotFoundException();
        }

        prodotto.setNome(prodotto.getNome());
        prodotto.setPrezzoUnitario(prodotto.getPrezzoUnitario());
        BigDecimal percentualeScontoDecimal = BigDecimal.valueOf(sconto);
        BigDecimal cento = BigDecimal.valueOf(100);

        BigDecimal prezzoUnitario = prodotto.getPrezzoUnitario();
        prezzoUnitario.multiply(percentualeScontoDecimal).divide(cento);

        prodottoService.updateProdotto(prodotto, sconto);
        return prodottoMapper.asDTO(prodotto);
    }

}
