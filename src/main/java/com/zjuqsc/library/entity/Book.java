package com.zjuqsc.library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Chenxi
 */
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

    @OneToMany(
            mappedBy = "book",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @OrderBy("borrow_id desc")
    private List<Borrow> borrows = new ArrayList<>();

    @CreationTimestamp
    @Column(
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @NotNull
    private Instant createdAt = Instant.EPOCH;

    @UpdateTimestamp
    @Column(
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    @NotNull
    private Instant updatedAt = Instant.EPOCH;

    @Column
    private Instant deletedAt;
}
