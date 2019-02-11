import java.util.List;

public class Polygon {

	private List<Point> vertices;
	
	public Polygon(List<Point> vertices) {
		this.vertices = vertices;
	}

	public List<Point> getPoints() {
		return vertices;
	}
	
	
}
