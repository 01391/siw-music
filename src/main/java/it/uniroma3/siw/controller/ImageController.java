package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.model.Artist_Image;
import it.uniroma3.siw.model.Album_Image;
import it.uniroma3.siw.repository.AlbumImageRepository;
import it.uniroma3.siw.repository.ArtistImageRepository;

@Controller
public class ImageController {

	 	@Autowired
	    ArtistImageRepository artistImageRepository;
	 
	 	@Autowired
	    AlbumImageRepository albumImageRepository;

	    @GetMapping("/display/artist/{id}")
	    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
	        Artist_Image img = this.artistImageRepository.findById(id).get();
	        byte[] image = img.getBytes();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG);
	        return new ResponseEntity<>(image, headers, HttpStatus.OK);
	    }
	    
	    @GetMapping("/display/album/{id}")
	    public ResponseEntity<byte[]> displayItemAlbumImage(@PathVariable("id") Long id) {
	        Album_Image img = this.albumImageRepository.findById(id).get();
	        byte[] image = img.getBytes();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG);
	        return new ResponseEntity<>(image, headers, HttpStatus.OK);
	    }
	
}
