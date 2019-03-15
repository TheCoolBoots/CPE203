import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PointEditor {
	List<Point> points;
	
	public PointEditor() {
		
	}
	
	public void importPoints(String filepath) {
		try {
			File file = new File(filepath);
			Scanner scan = new Scanner(file);
			LinkedList<Point> tempList = new LinkedList<Point>();
			String[] line;
			
			while (scan.hasNextLine()) {
				line = scan.nextLine().replaceAll(" ", "").split(",");
				System.out.println(line[0]+" "+line[1]+" "+line[2]+" ");
				
				tempList.add(new Point(Double.parseDouble(line[0]),Double.parseDouble(line[1]),Double.parseDouble(line[2])));
			}
			
			points = tempList;
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public void editPoints() {
		points = points.stream().filter(p->p.z<=2)
				.map((p)->new Point(p.x*.5,p.y*.5, p.z*.5))
				.map(p->new Point(p.x-150.0,p.y-37.0,p.z))
				.collect(Collectors.toList());
	}
	
	public void exportPointsToFile(String filepath) {
		try {
			
			File file = new File(filepath);	
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for(Point p:points) {
				writer.println(p);
			}
			writer.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PointEditor editor = new PointEditor();
		editor.importPoints("C:/Users/TheCoolBoots/Desktop/Stuff/School/CPE 203/lab8/src/positions.txt");
		editor.editPoints();
		editor.exportPointsToFile("C:/Users/TheCoolBoots/Desktop/Stuff/School/CPE 203/lab8/src/test.txt");
		System.out.println("Success");
		
	}
}
