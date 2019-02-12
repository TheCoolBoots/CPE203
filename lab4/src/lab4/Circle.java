package lab4;

import java.awt.Color;
import java.awt.Point;

public class Circle implements Shape{
	
	private Point p;
	private double radius;
	private Color color;	
	
	public Circle(double radius, Point p, Color color) {
		this.p = p;
		this.radius = radius;
		this.color = color;
	}

	public Point getCenter() {
		return p;
	}

	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double getPerimeter() {
		return Math.PI*2*radius;
	}
	
	public double getArea() {
		return Math.PI*radius*radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color=color;
	}


	public void translate(Point point) {
		this.p.setLocation(p.getX()+point.getX(), p.getY()+point.getY());
	}

}
