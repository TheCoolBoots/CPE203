
public class Point {
	private final double x;
	private final double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	
	public double getRadius() {
		return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
	}
	
	public double getAngle() {
		return Math.atan(y/x);
	}
	
	public Point rotate90() {
		return new Point(y,-x);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other.getClass() == Point.class) {
			if (((Point) other).getX() == x && ((Point) other).getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Point p1 = new Point(1.1,1.2);
		Point p2 = new Point(1.1,1.2);
		
		System.out.println(p1.equals(p2));
	}
}
