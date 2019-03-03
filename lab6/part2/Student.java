package part2;


import java.util.List;

class Student {
	private final String surname;
	private final String givenName;
	private final int age;
	private final List<CourseSection> currentCourses;

	public Student(final String surname, final String givenName, final int age, final List<CourseSection> currentCourses) {
		this.surname = surname;
		this.givenName = givenName;
		this.age = age;
		this.currentCourses = currentCourses;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other != null) {
			return other.hashCode()==this.hashCode();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (surname.hashCode()+givenName.hashCode()+age)*currentCourses.hashCode();
	}
}
