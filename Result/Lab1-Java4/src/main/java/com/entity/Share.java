package com.entity;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Shares")
public class Share {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
