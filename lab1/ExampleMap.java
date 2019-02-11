import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap {
	public static List<String> highEnrollmentStudents(Map<String, List<Course>> courseListsByStudentName, int unitThreshold) {
		List<String> overEnrolledStudents = new LinkedList<>();

		for (String i:courseListsByStudentName.keySet()) {
			int currentUnits = 0;
			for (int j = 0; j< courseListsByStudentName.get(i).size();j++) {
				currentUnits += courseListsByStudentName.get(i).get(j).getNumUnits();
			}
			if (currentUnits > unitThreshold) {
				overEnrolledStudents.add(i);
			}
		}

		return overEnrolledStudents;
	}
}
