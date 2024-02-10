package it.uniroma3.siw.controller;

import java.util.List;
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
import it.uniroma3.siw.controller.validator.ArtistValidator;
import it.uniroma3.siw.model.Album;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.AlbumRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class ArtistController {
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired 
	private ArtistRepository artistRepository;
	
	@Autowired 
	private AlbumRepository albumRepository;
	
	@Autowired
	private ArtistValidator artistValidator;
	
	@Autowired 
	private ArtistService artistService;
	
	
	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model, Model modelrole, Model modelalbums) {
		
		model.addAttribute("artist", this.artistRepository.findById(id).get());
		List<Album> albums = this.artistRepository.findById(id).get().getAlbums();
		modelalbums.addAttribute("albums", albums);
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
		return "artist.html";
	}
	
	
	@GetMapping("/admin/delete_artist/{id}")
	public String deleteArtist(@PathVariable("id") Long id, Model model, Model modelrole, Model modelalbums) {
		
		artistRepository.deleteById(id);
		
		model.addAttribute("artists", this.artistRepository.findAll());
		
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
		return "artists.html";
	}
	
	@GetMapping("/admin/update_artist/{id}")
	public String getUpdateArtist(@PathVariable("id") Long id, Model model, Model modelrole) {
		model.addAttribute("artist", this.artistRepository.findById(id).get());
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
		return "/admin/formUpdateArtist.html";
	}
	
	
	@GetMapping("/artist")
	public String getArtists(Model model, Model modelrole) {
		model.addAttribute("artists", this.artistRepository.findAll());
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
		return "artists.html";
	}
	
	@GetMapping(value="/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "/admin/formNewArtist.html";
	}
	
	
	@PostMapping("/admin/insert_artist")
	public String newArtist(@ModelAttribute("artist") Artist artist,BindingResult bindingResult, @RequestParam("artistImage") MultipartFile multipartFile, Model modelartists, Model modelalbums, Model modelerror) {		
				
			this.artistValidator.validate(artist, bindingResult);
				if (!bindingResult.hasErrors()) {
						this.artistService.createNewArtist(artist, multipartFile);
						modelartists.addAttribute("artists", this.artistRepository.findAll());
						modelalbums.addAttribute("albums",this.albumRepository.findAll());
						return "artists.html"; 
				} else {
					modelerror.addAttribute("errors", bindingResult.getAllErrors());
					return "/admin/formNewArtist.html";
				}				
	}
	
	@PostMapping("/admin/update_artist")
	public String updateArtist(@ModelAttribute("artist") Artist artist,BindingResult bindingResult, Model modelartists, Model modelgenres, Model modelalbums, Model modelerror) {			
					this.artistService.updateArtist(artist);
					modelartists.addAttribute("artists", this.artistRepository.findAll());
					modelalbums.addAttribute("albums",this.albumRepository.findAll());
					return "artists.html";
								
				}
		}
	
