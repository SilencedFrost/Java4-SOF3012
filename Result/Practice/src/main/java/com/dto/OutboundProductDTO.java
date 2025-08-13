package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutboundProductDTO implements ProductDTO{
    private Integer productId;
    private String productName;
    private Integer productPrice;
    private String categoryId;

}
