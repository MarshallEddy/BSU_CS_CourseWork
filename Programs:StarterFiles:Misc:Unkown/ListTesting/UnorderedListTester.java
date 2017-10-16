import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A unit test class for UnorderedListADT.
 * This is a set of black box tests that should work for
 *  any implementation of UnorderedListADT.
 * 
 * NOTE: One example test is given for each UnorderedListADT method
 * using a new list to get you started.
 * 
 * @author mvail
 */
public class UnorderedListTester {
	//you don't have to use these constants, but they may help
	private static final Integer ELEMENT_A = new Integer(1);
	private static final Integer ELEMENT_B = new Integer(2);
	private static final Integer ELEMENT_C = new Integer(3);
	private static final Integer ELEMENT_D = new Integer(4);
	
	//instance variables for tracking test results
	private int passes = 0;
	private int failures = 0;
	private int total = 0;

	//choice of which list to use
	private static enum ListChoice {good, bad}
	private static final ListChoice listToUse = ListChoice.good; //TODO: choose list here
	
	/** @param args not used */
	public static void main(String[] args) {
		//to avoid every method being static
		UnorderedListTester tester = new UnorderedListTester();
		tester.runTests();
	}
	
	/** Print test results in a consistent format
	 * @param testDesc description of the test
	 * @param result indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) {
		total++;
		if (result) {
			passes++;
		} else {
			failures++;
		}
		System.out.printf("%-46s\t%s\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/** Print a final summary */
	private void printFinalSummary() {
		System.out.printf("\nTotal Tests: %d,  Passed: %d,  Failed: %d\n", total, passes, failures);
	}
	
	/**
	 * Run tests to confirm required functionality from list constructors and methods
	 * 
	 * XXX <- tag for navigation. see the blue box to the right of the scroll bar?
	 */
	private void runTests() {
		///////////////////////////////
		// TESTS ON A NEW, EMPTY LIST
		///////////////////////////////
		//ListADT methods
		printTest("newList_testRemoveFirst", newList_testRemoveFirst());
		printTest("newList_testRemoveLast", newList_testRemoveLast());
		printTest("newList_testRemoveElement", newList_testRemoveElement());
		printTest("newList_testFirst", newList_testFirst());
		printTest("newList_testLast", newList_testLast());
		printTest("newList_testContains", newList_testContains());
		printTest("newList_testIsEmpty", newList_testIsEmpty());
		printTest("newList_testSize", newList_testSize());
		printTest("newList_testToString", newList_testToString());
		//Iterator methods
		printTest("newList_testIterator", newList_testIterator());
		printTest("newList_testIteratorHasNext", newList_testIteratorHasNext());
		printTest("newList_testIteratorNext", newList_testIteratorNext());
		printTest("newList_testIteratorRemove", newList_testIteratorRemove());
		//UnorderedListADT methods
		printTest("newList_testAddToFront", newList_testAddToFront());
		printTest("newList_testAddToRear", newList_testAddToRear());
		printTest("newList_testAddAfter", newList_testAddAfter());
		
		////////////////////////////
		// TESTS ON (next scenario)
		////////////////////////////
		
		//recommended test naming: start_change_result_testName
		// e.g.	empty_addToFrontA_A_testSize
		//		A_removeFirst_empty_testFirst
		//		AB_addToFrontC_CAB_testRemoveFirst

		/////////////////
		//final verdict
		/////////////////
		printFinalSummary();
	}
	
	///////////////////////////////
	// Scenario Set-up Methods XXX
	///////////////////////////////
	
	/**
	 * Returns a UnorderedListADT for the "new empty list" scenario
	 *
	 * Note: method returns a basic ListADT reference that can be
	 * cast to a more specific interface type in each test method.
	 * This feels like needless overhead, now, but pays off when we
	 * get to testing lists that implement multiple interfaces.
	 *
	 * @return a new UnorderedListADT
	 */
	private ListADT<Integer> newList() {
		switch (listToUse) {
		case good:
			return new GoodUnorderedList<Integer>();
		case bad:
			return new BadUnorderedList<Integer>();
		default:
			return null;
		}
	}
	
	/////////////////////////////////
	// Scenario: NEW EMPTY LIST XXX
	/////////////////////////////////

