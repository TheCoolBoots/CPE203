package lab4;

import java.awt.Color;
import java.awt.Point;

public class Rectangle implements Shape {

	private Point topLeft;
	private double height;
	private double width;
	private Color color;

	public Rectangle(double width, double height, Point topLeft, Color color) {
		this.topLeft = topLeft;
		this.setWidth(width);
		this.setHeight(height);
		this.color = color;
	}

	public Point getTopLeft() {
		return topLeft;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;

	}

	public double getArea() {
		return width * height;
	}

	public double getPerimeter() {
		return 2 * width + 2 * height;
	}

	public void translate(Point point) {
		this.topLeft = point;

	}

}
