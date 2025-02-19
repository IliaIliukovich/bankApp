package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping("/search")
    public List<Product> getProductByName(@RequestParam String name) {
        return service.getProductByName(name);
    }

    @GetMapping("/searchByStatus")
    public List<Product> searchProductByStatus(@RequestParam String status) {
        return service.getProductByStatus(status);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Product addedProduct = service.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        Product updated = service.updatedProduct(product);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        service.deleteProduct(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping
    public ResponseEntity<Void> changeStatus(@RequestParam Long id, @RequestParam(required = false) String status) {
        service.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}