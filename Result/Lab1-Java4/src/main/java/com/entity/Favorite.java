package com.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Favorite",
		uniqueConstraints = @UniqueConstraint(
			name = "unique_user_video",
			columnNames = {"UserID", "VideoID"}
	))
public class Favorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favouriteId;
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	@ManyToOne 
	@JoinColumn(name = "VideoId")
	private Video video;
	@Column(name = "LikeDate")
	private Date likeDate;
	}
