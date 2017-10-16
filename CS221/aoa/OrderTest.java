import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Put an array of ints in ascending order.
 * @author mvail
 */
public class OrderTest {
	/**
	 * Get an array of specified size and pass it to Order.order().
	 * Report the results.
	 * @param args either size of a new array, or a file containing ints
	 */
	public static void main(String[] args) {
		//Original Commented out code//
//		if (args.length != 1) {
//			System.out.println("Usage:  java OrderTest sizeOfArray\n"
//					+ "\tor\n\tjava OrderTest arrayFile");
//			System.exit(1);
//		}
		
		// create or read the int[]
		int size = 0;
		int[] array = new int[0];
//		try {
//			size = Integer.parseInt(args[0]);
//			array = ArrayOfInts.randomizedArray(size);
//		} catch (NumberFormatException nfe) {
//			try {
//				array = ArrayOfInts.arrayFromFile(args[0]);
//				size = array.length;
//			} catch (Exception e) {
//				System.err.println("unable to read array from " + args[0]);
//				System.exit(1);
//			}
//		}
//		
//		System.out.println("before:");
//		for (int i = 0; i < array.length; i++) {
//			System.out.printf(((i+1) % 10 > 0) ? " %d" : " %d\n", array[i]);
//		}
//		
//		Order.order(array); //this is the call we want to measure
//		
//		System.out.println("\nafter:");
//		for (int i = 0; i < array.length; i++) {
//			System.out.printf(((i+1) % 10 > 0) ? " %d" : " %d\n", array[i]);
//		}
		
			//Modified Code to collect data//
		try {
			PrintStream outfile = new PrintStream("Order.csv");
			outfile.println("size, statements");
			for (size = 1000; size < 50001; size += 10000) {
				array = ArrayOfInts.randomizedArray(size);
				Order.resetStatements();
				Order.orderMod(array);
				long statements = Order.getStatements();
				outfile.println(size + ", " + statements);

				System.out.printf("\nfor n = %d, took %d statements to order the elemnts of the array into ascending order. \n",
						size, statements);
				if(size <= 1000) {
					size -= 1000;
				}
			}
			outfile.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}
}
