
public class Circle {
	
	private Point p;
	private double radius;
	
	public Circle(Point p, double radius) {
		this.p = p;
		this.radius = radius;
	}

	public Point getCenter() {
		return p;
	}

	public double getRadius() {
		return radius;
	}

}
