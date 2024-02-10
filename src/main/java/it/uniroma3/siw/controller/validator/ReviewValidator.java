package it.uniroma3.siw.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;


import it.uniroma3.siw.model.Review_Album;

@Component
public class ReviewValidator implements Validator {
	
	@Override
	public void validate(Object o, Errors errors) {
	    Review_Album review = (Review_Album) o;
	    if (review.getRating() == null) {
	        errors.rejectValue("title", "" ,"ERRORE : RATING NON SELEZIONATO !!!");
	    }
	}
	
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Review_Album.class.equals(aClass);
	}
}