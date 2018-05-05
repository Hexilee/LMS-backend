package com.zjuqsc.library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Chenxi
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;

    @Column(
            length = 31,
            unique = true,
            nullable = false
    )
    @NotBlank
    private String username;

    @Column(
            length = 31,
            unique = true,
            nullable = false
    )
    @Email
    private String email;

    @Column(
            length = 31,
            nullable = false
    )
    @NotBlank
    private String name;

    @Column(
            length = 63,
            nullable = false
    )
    @NotBlank
    private String password;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @OrderBy("borrow_id desc")
    private List<Borrow> borrows = new ArrayList<>();


    @Column
    @NotNull
    private boolean isAdmin = false;

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
