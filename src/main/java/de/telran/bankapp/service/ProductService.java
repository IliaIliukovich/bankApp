package de.telran.bankapp.service;

import de.telran.bankapp.dto.ProductDto;
import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.ProductStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.ProductMapper;
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
    private final ProductMapper mapper;

    @Autowired
    public ProductService(ProductRepository repo, ProductMapper mapper) {
        this.repository = repo;
        this.mapper = mapper;
    }

    public List<ProductDto> getAll() {
        List<Product> products = repository.findAll();
        return mapper.toDto(products);
    }

    public Optional<ProductDto> getProductById(Long id) {
        Optional<Product> product = repository.findById(id);
        ProductDto productDto = mapper.toDto(product.orElse(null));
        return Optional.of(productDto);
    }

    public List<ProductDto> getProductByName(String name) {
        List<Product> products = repository.findByName(name);
        return mapper.toDto(products);
    }

    public List<ProductDto> getProductByStatus(String status) {
        List<Product> products = repository.findByStatus(ProductStatus.valueOf(status));
        return mapper.toDto(products);
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
