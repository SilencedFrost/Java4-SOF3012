package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book {

    @Id
    @Column(name = "BookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @Column(name = "Title")
    private String title;
    @Column(name = "Price")
    private Integer price = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="AuthorId", nullable = false)
    private Author author;

    public Book(String title, Integer price, Author author) {
        this.title = title;
        if(price >= 0) this.price = price;
        author.addBook(this);
    }

    public Book(Integer bookId, String productName, Integer price, Author author) {
        this(productName, price, author);
        this.bookId = bookId;
    }
}
