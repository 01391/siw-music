package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review_Album;
import it.uniroma3.siw.model.Album;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;
import it.uniroma3.siw.repository.AlbumRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.utility.ReviewStatistics;

@Controller
public class ReviewController {

	@Autowired 
	private ReviewRepository reviewRepository;

	@Autowired 
	private AlbumRepository albumRepository;
	
	@Autowired 
	private CredentialsService credentialsService;

	
	private ReviewStatistics reviewStatistics;
	
	@GetMapping("/registered/formNewReview/{albumId}")
	public String formNewReview(@PathVariable("albumId") Long albumId, Model model, Model modelrole) {
		Review_Album review = new Review_Album();
		Album album = this.albumRepository.findById(albumId).get();
		review.setAlbum(album);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			String profile=credentials.getRole();
			modelrole.addAttribute("credentials",profile);
			User user = credentials.getUser();
			review.setUser(user);
		}
		model.addAttribute("review", review);
		return "/registered/formNewReview.html";
	}
	
	@GetMapping("/delete_review/{reviewId}")
	public String deleteReview(@PathVariable("reviewId") Long reviewId, Model model, Model modelrole, Model modelrating, Model modelavg, Model modelnumber, Model modeluser) {
		
		Review_Album review=this.reviewRepository.findById(reviewId).get();
		Album album = review.getAlbum();
		reviewRepository.deleteById(reviewId);
		
		double avg=0;
		int number=0;
		reviewStatistics=new ReviewStatistics();
		avg=reviewStatistics.getAverageRatingAlbum(album);
		number=reviewStatistics.getNumberRatingAlbum(album);
		album.setAvg(avg);
		album.setNumReview(number);
		int[]rating = new int[5];
		rating=reviewStatistics.getReviewRatingAlbum(album);
							
		modelrating.addAttribute("rating", rating);
	    modelavg.addAttribute("avg",avg);
	    modelnumber.addAttribute("number",number);
		model.addAttribute("album",album);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			modelrole.addAttribute("credentials",null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			String profile=credentials.getRole();
			Long userId=credentials.getUser().getId();
			modelrole.addAttribute("credentials",profile);
			modeluser.addAttribute("userId",userId);	
		}		
		return "review_album.html";
		

		
		
		
	}
	
	
	
	@PostMapping("/registered/insert_review")
	public String newReview(@ModelAttribute("review") Review_Album review, BindingResult bindingResult, Model model, Model modelrating, Model modelavg, Model modelnumber, Model modelrole, Model modeluser) {
			Album album = review.getAlbum();
			this.reviewRepository.save(review); 
			double avg=0;
			int number=0;
			reviewStatistics=new ReviewStatistics();
			avg=reviewStatistics.getAverageRatingAlbum(review.getAlbum());
			number=reviewStatistics.getNumberRatingAlbum(review.getAlbum());
			album.setAvg(avg);
			album.setNumReview(number);
			int[]rating = new int[5];
			rating=reviewStatistics.getReviewRatingAlbum(album);

			this.albumRepository.save(album);
								
			modelrating.addAttribute("rating", rating);
		    modelavg.addAttribute("avg",avg);
		    modelnumber.addAttribute("number",number);
				
			model.addAttribute("review", review);
			model.addAttribute("album",album);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication instanceof AnonymousAuthenticationToken) {
				modelrole.addAttribute("credentials",null);
			}
			else {		
				UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
				String profile=credentials.getRole();
				Long userId=credentials.getUser().getId();
				modelrole.addAttribute("credentials",profile);
				modeluser.addAttribute("userId",userId);	
			}		
			return "review_album.html";
			
			
	}

	@GetMapping("/review_all")
	public String getReviews(Model model) {
		model.addAttribute("reviews", this.reviewRepository.findAll());
		return "reviews.html";
	}
	
	@GetMapping("/review_album/{albumId}")
	public String getReviewAlbum(@PathVariable("albumId") Long albumId, Model model,Model modelrole, Model modeluser, Model modelrating, Model modelavg, Model modelnumber) {
		Album album=this.albumRepository.findById(albumId).get();
		model.addAttribute("album", album);
		int[]rating = new int[5];
		double avg=0;
		int number=0;
		reviewStatistics=new ReviewStatistics();
		rating=reviewStatistics.getReviewRatingAlbum(album);
		avg=album.getAvg();
		number=album.getNumReview();
        modelrating.addAttribute("rating", rating);
        modelavg.addAttribute("avg",avg);
        modelnumber.addAttribute("number",number);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			modelrole.addAttribute("credentials",null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			String profile=credentials.getRole();
			Long userId=credentials.getUser().getId();
			modelrole.addAttribute("credentials",profile);
			modeluser.addAttribute("userId",userId);
			
		}
        
		return "review_album.html";
	}

}