	/** @return test success */
	private boolean newList_testRemoveFirst() {
		try {
			ListADT<Integer> list = newList();
			list.removeFirst();
			return false;
		} catch (EmptyCollectionException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveFirst", "EmptyCollectionException", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testRemoveLast() {
		try {
			ListADT<Integer> list = newList();
			list.removeLast();
			return false;
		} catch (EmptyCollectionException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveLast", "EmptyCollectionException", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testRemoveElement() {
		try {
			ListADT<Integer> list = newList();
			list.remove(ELEMENT_C);
			return false;
		} catch (ElementNotFoundException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveElement", "ElementNotFoundException", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testFirst() {
		try {
			ListADT<Integer> list = newList();
			list.first();
			return false;
		} catch (EmptyCollectionException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testFirst", "EmptyCollectionException", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testLast() {
		try {
			ListADT<Integer> list = newList();
			list.last();
			return false;
		} catch (EmptyCollectionException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testLast", "EmptyCollectionException", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testContains() {
		try {
			ListADT<Integer> list = newList();
			return (list.contains(ELEMENT_C) == false);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testContains", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testIsEmpty() {
		try {
			ListADT<Integer> list = newList();
			return (list.isEmpty() == true);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testIsEmpty", e.toString());
			return false;
		}
	}
	
	/** @return test success */
	private boolean newList_testSize() {
		try {
			ListADT<Integer> list = newList();
			return (list.size() == 0);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testSize", e.toString());
			return false;
		}
	}
	
	/** toString() is difficult to test - would like to confirm that
	 * the default address output has been overridden
	 * @return test success */
	private boolean newList_testToString() {
		try {
			ListADT<Integer> list = newList();
			String str = list.toString();
			System.out.println("toString() output:\n" + str);
			if (str.length() == 0) {
				return false;
			}
			char lastChar = str.charAt(str.length()-1);
			if (str.contains("@")
					&& !str.contains(" ")
					&& Character.isLetter(str.charAt(0)) 
					&& (Character.isDigit(lastChar)
							||
							(lastChar >= 'a' && lastChar <= 'f'))) {
				return false; //looks like default toString()
			}
			return true;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testToString", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testIterator() {
		try {
			ListADT<Integer> list = newList();
			@SuppressWarnings("unused")
			Iterator<Integer> it = list.iterator();
			return true;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testIterator", e.toString());
			return false;
		}
	}
	
	/** @return test success */
	private boolean newList_testIteratorHasNext() {
		try {
			ListADT<Integer> list = newList();
			Iterator<Integer> it = list.iterator();
			return (it.hasNext() == false);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testIterator", e.toString());
			return false;
		}
	}
	
	/** @return test success */
	private boolean newList_testIteratorNext() {
		try {
			ListADT<Integer> list = newList();
			Iterator<Integer> it = list.iterator();
			it.next();
			return false;
		} catch (NoSuchElementException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testIteratorNext", "NoSuchElementException", e.toString());
			return false;
		}
	}
	
	/** @return test success */
	private boolean newList_testIteratorRemove() {
		try {
			ListADT<Integer> list = newList();
			Iterator<Integer> it = list.iterator();
			it.remove();
			return false;
		} catch (UnsupportedOperationException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testIteratorRemove", "UnsupportedOperationException", e.toString());
			return false;
		}
	}
	
	/** @return test success */
	private boolean newList_testAddToFront() {
		try {
			UnorderedListADT<Integer> list = (UnorderedListADT<Integer>) newList();
			list.addToFront(ELEMENT_A);
			return true;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testAddToFront", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testAddToRear() {
		try {
			UnorderedListADT<Integer> list = (UnorderedListADT<Integer>) newList();
			list.addToRear(ELEMENT_A);
			return true;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "newList_testAddToRear", e.toString());
			return false;
		}
	}

	/** @return test success */
	private boolean newList_testAddAfter() {
		try {
			UnorderedListADT<Integer> list = (UnorderedListADT<Integer>) newList();
			list.addAfter(ELEMENT_A, ELEMENT_B);
			return false;
		} catch (ElementNotFoundException e) {
			return true;
		} catch (Exception e) {
			System.out.printf("%s expected %s, caught %s\n", "newList_testAddAfter", "ElementNotFoundException", e.toString());
			return false;
		}
	}

	//////////////////////////////////
	// Scenario: (next scenario) XXX
	//////////////////////////////////
	
}//end class UnorderedListTester
