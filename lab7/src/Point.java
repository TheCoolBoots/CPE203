public class Point
{
   public final int x;
   public final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   
   public String toString() {
	   return "("+x+", "+y+")";
   }
   
   public Point getLeft() {
	   return new Point(x-1,y);
   }
   
   public Point getRight() {
	   return new Point(x+1,y);
   }
   
   public Point getTop() {
	   return new Point(x,y-1);
   }
   
   public Point getBot() {
	   return new Point(x,y+1);
   }
}
