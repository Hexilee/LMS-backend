package com.zjuqsc.library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;

    @Column(length = 31, unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(length = 31, unique = true, nullable = false)
    @Email
    private String email;

    @Column(length = 31, nullable = false)
    @NotBlank
    private String name;

    @Column(length = 63, nullable = false)
    @NotBlank
    private String password;

//    @OrderColumn()
//    @OrderBy("borrow_id")
//    @OneToMany(targetEntity = Borrow.class, mappedBy = "borrowId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Borrow[] borrows;

    @Column
    @NotNull
    private boolean isAdmin;

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
