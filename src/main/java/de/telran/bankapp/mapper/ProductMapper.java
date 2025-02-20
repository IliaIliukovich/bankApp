package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.ProductDto;
import de.telran.bankapp.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toProduct(ProductDto productDto);
    List<ProductDto> toDto(List<Product> products);
}
