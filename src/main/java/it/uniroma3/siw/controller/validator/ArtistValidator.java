package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.model.Artist;

@Component
public class ArtistValidator implements Validator {
	@Autowired
	private ArtistRepository artistRepository;

	@Override
	public void validate(Object o, Errors errors) {
	    Artist artist = (Artist) o;
	    if (artist.getName() != null && artistRepository.existsByName(artist.getName())) {
	        errors.rejectValue("name", "artist.duplicate", "ERRORE : ARTISTA GIA' PRESENTE !!!");
	    }
	}
	
	
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Artist.class.equals(aClass);
	}
}