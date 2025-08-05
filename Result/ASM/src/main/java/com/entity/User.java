package com.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "UserId", length = 30)
    private String userId;
    @Column(name = "PasswordHash", nullable = false, length = 60)
    private String passwordHash;
    @Column(name = "FullName", nullable = false, length = 50)
    private String fullName;
    @Column(name = "Email", nullable = false, length = 50)
    private String email;
    @CreationTimestamp
    @Column(name = "CreationDate", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="RoleId", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> favourites  = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Share> shares = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Log> logs = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public User(String userId, String passwordHash, String fullName, String email, Role role) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    @PrePersist
    @PreUpdate
    private void normalizeEmail() {
        if (email != null) {
            this.email = email.toLowerCase().trim();
        }
    }

    @PrePersist
    @PreUpdate
    private void normalizeUserId() {
        if (userId != null) {
            this.userId = userId.toLowerCase().trim();
        }
    }

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

    public void addLog(Log log) {
        if (log == null) return;

        if (this.logs == null) this.logs = new ArrayList<>();

        User currentUser = log.getUser();
        if (currentUser != null) {
            if (this.logs.contains(log)) return;
            currentUser.removeLog(log);
        }

        this.logs.add(log);
        log.setUser(this);
    }

    public void removeLog(Log log) {
        if (log == null || this.logs == null || this.logs.isEmpty()) return;

        if (this.logs.contains(log)) {
            this.logs.remove(log);
            log.setUser(null);
        }
    }

    public boolean hasLog(Log log) {
        return log != null && this.logs != null && this.logs.contains(log);
    }

    public int getLogCount() {
        return this.logs != null ? this.logs.size() : 0;
    }

    public List<Log> getLogs() {
        return this.logs != null ? new ArrayList<>(this.logs) : new ArrayList<>();
    }

    public void addComment(Comment comment) {
        if (comment == null) return;

        if (this.comments == null) this.comments = new ArrayList<>();

        User currentUser = comment.getUser();
        if (currentUser != null) {
            if (this.comments.contains(comment)) return;
            currentUser.removeComment(comment);
        }

        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        if (comment == null || this.comments == null || this.comments.isEmpty()) return;

        if (this.comments.contains(comment)) {
            this.comments.remove(comment);
            comment.setUser(null);
        }
    }

    public boolean hasComment(Comment comment) {
        return comment != null && this.comments != null && this.comments.contains(comment);
    }

    public int getCommentCount() {
        return this.comments != null ? this.comments.size() : 0;
    }

    public List<Comment> getComments() {
        return this.comments != null ? new ArrayList<>(this.comments) : new ArrayList<>();
    }
}
