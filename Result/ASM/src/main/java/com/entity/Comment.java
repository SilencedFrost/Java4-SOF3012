package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Comments")
public class Comment {

	@Id
	@Column(name = "CommentId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "UserId", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VideoId", nullable = false)
	private Video video;

	@CreationTimestamp
	@Column(name = "CommentDate", updatable = false, nullable = false)
	private LocalDateTime commentDate;

	@Column(name = "CommentContent", nullable = false, length = 500)
	private String commentContent;

	public Comment(User user, Video video, String commentContent) {
		this.user = user;
		this.video = video;
		this.commentContent = commentContent;
	}
}
