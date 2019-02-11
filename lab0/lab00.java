
public class lab00 {
	public static void main(String[]args) {
		// declaring and initializing some variables
		int x = 5;
		String y = "hello";
		double z = 9.8;
		
		// printing the variables
		System.out.println("x: "+ x+ " y: "+ y+ " z: "+ z);
		
		// a list (make an array in java)
	    int[] nums = {3, 6, -1, 2};
	    for (int num:nums) {
	    	System.out.println(num);
	    }
	    
	    // call a function
	    int numFound = charCount(y, 'l')   ;
	    System.out.println("Found: "+ numFound);
	    
	    // a counting for loop
	    for (int i=1;i<11;i++) {
	    	System.out.print(i+" ");
	    }
	    System.out.println();
	}
	
	static int charCount(String y, char target) {
		int count = 0;
		for (int i=0; i < y.length(); i++) {
			if (y.charAt(i) == target) {
				count++;
			}
		}
		return count;
	}
	
}
