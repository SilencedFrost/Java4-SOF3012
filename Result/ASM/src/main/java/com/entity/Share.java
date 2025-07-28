package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	private String email;
	@Column(name = "ShareDate")
	private LocalDate shareDate;
}
