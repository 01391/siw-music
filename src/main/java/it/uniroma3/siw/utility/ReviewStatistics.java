package it.uniroma3.siw.utility;

import java.util.Set;
import java.util.Iterator;
import it.uniroma3.siw.model.Review_Album;
import it.uniroma3.siw.model.Album;


public class ReviewStatistics {
	
	public int[] getReviewRatingAlbum(Album album) {
		Set<Review_Album> reviews=album.getReviews();
		int[]rating = new int[5];
		for (int i = 0; i < 5; i++) 
		{
	           rating[i] = 0;
	    }
		
		Iterator<Review_Album> iterator = reviews.iterator();

        while (iterator.hasNext()) {
            Review_Album element = iterator.next();
            int rating_element=element.getRating();
            rating[rating_element-1]=rating[rating_element-1]+1;  
        }
 		
		return rating;
	}
	
	public double getAverageRatingAlbum(Album album) {
		Set<Review_Album> reviews=album.getReviews();
		double avg=0;
		int n=0;
		int val=0;
		
		Iterator<Review_Album> iterator = reviews.iterator();

        while (iterator.hasNext()) {
            Review_Album element = iterator.next();
            int rating_element=element.getRating();
            if (rating_element>0)
            {
            	n=n+1;
            	val=val+rating_element;
            }   	
            if (n>0)
            {
               avg=(double) val/n;
               avg=Math.round(avg * 10.0) / 10.0;
            }
        }	
		return avg;
	}
	
	public int getNumberRatingAlbum(Album album) {
		Set<Review_Album> reviews=album.getReviews();
		int n=0;
		
		Iterator<Review_Album> iterator = reviews.iterator();

        while (iterator.hasNext()) {
            Review_Album element = iterator.next();
            int rating_element=element.getRating();
            if (rating_element>0)
            	n=n+1;
        }	
		return n;
	}
}
