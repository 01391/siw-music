package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.AlbumRepository;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
	
    @Autowired 
   	private ArtistRepository artistRepository;
    
    @Autowired 
   	private AlbumRepository albumRepository;
        
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}
	
	@GetMapping(value = "/loginerror") 
	public String showLoginError () {
		return "loginError.html";
	}
	
	@GetMapping(value = "/genre") 
	public String showGenre () {
		return "categories.html";
	}
	
	
	@GetMapping(value = "/") 
	public String index(Model modelartists, Model modelgenres, Model modelalbums) {		
		modelartists.addAttribute("artists", this.artistRepository.findAll());
		modelalbums.addAttribute("albums",this.albumRepository.findAll());
		return "index.html";
	}	
	
	
	@GetMapping(value = "/success")
    public String defaultAfterLogin(Model modelartists, Model modelgenres, Model modelrole, Model modelalbums) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		String profile=credentials.getRole();
		modelartists.addAttribute("artists", this.artistRepository.findAll());
		modelalbums.addAttribute("albums",this.albumRepository.findAll());
		modelrole.addAttribute("credentials",profile);
		return "index.html";
    }
	
	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	if (userService.saveUser(user, credentials)!=null)
        	{
        		credentials.setUser(user);
                credentialsService.saveCredentials(credentials);
                return "/login";
        	} 
        	else
        	{
        		return "usernameError.html";
        	}
        }
        return "registrationError.html";
    }
}