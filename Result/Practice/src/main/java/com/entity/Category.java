package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @Column(name = "CategoryId")
    private String categoryId;
    @Column(name = "CategoryName")
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public void addProduct(Product product) {
        if (product == null) return;

        if (this.products == null) this.products = new ArrayList<>();

        Category currentCategory = product.getCategory();
        if (currentCategory != null) {
            if (this.products.contains(product)) return;
            currentCategory.removeProduct(product);
        }

        this.products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        if (product == null || this.products == null || this.products.isEmpty()) return;

        if (this.products.contains(product)) {
            this.products.remove(product);
            product.setCategory(null);
        }
    }

    public boolean hasProduct(Product product) {
        return product != null && this.products != null && this.products.contains(product);
    }

    public int getProductCount() {
        return this.products != null ? this.products.size() : 0;
    }

    public List<Product> getProducts() {
        return this.products != null ? new ArrayList<>(this.products) : new ArrayList<>();
    }
}
