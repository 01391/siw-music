package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Review_Album;

public interface ReviewRepository extends CrudRepository<Review_Album, Long>{
	
	public List<Review_Album> findByRating(int rating);
	
	public boolean existsByTitleAndRating(String title, Integer rating);
}
