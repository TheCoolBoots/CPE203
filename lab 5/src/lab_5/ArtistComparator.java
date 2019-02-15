package lab_5;

import java.util.Comparator;

public class ArtistComparator implements Comparator<Song> {

	@Override
	public int compare(Song o1, Song o2) {
		
		if (o1.getArtist().equals(o2.getArtist())) {
			return o1.getYear()-o2.getYear();
		}
		return o1.getArtist().compareTo(o2.getArtist());
		
	}

}
