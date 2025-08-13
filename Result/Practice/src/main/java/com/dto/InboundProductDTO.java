package com.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundProductDTO implements ProductDTO{
    private String productName;
    private Integer productPrice;
    private String categoryId;

}
