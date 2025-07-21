package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "UserId")
    private String userId;
    @Column(name = "Passwordhash")
    private String passwordHash;
    @Column(name = "Fullname")
    private String fullName;
    @Column(name = "Email")
    private String email;

    @ManyToOne
    @JoinColumn(name="RoleId")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favourite> favorites  = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Share> shares = new ArrayList<>();

    public void addFavourite(Favourite favourite){
        this.favorites.add(favourite);
        favourite.setUser(this);
    }

    public void addShare(Share share){
        this.shares.add(share);
        share.setUser(this);
    }

    public User(String userId,String passwordHash,String fullName, String email, Role role){
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
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
