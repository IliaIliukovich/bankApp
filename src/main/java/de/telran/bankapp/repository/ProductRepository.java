package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.entity.enums.ProductStatus;
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
        products.add(new Product(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, "Current Account", CurrencyCode.EUR, 2.0, new BigDecimal("1500.75"), ProductStatus.ACTIVE));
        products.add(new Product(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, "Credit Account", CurrencyCode.USD, 18.0, new BigDecimal("5000.0"), ProductStatus.ACTIVE));
        products.add(new Product(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, "Business Credit", CurrencyCode.USD, 18.0, new BigDecimal("20000.0"), ProductStatus.INACTIVE));
    }

    public List<Product> findAll() {
        return products;
    }

    public Product getProductById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public Optional<Product> findById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product addProduct(Product product) {
        product.setId(Long.valueOf(UUID.randomUUID().toString()));
        products.add(product);
        return product;
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    public List<Product> searchProductByCurrencyAndStatus(CurrencyCode currencyCode, ProductStatus status) {
        return products.stream()
                .filter(product -> product.getCurrencyCode().equals(currencyCode))
                .filter(product -> product.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public boolean deleteInactiveProducts() {
        return products.removeIf(product -> product.getStatus().equals(ProductStatus.INACTIVE));
    }

    public Optional<Product> changeStatus(Long id, ProductStatus status) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .map(product -> {
                    product.setStatus(status);
                    return product;
                });
    }
}
