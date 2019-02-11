
public class Rectangle {

	private Point bottomLeft;
	private Point topRight;
	
	public Rectangle(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}
	
	public Point getTopLeft() {
		return bottomLeft;
	}
	
	public Point getBottomRight() {
		return topRight;
	}
}
