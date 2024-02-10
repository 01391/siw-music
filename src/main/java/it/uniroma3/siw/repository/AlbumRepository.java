package it.uniroma3.siw.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Album;

public interface AlbumRepository extends CrudRepository<Album, Long> {

	public boolean existsByName(String name);
	/*
	public boolean existsByNameAndSurname(String name, String surname);	

	@Query(value="select * "
			+ "from artist a "
			+ "where a.id not in "
			+ "(select actors_id "
			+ "from movie_actors "
			+ "where movie_actors.starred_movies_id = :movieId)", nativeQuery=true)
	public Iterable<Artist> findActorsNotInMovie(@Param("movieId") Long id);
	*/
	
	@Query(value="select count(*) "
			+ "from review_album r "
			+ "where r.album_id =: albumId)", nativeQuery=true)
	
	public int countReviewAlbum(@Param("albumId") Long id);
	
}