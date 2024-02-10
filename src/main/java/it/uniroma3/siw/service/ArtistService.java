package it.uniroma3.siw.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Artist_Image;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.ArtistImageRepository;
import jakarta.transaction.Transactional;

@Service
public class ArtistService {
	
	
    @Autowired
    private ArtistImageRepository artistImageRepository;
    
    @Autowired
    private ArtistRepository artistRepository;
    
    
    @Transactional
    public Artist createNewArtist(Artist artist, MultipartFile file) {
    	try {
            if (!file.isEmpty())
            	artist.setPhoto(artistImageRepository.save(new Artist_Image(file.getBytes())));
        }
        catch (IOException e){}
         this.artistRepository.save(artist);
        return artist;
    }
    
    @Transactional
    public Artist updateArtist(Artist artist) {
         this.artistRepository.save(artist);
         return artist;
    }
    	
    
    
    @Transactional
    public Artist getArtist(Long id){

        return this.artistRepository.findById(id).get();
    }
   

}
