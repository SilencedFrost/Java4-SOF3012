package com.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Video")
public class Video {

    @Id
    @Column(name = "VideoId")
    private String videoId;
    @Column(name = "Title")
    private String title;
    @Column(name = "Poster")
    private String poster;
    @Column(name = "ViewCount")
    private Long views;
    @Column(name = "Descript")
    private String description;
    @Column(name = "Active")
    private Boolean active;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> favourites;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Share> shares;

    public Video(String videoId, String title, String poster, Long views, String description, Boolean active) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
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

    public int getUserCount() {
        return this.shares != null ? this.shares.size() : 0;
    }

    public List<Share> getShares() {
        return this.shares != null ? new ArrayList<>(this.shares) : new ArrayList<>();
    }
}


