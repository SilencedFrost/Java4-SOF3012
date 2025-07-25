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
    private List<Favourite> favourites  = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Share> shares = new ArrayList<>();

    public void addFavourite(Favourite favourite) {
        if (favourite == null) return;

        if (this.favourites == null) this.favourites = new ArrayList<>();

        User currentUser = favourite.getUser();
        if (currentUser != null) {
            if (this.favourites.contains(favourite)) return;
            currentUser.removeFavourite(favourite);
        }

        this.favourites.add(favourite);
        favourite.setUser(this);
    }

    public void removeFavourite(Favourite favourite) {
        if (favourite == null || this.favourites == null || this.favourites.isEmpty()) return;

        if (this.favourites.contains(favourite)) {
            this.favourites.remove(favourite);
            favourite.setUser(null);
        }
    }

    public boolean hasFavourite(Favourite favourite) {
        return favourite != null && this.favourites != null && this.favourites.contains(favourite);
    }

    public int getFavouriteCount() {
        return this.favourites != null ? this.favourites.size() : 0;
    }

    public List<Favourite> getFavourites() {
        return this.favourites != null ? new ArrayList<>(this.favourites) : new ArrayList<>();
    }

    public void addShare(Share share) {
        if (share == null) return;

        if (this.shares == null) this.shares = new ArrayList<>();

        User currentUser = share.getUser();
        if (currentUser != null) {
            if (this.shares.contains(share)) return;
            currentUser.removeShare(share);
        }

        this.shares.add(share);
        share.setUser(this);
    }

    public void removeShare(Share share) {
        if (share == null || this.shares == null || this.shares.isEmpty()) return;

        if (this.shares.contains(share)) {
            this.shares.remove(share);
            share.setUser(null);
        }
    }

    public boolean hasShare(Share share) {
        return share != null && this.shares != null && this.shares.contains(share);
    }

    public int getShareCount() {
        return this.shares != null ? this.shares.size() : 0;
    }

    public List<Share> getShares() {
        return this.shares != null ? new ArrayList<>(this.shares) : new ArrayList<>();
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
