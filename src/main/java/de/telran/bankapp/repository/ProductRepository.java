package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product(UUID.randomUUID().toString(), "Current Account", CurrencyCode.EUR, 2.0, new BigDecimal("1500.75"), ProductStatus.ACTIVE));
        products.add(new Product(UUID.randomUUID().toString(), "Credit Account", CurrencyCode.USD, 18.0, new BigDecimal("5000.0"), ProductStatus.ACTIVE));
        products.add(new Product(UUID.randomUUID().toString(), "Business Credit", CurrencyCode.USD, 18.0, new BigDecimal("20000.0"), ProductStatus.INACTIVE));
    }

    public List<Product> findAll() {
        return products;
    }

    public Product getProductById(String id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public Optional<Product> findById(String id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product addProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        products.add(product);
        return product;
    }

    public boolean deleteProduct(String id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    public List<Product> searchProductByCurrencyAndStatus(CurrencyCode currencyCode, ProductStatus status) {
        return products.stream()
                .filter(product -> product.getCurrencyCode().equals(currencyCode))
                .filter(product -> product.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> deleteInactiveProducts() {
        products.removeIf(product -> product.getStatus().equals(ProductStatus.INACTIVE));
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<Product> changeStatus(String id, String status) {
        Optional<Product> optional = products.stream().filter(c -> c.getId().equals(id)).findAny();
        if (optional.isPresent()) {
            Product client = optional.get();
            ProductStatus clientStatus = status == null ? ProductStatus.ACTIVE : ProductStatus.valueOf(status);
            client.setStatus(clientStatus);
            return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
