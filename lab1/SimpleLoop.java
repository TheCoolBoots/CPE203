class SimpleLoop {
	public static int sum(int low, int high) {
		
		if (high<low) {
			return 0;
		}
		
		int sum = 0;
		
		for (int i=low; i<high+1; i++) {
			sum += i;
		}

		return sum;
	}
}
