package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Share")
public class Share {

	@Id
	@Column(name = "ShareId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shareId;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "VideoId")
	private Video video;

    @Column(name = "Emails")
	private String receiveEmail;
	@Column(name = "ShareDate")
	private LocalDate shareDate;

	public Share(User user, Video video, String receiveEmail, LocalDate shareDate) {
		this.user = user;
		this.video = video;
		this.receiveEmail = receiveEmail;
		this.shareDate = shareDate;
	}
}
