package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Authors")
public class Author {

    @Id
    @Column(name = "AuthorId")
    private String authorId;
    @Column(name = "AuthorName")
    private String authorName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    public Author(String authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public void addBook(Book book) {
        if (book == null) return;

        if (this.books == null) this.books = new ArrayList<>();

        Author currentAuthor = book.getAuthor();
        if (currentAuthor != null) {
            if (this.books.contains(book)) return;
            currentAuthor.removeBook(book);
        }

        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        if (book == null || this.books == null || this.books.isEmpty()) return;

        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setAuthor(null);
        }
    }

    public boolean hasBook(Book book) {
        return book != null && this.books != null && this.books.contains(book);
    }
}
