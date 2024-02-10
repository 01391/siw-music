package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
public class Review_Album {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String text;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateInsert;

	
	@Min(1)
    @Max(5)
	private Integer rating;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Album album;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(LocalDate dateInsert) {
		this.dateInsert = dateInsert;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rating, text, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review_Album other = (Review_Album) obj;
		return Objects.equals(rating, other.rating) && Objects.equals(text, other.text)
				&& Objects.equals(title, other.title);
	}

}
