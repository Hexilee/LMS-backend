package com.zjuqsc.library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
public class BookClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bcid;

    @ISBN
    @Column(length = 31, unique = true)
    private String ISBN;

    @Column(length = 31)
    @NotBlank
    private String name;

    @Column(length = 31)
    @NotBlank
    private String author;

    @Column
    @NotBlank
    private String description;

//    @OrderColumn()
//    @OrderBy("bid")
//    @OneToMany(targetEntity = Book.class, mappedBy = "bid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Book[] books;

    @Column(nullable = false)
    @NotNull
    private Instant publishedAt;

    @CreationTimestamp
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @NotNull
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @NotNull
    private Instant updatedAt;

    @Column
    private Instant deletedAt;
}