package base;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

public class GetNeighbors implements Function<Point, Stream<Point>>{

	@Override
	public Stream<Point> apply(Point t) {
		ArrayList<Point> neighbors = new ArrayList<Point>();
		neighbors.add(new Point(t.getX()+1,t.getY()));
		neighbors.add(new Point(t.getX()-1,t.getY()));
		neighbors.add(new Point(t.getX(),t.getY()+1));
		neighbors.add(new Point(t.getX(),t.getY()-1));
		return neighbors.stream();
	}

}
