package it.uniroma3.siw.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.Album;
import it.uniroma3.siw.model.Album_Image;
import it.uniroma3.siw.repository.AlbumImageRepository;
import it.uniroma3.siw.repository.AlbumRepository;
import jakarta.transaction.Transactional;


@Service
public class AlbumService {
	
	@Autowired
    private AlbumImageRepository albumImageRepository;
	
	@Autowired
    private AlbumRepository albumRepository;

    
    @Transactional
    public Album getAlbum(Long id){

        return this.albumRepository.findById(id).get();
    }
   
    @Transactional
    public Album createNewAlbum(Album album, MultipartFile file) {
    	try {
            if (!file.isEmpty())
            	album.setPhoto(albumImageRepository.save(new Album_Image(file.getBytes())));
        }
        catch (IOException e){}
         this.albumRepository.save(album);
        return album;
    }

}

