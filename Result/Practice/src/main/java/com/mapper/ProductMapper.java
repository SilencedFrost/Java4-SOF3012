package com.mapper;

import com.dto.InboundProductDTO;
import com.dto.OutboundProductDTO;
import com.dto.ProductDTO;
import com.entity.Category;
import com.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new OutboundProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getCategory().getCategoryId()
                );
    }

    public static Product toEntity(InboundProductDTO productDTO, Category category) {
        if (productDTO == null) {
            return null;
        }

        return new Product(
                productDTO.getProductName(),
                productDTO.getProductPrice(),
                category
        );
    }

    public static List<ProductDTO> toDTOList(List<Product> entityList) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : entityList) {
            dtoList.add(ProductMapper.toDTO(product));
        }
        return dtoList;
    }
}
