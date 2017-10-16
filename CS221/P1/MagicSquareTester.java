import java.io.File;

/**
 * Driver class used to test the MagicSquare class.
 * 
 * @author MarshallEddy
 *
 */
public class MagicSquareTester {
	public static void main(String[] args) {
		MagicSquare bob = new MagicSquare();
		if (args.length == 2 || args.length == 3) {
			if (args[0].equals("-check")) {
				if (args.length == 3) {
					System.out.println("Please only enter valid arguments.");
				} else {
					File file = new File(args[1]);
					if (file.canRead() == false) {
						System.out.println("Please enter a valid file.");
					} else {
						System.out.println("The Matrix: \n");
						String is = bob.checkMatrix(file);
						System.out.println("\n" + is);
					}
				}
			}

			else if (args[0].equals("-create")) {
				String filename = args[1];
				int n = Integer.parseInt(args[2]);
				bob.createMagicSquare(filename, n);
			}

			else {
				System.out
						.println("Please enter correct flag(-create, or -check");
			}
		}

		else {
			System.out.println("Please only enter valid arguments");
		}
	}
}
