package de.telran.bankapp.controller;

import de.telran.bankapp.dto.ProductCreateDto;
import de.telran.bankapp.dto.ProductDto;
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
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<ProductDto> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductDto> getProductByName(@RequestParam String name) {
        return service.getProductByName(name);
    }

    @GetMapping("/searchByStatus")
    public List<ProductDto> searchProductByStatus(@RequestParam String status) {
        return service.getProductByStatus(status);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductCreateDto productDto) {
        ProductDto addedProduct = service.addProduct(productDto);
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto updated = service.updatedProduct(productDto);
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