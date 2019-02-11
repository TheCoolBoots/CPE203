import java.util.List;

public class Util {
	
	public static double perimeter(Circle circle) {
		return Math.PI*Math.pow(circle.getRadius(),2);
	}
	
	public static double perimeter(Rectangle rect) {
		Point p1 = rect.getTopLeft();
		Point p2 = rect.getBottomRight();
		
		return 2 * Math.abs(p1.getX()-p2.getX()) + 2 * Math.abs(p1.getY() - p2.getY());
	}
	
	public static double perimeter(Polygon poly) {
		List<Point> points = poly.getPoints();
		
		Point firstPoint = points.get(0);
		Point lastPoint = points.get(0);
		double perimeter = 0;
		for (int i = 1; i < points.size(); i ++) {
			perimeter += Math.sqrt(Math.pow(lastPoint.getX() - points.get(i).getX(), 2.0)
					+ Math.pow(lastPoint.getY() - points.get(i).getY(), 2.0));
			lastPoint = points.get(i);
		}
		perimeter += Math.sqrt(Math.pow(lastPoint.getX() - firstPoint.getX(), 2.0)
				+ Math.pow(lastPoint.getY() - firstPoint.getY(), 2.0));
		
		return perimeter;
		
	}
}
