package com.dto;

public class UpdateProductDTO extends OutboundProductDTO{
    public UpdateProductDTO(Integer productId, String productName, Integer price, String categoryId) {
        super(productId, productName, price, categoryId);
    }
}
