package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @Column(name = "ProductId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @Column(name = "ProductName")
    private String productName;
    @Column(name = "Price")
    private Integer price = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="CategoryId", nullable = false)
    private Category category;

    public Product(String productName, Integer price, Category category) {
        this.productName = productName;
        if(price >= 0) this.price = price;
        category.addProduct(this);
    }
}
