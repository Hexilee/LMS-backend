package com.zjuqsc.library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bid;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            targetEntity = BookClass.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "bcid")
    @NotNull
    private BookClass bookClass;

    @Column(name = "`accessible`")
    @NotNull
    private boolean accessible;
//
//    @OneToMany(targetEntity = Borrow.class, mappedBy = "borrowId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Borrow[] borrows;

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
