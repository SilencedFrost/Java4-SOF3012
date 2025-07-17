package com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "userid")
    String id;
    @Column(name = "passwordhash")
    String passwordHash;
    @Column(name = "fullname")
    String fullname;
    @Column(name = "email")
    String email;
}
