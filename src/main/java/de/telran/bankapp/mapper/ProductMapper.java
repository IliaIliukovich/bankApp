package de.telran.bankapp.mapper;

import de.telran.bankapp.dto.ProductCreateDto;
import de.telran.bankapp.dto.ProductDto;
import de.telran.bankapp.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product dtoToEntity(ProductDto dto);

    ProductDto entityToDto(Product entity);

    List<ProductDto> entityListToDto(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Product createDtoToEntity(ProductCreateDto dto);
}
