package com.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Video")
public class Video {

    @Id
    @Column(name = "VideoId", length = 20)
    private String videoId;
    @Column(name = "Title", nullable = false, length = 100)
    private String title;
    @Column(name = "Thumbnail", nullable = false, length = 50)
    private String thumbnail;
    @Column(name = "Link", nullable = false, length = 100)
    private String link;
    @Column(name = "ViewCount", nullable = false)
    private Long views = 0L;
    @Column(name = "Descript", nullable = false, length = 500)
    private String description;
    @Column(name = "Active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> favourites;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Share> shares;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Video(String videoId, String title, String thumbnail, String link, Long views, String description, Boolean active) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.link = link;
        this.views = views;
        this.description = description;
        this.active = active;
    }

    public void addFavourite(Favourite favourite) {
        if (favourite == null) return;

        if (this.favourites == null) this.favourites = new ArrayList<>();

        Video currentVideo = favourite.getVideo();
        if (currentVideo != null) {
            if (this.favourites.contains(favourite)) return;
            currentVideo.removeFavourite(favourite);
        }

        this.favourites.add(favourite);
        favourite.setVideo(this);
    }

    public void removeFavourite(Favourite favourite) {
        if (favourite == null || this.favourites == null || this.favourites.isEmpty()) return;

        if (this.favourites.contains(favourite)) {
            this.favourites.remove(favourite);
            favourite.setVideo(null);
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

        Video currentVideo = share.getVideo();
        if (currentVideo != null) {
            if (this.shares.contains(share)) return;
            currentVideo.removeShare(share);
        }

        this.shares.add(share);
        share.setVideo(this);
    }

    public void removeShare(Share share) {
        if (share == null || this.shares == null || this.shares.isEmpty()) return;

        if (this.shares.contains(share)) {
            this.shares.remove(share);
            share.setVideo(null);
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

    public void addComment(Comment comment) {
        if (comment == null) return;

        if (this.comments == null) this.comments = new ArrayList<>();

        Video currentVideo = comment.getVideo();
        if (currentVideo != null) {
            if (this.comments.contains(comment)) return;
            currentVideo.removeComment(comment);
        }

        this.comments.add(comment);
        comment.setVideo(this);
    }

    public void removeComment(Comment comment) {
        if (comment == null || this.comments == null || this.comments.isEmpty()) return;

        if (this.comments.contains(comment)) {
            this.comments.remove(comment);
            comment.setVideo(null);
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


