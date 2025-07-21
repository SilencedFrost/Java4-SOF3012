package com.entity;

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
    private long views;
    @Column(name = "Descript")
    private String description;
    @Column(name = "Active")
    private boolean active;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Favourite> favourites;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Share> shares;

    public void addFavourite(Favourite favourite){
        this.favourites.add(favourite);
        favourite.setVideo(this);
    }

    public void addShare(Share share){
        this.shares.add(share);
        share.setVideo(this);
    }
}


