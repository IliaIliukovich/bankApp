package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import de.telran.bankapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }

    @DeleteMapping()
    public ResponseEntity<Product> deleteProduct(@RequestParam Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Optional<Product> updated = productService.updatedProduct(product);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Product> searchProductByCurrencyAndStatus(@RequestParam CurrencyCode currencyCode, @RequestParam ProductStatus status) {
        return productService.searchProductByCurrencyAndStatus(currencyCode, status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInactiveProducts() {
        boolean deleted = productService.deleteInactiveProducts();
        return deleted ? ResponseEntity.accepted().build() : ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{id}/status")
    public ResponseEntity<Product> changeStatus(@PathVariable Long id, @RequestParam(required = false) String status) {
        return productService.changeStatus(id, status)
                .map(product -> new ResponseEntity<>(product, HttpStatus.ACCEPTED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}