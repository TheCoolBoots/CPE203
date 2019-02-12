package lab4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class WorkSpace {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>(10);
	
	public WorkSpace() {
		
	}
	
	public void add(Shape shape) {
		shapes.add(shape);
	}
	
	public Shape get(int index) {
		if (index<shapes.size())
			return shapes.get(index);
		return null;
	}
	
	public int size() {
		return shapes.size();
	}
	
	public ArrayList<Circle> getCircles(){
		
		ArrayList<Circle> circles = new ArrayList<Circle>();
		
		for (Shape i:shapes) {
			if (i instanceof Circle) {
				circles.add((Circle) i);
			}
		}
		
		return circles;
	}
	public ArrayList<Rectangle> getRectangles(){
		
		ArrayList<Rectangle> circles = new ArrayList<Rectangle>();
		
		for (Shape i:shapes) {
			if (i instanceof Rectangle) {
				circles.add((Rectangle) i);
			}
		}
		
		return circles;
	}
	
	public ArrayList<Triangle> getTriangles(){
		
		ArrayList<Triangle> Triangles = new ArrayList<Triangle>();
		
		for (Shape i:shapes) {
			if (i instanceof Triangle) {
				Triangles.add((Triangle) i);
			}
		}
		
		return Triangles;
	}
	
	public ArrayList<ConvexPolygon> getConvexPolygons(){
		
		ArrayList<ConvexPolygon> ConvexPolygons = new ArrayList<ConvexPolygon>();
		
		for (Shape i:shapes) {
			if (i instanceof ConvexPolygon) {
				ConvexPolygons.add((ConvexPolygon) i);
			}
		}
		
		return ConvexPolygons;
	}
	
	public ArrayList<Shape> getShapesByColor(Color color){
		ArrayList<Shape> shape = new ArrayList<Shape>();
		
		for (Shape i:shapes) {
			if(i.getColor().equals(color)) {
				shape.add(i);
			}
		}
		
		return shape;
	}
	
	public double getAreaOfAllShapes() {
		double area = 0;
		
		for (Shape i:shapes) {
			area += i.getArea();
		}
		
		return area;
	}
	
	public double getPerimeterOfAllShapes() {
		double area = 0;
		
		for (Shape i:shapes) {
			area += i.getPerimeter();
		}
		
		return area;
	}
}
