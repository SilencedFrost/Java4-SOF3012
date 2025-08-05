package com.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "UserId", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VideoId", nullable = false)
	private Video video;

    @Column(name = "Emails", nullable = false, length = 50)
	private String receiveEmail;
	@CreationTimestamp
	@Column(name = "ShareDate", updatable = false, nullable = false)
	private LocalDateTime shareDate;

	public Share(User user, Video video, String receiveEmail) {
		this.user = user;
		this.video = video;
		this.receiveEmail = receiveEmail;
	}

	@PrePersist
	@PreUpdate
	private void normalizeEmail() {
		if (receiveEmail != null) {
			this.receiveEmail = receiveEmail.toLowerCase().trim();
		}
	}
}
