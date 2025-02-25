package de.telran.bankapp.service;

import de.telran.bankapp.dto.ProductCreateDto;
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
        return mapper.entityListToDto(products);
    }

    public Optional<ProductDto> getProductById(Long id) {
        Optional<Product> optional = repository.findById(id);
        Product product = optional.orElseThrow(() -> new BankAppResourceNotFoundException(String.format("Product with id = %d not found", id)));
        ProductDto productDto = mapper.entityToDto(product);
        return Optional.of(productDto);
    }

    public List<ProductDto> getProductByName(String name) {
        List<Product> products = repository.findByName(name);
        return mapper.entityListToDto(products);
    }

    public List<ProductDto> getProductByStatus(String status) {
        List<Product> products = repository.findByStatus(ProductStatus.valueOf(status));
        return mapper.entityListToDto(products);
    }

    @Transactional
    public ProductDto addProduct(ProductCreateDto dto) {
        Product product = mapper.createDtoToEntity(dto);
        Product savedProduct = repository.save(product);
        return mapper.entityToDto(savedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ProductDto updatedProduct(ProductDto productDto) {
        Long id = productDto.getId();
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            Product product = mapper.dtoToEntity(productDto);
            Product savedProduct = repository.save(product);
            return mapper.entityToDto(savedProduct);
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
