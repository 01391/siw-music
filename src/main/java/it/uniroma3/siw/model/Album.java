package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class Album {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String name;
	
	@Column(name = "numReview", nullable = false)
    private int numReview = 0;
	
	@Column(name = "avg", nullable = false)
    private double avg = 0;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfPublication;
	
	@ManyToOne
	private Artist artist;

	@OneToOne
	private Album_Image photo;
	
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Review_Album> reviews;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(LocalDate dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public Album_Image getPhoto() {
		return photo;
	}

	public void setPhoto(Album_Image photo) {
		this.photo = photo;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Set<Review_Album> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review_Album> reviews) {
		this.reviews = reviews;
	}

	public int getNumReview() {
		return numReview;
	}

	public void setNumReview(int numReview) {
		this.numReview = numReview;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfPublication, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		return Objects.equals(dateOfPublication, other.dateOfPublication) && Objects.equals(name, other.name);
	}

}
