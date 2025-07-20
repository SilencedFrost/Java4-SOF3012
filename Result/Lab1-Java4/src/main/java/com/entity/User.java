package com.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "userid")
    String userId;
    @Column(name = "passwordhash")
    String passwordHash;
    @Column(name = "fullname")
    String fullName;
    @Column(name = "email")
    String email;
    @Column(name = "IsAdmin")
    Boolean admin = false;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorites;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Share> shares;

    public void addFavourite(Favorite favorite){
        this.favorites.add(favorite);
        favorite.setUser(this);
    }

    public void addShare(Share share){
        this.shares.add(share);
        share.setUser(this);
    }

    public User(String userId,String passwordHash,String fullName, String email){
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
    }

    public User(String userId,String passwordHash,String fullName, String email, Boolean admin){
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
