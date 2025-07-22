package com.entity;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

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
	private Date shareDate;
}
