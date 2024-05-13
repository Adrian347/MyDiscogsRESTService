package com.mydiscogs.restservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydiscogs.restservice.beans.Album;
import com.mydiscogs.restservice.dao.*;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

	@Autowired
	private DatabaseAccess da;
	
	// return collection of all albums
	@GetMapping(value={"","/"})	// multiple URLS for same method
	public List<Album> getAlbumCollection() {
		return da.viewAlbums();
	}
	
	// return collection of owned albums
	@GetMapping(value="/viewOwned")	
	public List<Album> getOwned() {
		return da.viewOwned();
	}
	
	// return collection of wish list albums
	@GetMapping(value="/viewWishlist")	
	public List<Album> getWishlist() {
		return da.viewWishlist();
	}
	
	// return single album element
	@GetMapping("/{id}")
	public Album getAlbum(@PathVariable Long id) {
		return da.selectAlbumById(id);
	}
	
	// create single album element
	@PostMapping(value={"","/"}, 
			headers={"Content-type=application/json"})	
			// expect a JSON record as part of request header
	public String postAlbum(@RequestBody Album album) {
		da.addAlbum(album);
		return "Art added at index " + album.getId();
	}
	
	// update single album element
	@PutMapping(value={"/{id}"}, 
			headers={"Content-type=application/json"})
	public String putAlbum(@RequestBody Album album, @PathVariable Long id) {
		album.setId(id);
		da.updateAlbumById(album);
		return "Album updated at index " + album.getId();
	}
	
	// delete album
	@DeleteMapping("/{id}")
	public String deleteArt(@PathVariable Long id) {
		if (id != null) {
			da.deleteAlbum(id);
			return "Record at index " + id + " was deleted.";
		}
		else {
			return "No record found at index " + id;
		}
	}
}


