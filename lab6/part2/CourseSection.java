package part2;


import java.time.LocalTime;

class CourseSection {
	private final String prefix;
	private final String number;
	private final int enrollment;
	private final LocalTime startTime;
	private final LocalTime endTime;

	public CourseSection(final String prefix, final String number, final int enrollment, final LocalTime startTime,
			final LocalTime endTime) {
		this.prefix = prefix;
		this.number = number;
		this.enrollment = enrollment;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object other) {
		if (other != null) {
			if (this.hashCode() == ((CourseSection) other).hashCode()) {
				return true;
			}
		}
		return false;
	}

	
	@Override
	public int hashCode() {
		//System.out.println((prefix.hashCode()+number.hashCode()+enrollment+startTime.hashCode())*endTime.hashCode());
		return (prefix.hashCode()+number.hashCode()+enrollment+startTime.hashCode())*endTime.hashCode();
	}

}
