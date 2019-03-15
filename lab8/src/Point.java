import java.text.DecimalFormat;

public class Point {

	public final double x;
	public final double y;
	public final double z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.##############");
		return f.format(x) + ", " + f.format(y) + ", "+ f.format(z);
	}

}
