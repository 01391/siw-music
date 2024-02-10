package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import it.uniroma3.siw.repository.AlbumRepository;
import it.uniroma3.siw.model.Album;

@Component
public class AlbumValidator implements Validator {
	@Autowired
	private AlbumRepository albumRepository;

	@Override
	public void validate(Object o, Errors errors) {
	    Album album = (Album) o;
	    if (album.getName() != null && albumRepository.existsByName(album.getName())) {
	        errors.rejectValue("name", "album.duplicate", "ERRORE : ALBUM GIA' PRESENTE !!!");
	    }
	}
	
	
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Album.class.equals(aClass);
	}
}