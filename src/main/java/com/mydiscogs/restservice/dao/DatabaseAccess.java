package com.mydiscogs.restservice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mydiscogs.restservice.beans.*;

@Repository
public class DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	// add album to database
	public long addAlbum(Album album) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String insert = "INSERT INTO albums(artist, title, releaseFormat, releaseYear, "
				+ "ownership) \r\n VALUES (:artist, :title, :releaseFormat, :releaseYear, "
				+ ":ownership);";
		namedParameters.addValue("artist", album.getArtist());
		namedParameters.addValue("title", album.getTitle());
		namedParameters.addValue("releaseFormat", album.getReleaseFormat());
		namedParameters.addValue("releaseYear", album.getReleaseYear());
		namedParameters.addValue("ownership", album.getOwnership());
		int rowsAffected = jdbc.update(insert, namedParameters);
		return rowsAffected;
	}
	
	// view albums in database
	public List<Album> viewAlbums() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM albums ORDER BY artist ASC;";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Album>(Album.class));
	}
	
	// Method to return list of owned albums
	public List<Album> viewOwned() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM albums WHERE ownership = 'Owned' ORDER BY artist ASC;";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Album>(Album.class));
	}
	
	// Method to return list of wishlist albums
	public List<Album> viewWishlist() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM albums WHERE ownership = 'Wishlist' ORDER BY artist ASC;";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Album>(Album.class));
	}

	// Method to search database by name for album to be edited
	public Album selectAlbumById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		Album album;
		String albumById = "SELECT * FROM albums WHERE id = :id;";
		namedParameters.addValue("id", id);
		List<Album> albums = jdbc.query(albumById, namedParameters, 
				new BeanPropertyRowMapper<Album>(Album.class));
		if (albums.size() > 0) {
			album = albums.get(0);
		}
		else {
			album = new Album();
		}
		return album;
	}
	
	//Method to update database for edited album.
	public long updateAlbumById(Album album) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String updateAlbum = "UPDATE albums SET "
				+ "artist = :artist, "
				+ "title = :title, releaseFormat = "
				+ ":releaseFormat, releaseYear = :releaseYear, ownership = :ownership "
				+ "WHERE id = :id;";
		namedParameters.addValue("artist", album.getArtist());
		namedParameters.addValue("title", album.getTitle());
		namedParameters.addValue("releaseFormat", album.getReleaseFormat());
		namedParameters.addValue("releaseYear", album.getReleaseYear());
		namedParameters.addValue("ownership", album.getOwnership());
		namedParameters.addValue("id", album.getId());
		int rowsUpdated = jdbc.update(updateAlbum, namedParameters);
		return rowsUpdated;
	}
	
	//Method to delete an Album from the database based on ID.
	public long deleteAlbum(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String delete = "DELETE FROM albums WHERE id = :id;";
		namedParameters.addValue("id", id);
		int rowsAffected = jdbc.update(delete, namedParameters);	
		return rowsAffected;
	}
}

