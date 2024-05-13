package com.mydiscogs.restservice.beans;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityScan
public class Album {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String artist;
	private String title;
	private String releaseFormat;
	private String releaseYear;
	private String ownership;
}
