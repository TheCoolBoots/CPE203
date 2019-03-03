package lab_5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases {
	private static final Song[] songs = new Song[] { 
			new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
			new Song("City and Colour", "Sleeping Sickness", 2007),
			new Song("Avett Brothers", "Talk on Indolence", 2006),
			new Song("Decemberists", "The Mariner's Revenge Song", 2005),
			new Song("Foo Fighters", "Baker Street", 1997),
			new Song("Gerry Rafferty", "Baker Street", 1998),
			new Song("Gerry Rafferty", "Baker Street", 1978),
			new Song("Queen", "Bohemian Rhapsody", 1975)};
	@Test
	public void testArtistComparator() {
	}

	@Test
	public void testLambdaTitleComparator() {
	}

	@Test
	public void testYearExtractorComparator() {
		List<Song> songList = new ArrayList<>(Arrays.asList(songs));
		List<Song> expectedList = Arrays.asList(
				new Song("City and Colour", "Sleeping Sickness", 2007),
				new Song("Avett Brothers", "Talk on Indolence", 2006),
				new Song("Decemberists", "The Mariner's Revenge Song", 2005),
				new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
				new Song("Gerry Rafferty", "Baker Street", 1998),
				new Song("Foo Fighters", "Baker Street", 1997),
				new Song("Gerry Rafferty", "Baker Street", 1978),
				new Song("Queen", "Bohemian Rhapsody", 1975));
		
		songList.sort(Comparator.comparing(Song::getYear).reversed().thenComparing(Song::getArtist));
		assertEquals(songList,expectedList);

	}
	

	@Test
	public void testComposedComparator() {
		List<Song> songList = new ArrayList<>(Arrays.asList(songs));
		List<Song> expectedList = Arrays.asList(
				new Song("City and Colour", "Sleeping Sickness", 2007),
				new Song("Avett Brothers", "Talk on Indolence", 2006),
				new Song("Decemberists", "The Mariner's Revenge Song", 2005),
				new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
				new Song("Gerry Rafferty", "Baker Street", 1998),
				new Song("Foo Fighters", "Baker Street", 1997),
				new Song("Gerry Rafferty", "Baker Street", 1978),
				new Song("Queen", "Bohemian Rhapsody", 1975));
		
		ComposedComparator compare = new ComposedComparator((s1,s2)->s2.getYear()-s1.getYear(),(s1,s2)->s1.getArtist().compareTo(s2.getArtist()));
		songList.sort(compare);
		assertEquals(songList,expectedList);
	}

	@Test
	public void testThenComparing() {
		List<Song> songList = new ArrayList<>(Arrays.asList(songs));
		List<Song> expectedList = Arrays.asList(
				new Song("City and Colour", "Sleeping Sickness", 2007),
				new Song("Avett Brothers", "Talk on Indolence", 2006),
				new Song("Decemberists", "The Mariner's Revenge Song", 2005),
				new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
				new Song("Gerry Rafferty", "Baker Street", 1998),
				new Song("Foo Fighters", "Baker Street", 1997),
				new Song("Gerry Rafferty", "Baker Street", 1978),
				new Song("Queen", "Bohemian Rhapsody", 1975));
		
		Comparator<Song> thenComparing = Comparator.comparing(Song::getYear).reversed().thenComparing(Song::getArtist);
		songList.sort(thenComparing);
		System.out.println(songList);
		assertEquals(songList,expectedList);
	}

	@Test
	public void runSort() {
		List<Song> songList = new ArrayList<>(Arrays.asList(songs));
		List<Song> expectedList = Arrays.asList(new Song("Avett Brothers", "Talk on Indolence", 2006),
				new Song("City and Colour", "Sleeping Sickness", 2007),
				new Song("Decemberists", "The Mariner's Revenge Song", 2005),
				new Song("Foo Fighters", "Baker Street", 1997),
				new Song("Gerry Rafferty", "Baker Street", 1978),
				new Song("Gerry Rafferty", "Baker Street", 1998),
				new Song("Queen", "Bohemian Rhapsody", 1975),
				new Song("Rogue Wave", "Love's Lost Guarantee", 2005));

		songList.sort((s1, s2) -> {
			if (s1.getArtist().equalsIgnoreCase(s2.getArtist())) {
				return s1.getYear() - s2.getYear();
			}
			return s1.getArtist().compareTo(s2.getArtist());
		});
		// songList.sort(new ArtistComparator());

		assertEquals(songList, expectedList);
	}
}
