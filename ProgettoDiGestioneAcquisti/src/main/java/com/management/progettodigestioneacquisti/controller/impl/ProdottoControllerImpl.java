package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.ProdottoController;
import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ScontoProdottoNonLogico;
import com.management.progettodigestioneacquisti.mapper.ProdottoMapper;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import com.management.progettodigestioneacquisti.validators.ProdottoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prodotti")
@RequiredArgsConstructor
public class ProdottoControllerImpl implements ProdottoController {
    private final ProdottoService prodottoService;
    private final ProdottoMapper prodottoMapper;
    private final ProdottoRepository prodottoRepository;


    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdottoDto createProduct(@RequestBody @Valid ProdottoDto prodottoDto, @RequestParam("file") MultipartFile file, BindingResult result) throws IOException {
        ProdottoValidator prodottoValidator = new ProdottoValidator();
        prodottoValidator.validate(prodottoDto, result);
        if (result.hasErrors()) {
            throw new ValidationException("Dati prodotto non validi " + result);
        }
        Prodotto prodotto = prodottoMapper.asEntity(prodottoDto);
        return prodottoMapper.asDTO(prodottoService.createProduct(prodotto, file));
    }

    @Override
    @GetMapping("/{ean}")
    public ProdottoDto findById(@PathVariable String ean) {
        Prodotto prodotto = prodottoService.getProdottoById(ean);
        return prodottoMapper.asDTO(prodotto);
    }

    @Override
    @GetMapping
    public List<ProdottoDto> list() throws IOException {
       return prodottoMapper.asDTOlist(prodottoService.getAllProducts());
    }

    @Override
    @DeleteMapping("/{ean}")
    public ResponseEntity<?> delete(@PathVariable("ean") String ean) {
        Prodotto prodotto = prodottoService.getProdottoById(ean);
        if (prodotto == null) {
            return ResponseEntity.notFound().build();
        }
        prodottoService.deleteProdotto(ean);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/{ean}")
    public ProdottoDto updateProdotto(@PathVariable String ean, @RequestParam Double sconto) throws ChangeSetPersister.NotFoundException, ScontoProdottoNonLogico {
        Prodotto prodotto = prodottoService.getProdottoById(ean);

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

    @Override
    @PostMapping("/{ean}/stock")
    public ResponseEntity<String> aggiornaQuantitaDisponibile(@PathVariable("ean") String eanProdotto,
                                                              @RequestParam("quantity") int quantitaDaAggiungere) {
        prodottoService.aggiornaQuantitaDisponibile(eanProdotto, quantitaDaAggiungere);
        return ResponseEntity.ok().body("Quantità disponibile aggiornata con successo per EAN " + eanProdotto);
    }

    @Override
    @PostMapping("/importa-prezzi-fornitori")
    public void importaPrezziFornitoriDalCsv() {
        prodottoService.importaPrezziFornitoriDalCsv();
    }

    @Override
    @PostMapping("/importa-quantita-fornita")
    public void importaQuantitaFornita() {
        prodottoService.importaQuantitaFornitaDalCsv();
    }
    @Override
    @GetMapping("/prodotti/{ean}/immagine")
    public ResponseEntity<byte[]> getImmagineProdottoByEan(@PathVariable String ean) {
        Optional<Prodotto> optionalProdotto = prodottoRepository.findByEanProdotto(ean);
        if (optionalProdotto.isPresent()) {
            Prodotto prodotto = optionalProdotto.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(prodotto.getImmagine().length);
            return new ResponseEntity<>(prodotto.getImmagine(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
