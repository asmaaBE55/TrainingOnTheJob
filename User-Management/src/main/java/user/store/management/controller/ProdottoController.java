package user.store.management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.store.management.entity.Prodotto;
import user.store.management.service.ProdottoService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Api(value = "Product Management System")
public class ProdottoController {
    private final ProdottoService prodottoService;


    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ResponseEntity<Prodotto> createProduct(@Valid @RequestBody Prodotto prodotto) {
        Prodotto createProduct = prodottoService.createProduct(prodotto);
        return ResponseEntity.created(URI.create("/api/products/" + createProduct.getId()))
                .body(createProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by Id")
    public ResponseEntity<Prodotto> getProductById(@PathVariable Long id) {
        Optional<Prodotto> product = Optional.ofNullable(prodottoService.getProductById(id));//uso del Optional serve per il .map() method
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public ResponseEntity<List<Prodotto>> getAllProducts() {
        List<Prodotto> products = prodottoService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}