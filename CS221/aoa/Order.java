/**
 * Takes an array and reorders it so elements are in ascending order.
 * @author mvail
 */
public class Order {

	private static long numStatements = 0;
	
	/**
	 * Take an int[] and reorganize it so they are in ascending order.
	 * @param array ints that need to be ordered 
	 */
	public static void order(int[] array) {
        for (int next = 1; next < array.length; next++) {
            int val = array[next];
            int index = next;
            while (index > 0 && val < array[index - 1]) {
                array[index] = array[index - 1];
                index--;
            }
            array[index] = val;
        }
	}
	
	/**
	 * 
	 * Modified code to help in collecting data.
	 * Take an int[] and reorganize it so they are in ascending order.
	 * @param array ints that need to be ordered 
	 */
	public static void orderMod(int[] array) {
		numStatements += 2;  //loop initialization, 1st loop condition check
        for (int next = 1; next < array.length; next++) {
        	
        	numStatements+= 4; //double assignment statement, loop increment, loop condition check
       
            int val = array[next];
            int index = next;
            
            numStatements += 3;  //loop initialization and first double condition check
            while (index > 0 && val < array[index - 1]) {
            	
            	numStatements += 4; //loop decrement, assignment statement, and check while condition again
            	
                array[index] = array[index - 1];
                index--;
            }
            
            numStatements += 1;  //assignment statement
            array[index] = val;
        }
	}
	
	/**
	 * method added just to collect data about order()
	 * @return approximate number of statements executed on last call
	 */
	public static long getStatements() {
		return numStatements;
	}
	/**
	 * method added just to reset the statement counter for collecting data about order()
	 */
	public static void resetStatements() {
		numStatements = 0;
	}
}
