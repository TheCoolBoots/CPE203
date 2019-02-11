import java.io.EOFException;
import java.io.FileNotFoundException;

public class ExceptWhat {
	private static void exceptional(boolean isBad) throws EOFException {
		if (isBad) {
			throw new EOFException();
		}
	}

	private static void outstanding() throws FileNotFoundException {
		throw new FileNotFoundException();
	}

	private static void fantastic() {
		throw new NullPointerException();
	}

	private static void intermediate3(boolean isBad) throws EOFException, FileNotFoundException {
		// This method should not attempt to recover from any exceptions.
		exceptional(isBad);
		outstanding();
		fantastic();
	}

	private static void intermediate2(boolean isBad) throws FileNotFoundException {
		/*
		 * This method should handle EOFExceptions and print
		 * "intermediate 2 - eof handled" when so doing. But it is not capable of
		 * recovering from any other exceptions.
		 */
		try {
			intermediate3(isBad);
		} catch (EOFException e) {
			System.out.println("intermediate 2 -veof handled");
		}
	}

	private static void intermediate1(boolean doIt) {
		/*
		 * This method should handle FileNotFoundExceptions and print
		 * "intermediate 1 - handling missing file" when so doing. But this method is
		 * not capable of recovering from any other exceptions.
		 */
		try {
			intermediate2(doIt);
		} catch (FileNotFoundException e) {
			System.out.println("intermediate 1 - handling missing file");
		}
		
	}

	private static void start1() {
		// Does not attempt to recover from any exceptions.
		intermediate1(true);
	}

	private static void start2() {
		// Does not attempt to recover from any exceptions.
		intermediate1(false);
	}

	public static void main(String[] args) {
		/*
		 * Though there are exceptions to most rules, let's establish for now that
		 * checked exceptions should not escape main. As such, handle any checked
		 * exceptions raised below by printing the "main handling " and the type of
		 * exception.
		 */

		start1();
		start2();
	}
}
