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
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }

    @DeleteMapping()
    public ResponseEntity<Product> deleteProduct(@RequestParam String id) {
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
        return productService.deleteInactiveProducts();
    }

    @PatchMapping
    public ResponseEntity<Product> changeStatus(@RequestParam String id, @RequestParam(required = false) String status) {
        return productService.changeStatus(id, status);
    }
}