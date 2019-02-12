package lab4;

import java.awt.Color;
import java.awt.Point;

public class Triangle implements Shape{
	
	//Points start at a and go counterclockwise to c
	Point a;
	Point b;
	Point c;
	Color color;
	
	public Triangle(Point A, Point B, Point C, Color color) {
		this.a = A;
		this.b = B;
		this.c = C;
		this.color = color;
	}
	
	public Point getVertexA() {
		return a;
	}
	public Point getVertexB() {
		return b;
	}
	public Point getVertexC() {
		return c;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		
	}

	public double getArea() {
		return Math.abs(a.getX()*(b.getY()-c.getY()) + b.getX()*(c.getY()-a.getY()) + c.getX()*(a.getY()-b.getY()))/2;
	}

	public double getPerimeter() {
		return a.distance(b)+b.distance(c)+c.distance(a);
	}

	public void translate(Point point) {
		a.setLocation(a.getX()+point.getX(), a.getY()+point.getY());
		b.setLocation(b.getX()+point.getX(), b.getY()+point.getY());
		c.setLocation(c.getX()+point.getX(), c.getY()+point.getY());
	}
}
