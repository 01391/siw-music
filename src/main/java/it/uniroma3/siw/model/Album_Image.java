package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Album_Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    private byte[] bytes;

    private String base64Image;
    public Album_Image(){

    }
    
    public Album_Image(byte[] bytes){
        this.bytes = bytes;
    }
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }	
}
