class SimpleArray {
	public static int[] squareAll(int values[]) {
		/*
		 * TO DO: This size is not right. Fix it to work with any input array. The
		 * length of an array is accessible through an array's length field (e.g.,
		 * values.length).
		 */
		int[] newValues = values; // This allocates an array of integers.
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i] * values[i];
		}

		return newValues;
	}
}
