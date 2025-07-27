package com.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Favourite", uniqueConstraints = @UniqueConstraint(name = "unique_user_video", columnNames = {"UserID", "VideoID"}))
public class Favourite {

	@Id
	@Column(name = "FavouriteId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favouriteId;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	@ManyToOne 
	@JoinColumn(name = "VideoId")
	private Video video;

	@Column(name = "LikeDate")
	private LocalDate favouriteDate;

	public Favourite(User user, Video video, LocalDate favouriteDate) {
		this.user = user;
		this.video = video;
		this.favouriteDate = favouriteDate;
	}
}
