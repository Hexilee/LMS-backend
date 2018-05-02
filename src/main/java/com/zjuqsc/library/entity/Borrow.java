package com.zjuqsc.library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author Li Chenxi
 */
@Entity
@Data
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int borrowId;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "uid")
    @NotNull
    private User user;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            targetEntity = Book.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "bid")
    @NotNull
    private Book book;

    @Column(nullable = false)
    @NotNull
    private Instant shouldReturnAt;

    @Column
    private Instant returnedAt;

    @CreationTimestamp
    @Column(
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @NotNull
    private Instant createdAt;

    @UpdateTimestamp
    @Column(
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    @NotNull
    private Instant updatedAt;

    @Column
    private Instant deletedAt;
}
