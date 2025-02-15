package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@Validated
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
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<Product> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        Optional<Product> updated = productService.updatedProduct(product);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping
    public ResponseEntity<Void> changeStatus(@RequestParam Long id, @RequestParam(required = false) String status) {
        Integer integer = productService.changeStatus(id, status);
        if (!integer.equals(0)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}