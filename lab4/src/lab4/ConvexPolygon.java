package lab4;

import java.awt.Color;
import java.awt.Point;

public class ConvexPolygon implements Shape{

	private Point[] vertices;
	private Color color;
	
	public ConvexPolygon(Point[] vertices) {
		this.vertices = vertices;
	}
	
	public Point getVertex(int index) {
		if (index<vertices.length){
			return vertices[index];
		}
		return null;
	}
	
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
		
	}

	@Override
	public double getArea() {
		
		double area = 0;
		
		for (int i=1; i<vertices.length; i++) {
			area += (vertices[i-1].getX()+vertices[i].getX())*(vertices[i-1].getY()+vertices[i].getY());
		}
		
		return area/2.0;
	}

	@Override
	public double getPerimeter() {
		double perimeter = 0;
		Point lastPoint = null;
		Point firstPoint = null;
		for (Point i:vertices) {
			if (lastPoint == null) {
				lastPoint = i;
				firstPoint = i;
			}
			else {
				perimeter += lastPoint.distance(i);
				lastPoint = i;
			}
		}
		perimeter += lastPoint.distance(firstPoint);
		
		return perimeter;
	}

	@Override
	public void translate(Point point) {
		for (Point i:vertices) {
			i.setLocation(i.getX() + point.getX(), i.getY() + point.getY());
		}
	}

}
