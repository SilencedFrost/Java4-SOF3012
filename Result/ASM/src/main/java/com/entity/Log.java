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
@Table(name = "Logs")
public class Log {

	@Id
	@Column(name = "LogId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long LogId;

	@Column(name = "Link", length = 100)
	private String link;

	@CreationTimestamp
	@Column(name = "LoginTime", updatable = false, nullable = false)
	private LocalDateTime loginTime;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "UserId", nullable = false)
	private User user;

	public Log(String link, User user) {
		this.link = link;
		this.user = user;
	}
}
