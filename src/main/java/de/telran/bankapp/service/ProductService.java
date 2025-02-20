package de.telran.bankapp.service;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.ProductStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repository = repo;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        Optional<Product> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new BankAppResourceNotFoundException(String.format("Product with id = %d not found", id));
        }
        return repository.findById(id);
    }

    public List<Product> getProductByName(String name) {
        return repository.findByName(name);
    }

    public List<Product> getProductByStatus(String status) {
        ProductStatus enumStatus = ProductStatus.valueOf(status.toUpperCase());
        return repository.findByStatus(enumStatus);
    }

    @Transactional
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Product updatedProduct(Product product) {
        Long id = product.getId();
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            Product savedProduct = repository.save(product);
            return savedProduct;
        }
        throw new BankAppResourceNotFoundException(String.format("Product with id = %d not found", id));
    }

    @Transactional
    public void changeStatus(Long id, String status) {
        ProductStatus productStatus = (status == null) ? ProductStatus.ACTIVE : ProductStatus.valueOf(status);
        int updated = repository.updateStatus(id, productStatus);
        if (updated == 0) throw new BankAppResourceNotFoundException(String.format("Product with id = %d not found", id));
    }

}
