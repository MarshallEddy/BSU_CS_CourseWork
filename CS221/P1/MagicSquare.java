import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Has methods used to check different matrices of size n X n to tell if it is a
 * magic square.
 * 
 * Also creates a magic square and prints it to a file.
 * 
 * @author MarshallEddy
 *
 */
public class MagicSquare {
	private int n;
	public int[][] matrixN;
	private int row;
	private int col;
	private int oldRow;
	private int oldCol;
	private String is;

	/**
	 * Checks a given matrix to ensure it is a Magic Square.
	 * 
	 * @param i
	 * @param file
	 */
	public String checkMatrix(File file) {
		try {
			readMatrix(file);
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int check = 0;
		int checkRow = 0;
		int checkCol = 0;
		int sum = ((n * ((n * n) + 1)) / 2);
		int numR = 0;
		int numC = 0;
		int numD1 = 0;
		int numD2 = 0;
		int D1 = 0;
		int D2 = 0;

		for (int l = 1; l <= (n * n); l++) { // Checks all numbers from 1-n are
												// in the matrix.
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (matrixN[i][j] == l) {
						check++;
					}
				}
			}
		}

		for (int i = 0; i < n; i++) // Checks all rows add up to the int sum.
		{
			for (int j = 0; j < n; j++) {
				numR += matrixN[i][j];
			}
			// System.out.println(numR);
			if (numR == sum) {
				checkRow++;
			}
			numR = 0;
		}

		for (int i = 0; i < n; i++) // Checks all columns add up to the int sum.
		{
			for (int j = 0; j < n; j++) {
				numC += matrixN[j][i];
			}
			// System.out.println(numC);
			if (numC == sum) {
				checkCol++;
			}
			numC = 0;
		}

		for (int i = 0; i < n; i++) // Checks the diagonal (left-right) adds up
									// to int sum.
		{
			numD1 += matrixN[i][i];
		}
		if (numD1 == sum) {
			D1++;
		}

		for (int i = n - 1; i >= 0; i--) // checks the diagonal (right-left)
											// adds up to int sum.
		{
			numD2 += matrixN[i][i];
		}
		if (numD2 == sum) {
			D2++;
		}

		// System.out.println(D1 + " " + D2);
		if (check == (n * n) && (checkRow + checkCol) / 2 == n) {
			if ((D1 + D2) == 2) {
				is = "is a magic square.";
			}
		} else {
			is = "is not a magic square.";
		}
		return is;
	}

	/**
	 * Reads the file given from the command line arguments to obtain the matrix
	 * of the Magic Square.
	 * 
	 * @throws FileNotFoundException
	 */
	private void readMatrix(File file) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		try {
			n = scan.nextInt();
		} catch (InputMismatchException e) {
			System.out
					.println("Please enter a valid file with a valid matrix that uses integers.");
		}
		matrixN = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrixN[i][j] = scan.nextInt();
				System.out.print(matrixN[i][j] + " ");

			}
			System.out.println("");
		}

	}

	/**
	 * Creates a Magic Square of size 'n' given by the user. Then uses the
	 * writeMatrix() method to create a file and print a magic square to that
	 * file.
	 * 
	 * Takes in the parameters of a String and an int from the command line
	 * arguments.
	 * 
	 * The String being the files name, and the int being the size of the matrix
	 * for the magic square.
	 *
	 */
	public void createMagicSquare(String filename, int n) {
		// System.out.println(filename);
		this.n = n;
		row = n - 1;
		col = n / 2;
		matrixN = new int[n][n];
		// Scanner scanN = new Scanner(File);

		// n = scanN.nextInt();
		// System.out.println(n);
		if (n % 2 == 1) {
			for (int i = 1; i <= (n * n); i++) {
				matrixN[row][col] = i;
				oldRow = row;
				oldCol = col;

				row++;
				col++;

				if (row == n) {
					row = 0;
				}
				if (col == n) {
					col = 0;
				}
				if (matrixN[row][col] != 0) {
					row = oldRow;
					col = oldCol;
					row--;
				}
			}
		} else {
			System.out
					.println("The size of the Magic Square must be an odd number.");
		}

		try {
			writeMatrix(filename);
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a file that was named using the command line arguments that this
	 * method takes in as parameters, and then prints the magic square and size
	 * of the matrix into the file.
	 * 
	 * @throws IOException
	 */
	private void writeMatrix(String filename) throws IOException {
		File file = new File(filename);
		PrintWriter outFile = new PrintWriter(new FileWriter(file));
		// outFile.println("Size: " + n + " X " + n + "\n\nMagic Square:\n");
		outFile.println(n);
		// System.out.println("Size: " + n + " X " + n + "\n\nMagic Square:\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// System.out.print(matrixN[i][j]);
				// System.out.print(" ");
				outFile.print(matrixN[i][j]);
				outFile.print(" ");
			}
			// System.out.println(" ");
			outFile.println(" ");
		}
		outFile.close();
	}
}