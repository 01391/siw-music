package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.controller.validator.AlbumValidator;
import it.uniroma3.siw.model.Album;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.AlbumRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.AlbumService;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class AlbumController {
	
	@Autowired
	private CredentialsService credentialsService;
	
    @Autowired 
	private ArtistRepository artistRepository;
    
	@Autowired 
	private AlbumRepository albumRepository;
	
	@Autowired 
	private AlbumService albumService;
	

	@Autowired 
	private ArtistService artistService;
	
	@Autowired
	private AlbumValidator albumValidator;
	
	@GetMapping("/album/{id}")
	public String getAlbum(@PathVariable("id") Long id, Model model, Model modelrole, Model modeltracklist) {
		
		model.addAttribute("album", this.albumRepository.findById(id).get());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			modelrole.addAttribute("credentials",null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			String profile=credentials.getRole();
			modelrole.addAttribute("credentials",profile);
		}
		return "review_album.html";
	}
	
	@GetMapping("/album")
	public String getAlbums(Model model, Model modelrole) {
		model.addAttribute("albums", this.albumRepository.findAll());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			modelrole.addAttribute("credentials",null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			String profile=credentials.getRole();
			modelrole.addAttribute("credentials",profile);
		}
		return "albums.html";
	}

	
	@GetMapping("/admin/delete_album/{id}")
	public String deleteAlbum(@PathVariable("id") Long id, Model model, Model modelrole, Model modelalbums) {
		
		albumRepository.deleteById(id);
		model.addAttribute("albums", this.albumRepository.findAll());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			modelrole.addAttribute("credentials",null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			String profile=credentials.getRole();
			modelrole.addAttribute("credentials",profile);
		}
		return "albums.html";
	}
	
	@GetMapping(value="/admin/formNewAlbum/{id}")
	public String formNewAlbum(@PathVariable("id") Long id, Model model) {
		
		Artist artist = artistService.getArtist(id);
		Album album=new Album();
		album.setArtist(artist);
		model.addAttribute("album", album);
		model.addAttribute("artist", artist);
		return "/admin/formNewAlbum.html";
	}
	
	
	@PostMapping("/admin/insert_album")
	public String newAlbum(@ModelAttribute("album") Album album,BindingResult bindingResult, @RequestParam("albumImage") MultipartFile multipartFile, Model modelartists, Model modelalbums, Model modelerror) {		
				
			this.albumValidator.validate(album, bindingResult);
				if (!bindingResult.hasErrors()) {
						this.albumService.createNewAlbum(album, multipartFile);
						modelartists.addAttribute("artists", this.artistRepository.findAll());
						modelalbums.addAttribute("albums",this.albumRepository.findAll());
						return "albums.html"; 
				} else {
					modelerror.addAttribute("errors", bindingResult.getAllErrors());
					return "/admin/formNewAlbum.html";
				}				
	}

}
