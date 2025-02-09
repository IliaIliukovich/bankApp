package de.telran.bankapp.service;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repo.findById(id);
    }

    public List<Product> getProductByName(String name) {
        return repo.findByName(name);
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

//    public Optional<Product> updatedProduct(Product product) {
//        Long id = product.getId();
//        Optional<Product> optional = repo.findById(id);
//        if (optional.isPresent()) {
//            Product found = optional.get();
//            found.setName(product.getName());
//            found.setStatus(product.getStatus());
//            found.setCurrencyCode(product.getCurrencyCode());
//            found.setInterestRate(product.getInterestRate());
//            found.setLimitAmount(product.getLimitAmount());
//
//            return Optional.of(found);
//        } else
//            return Optional.empty();
//    }

//    public List<Product> searchProductByCurrencyAndStatus(CurrencyCode currencyCode, ProductStatus status) {
//        return repo.searchProductByCurrencyAndStatus(currencyCode, status);
//    }
//
//    public boolean deleteInactiveProducts() {
//        return repo.deleteInactiveProducts();
//    }
//
//    public Optional<Product> changeStatus(Long id, String status) {
//        ProductStatus productStatus = (status == null) ? ProductStatus.ACTIVE : ProductStatus.valueOf(status);
//        return repo.changeStatus(id, productStatus);
//    }
}
