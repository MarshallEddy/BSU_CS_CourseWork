/**
 * Returns index of a value in an int[] or -1 if it isn't found.
 * @author mvail
 */
public class Find {
	public static long numStatements = 0;
	
	/**
	 * Return index where value is found in array or -1 if not found.
	 * @param array ints where value may be found
	 * @param value int that may be in array
	 * @return index where value is found or -1 if not found
	 */
	public static int find(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Modified code to help in collecting data.
	 * Return index where value is found in array or -1 if not found.
	 * @param array ints where value may be found
	 * @param value int that may be in array
	 * @return index where value is found or -1 if not found
	 */
	public static int findMod(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			numStatements+= 3;	//initialize, increment, and check
			numStatements+= 1;	//condition statement
			if (array[i] == value) {
				return i;
			}
		}
		numStatements+= 1;	//return statement
		return -1;
	}
	/**
	 * method added just to collect data about find()
	 * @return approximate number of statements executed on last call
	 */
	public static long getStatements() {
		return numStatements;
	}
	/**
	 * method added just to reset the statement counter for collecting data about find()
	 */
	public static void resetStatements() {
		numStatements = 0;
	}
}

