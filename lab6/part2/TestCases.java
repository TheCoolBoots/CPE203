package part2;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

public class TestCases {
	@Test
	public void testExercise1() {
		final CourseSection one = new CourseSection("CSC", "203", 35, LocalTime.of(9, 40), LocalTime.of(11, 0));
		final CourseSection two = new CourseSection("CSC", "203", 35, LocalTime.of(9, 40), LocalTime.of(11, 0));
		assertTrue(one.equals(two));
		assertTrue(two.equals(one));
	}

	@Test
	public void testExercise2() {
		final CourseSection one = new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0));
		final CourseSection two = new CourseSection("CSC", "203", 35, LocalTime.of(1, 10), LocalTime.of(2, 0));

		assertFalse(one.equals(two));
		assertFalse(two.equals(one));
	}

	@Test
	public void testExercise3() {
		final CourseSection one = new CourseSection("CSC", "203", 35, LocalTime.of(9, 40), LocalTime.of(11, 0));
		final CourseSection two = new CourseSection("CSC", "203", 35, LocalTime.of(9, 40), LocalTime.of(11, 0));
		ArrayList<CourseSection> courselist = new ArrayList<CourseSection>();
		courselist.add(one);
		courselist.add(two);
		final Student s1 = new Student("TheCoolBoots", "Andrew", 19, courselist);
		final Student s2 = new Student("TheCoolBoots", "Andrew", 19, courselist);

		assertEquals(one.hashCode(), two.hashCode());
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));
	}

}
