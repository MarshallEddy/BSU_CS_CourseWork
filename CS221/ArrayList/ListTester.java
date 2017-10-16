import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A unit test class for lists that implement IndexedUnorderedListADT
 * This is a set of black box tests that should work for any
 * implementation of this interface.
 * 
 * NOTE: One example test is given for each interface method using a new list to
 * get you started.
 * 
 * @author mvail, mhthomas, MarshallEddy (edited)
 * 
 */
public class ListTester {
	//possible lists that could be tested
	private enum ListToUse {
		goodList, badList, arrayList, singleLinkedList, doubleLinkedList
	};
	 // TODO: THIS IS WHERE YOU CHOOSE WHICH LIST TO TEST
//	private final ListToUse LIST_TO_USE = ListToUse.goodList;
//	private final ListToUse LIST_TO_USE = ListToUse.badList;
	private final ListToUse LIST_TO_USE = ListToUse.arrayList;
//	private final ListToUse LIST_TO_USE = ListToUse.singleLinkedList;
//	private final ListToUse LIST_TO_USE = ListToUse.doubleLinkedList;

	// possible results expected in tests
	private enum Result {
		EmptyCollection, ElementNotFound, IndexOutOfBounds, IllegalState, ConcurrentModification, NoSuchElement, 
		NoException, UnexpectedException,
		True, False, Pass, Fail, 
		MatchingValue,
		ValidString
	};

	// named elements for use in tests
	private static final Integer ELEMENT_A = new Integer(1);
	private static final Integer ELEMENT_B = new Integer(2);
	private static final Integer ELEMENT_C = new Integer(3);
	private static final Integer ELEMENT_D = new Integer(4);

	// instance variables for tracking test results
	private int passes = 0;
	private int failures = 0;
	private int total = 0;

	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		// to avoid every method being static
		ListTester tester = new ListTester();
		tester.runTests();
	}

	/**
	 * Print test results in a consistent format
	 * 
	 * @param testDesc description of the test
	 * @param result indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) {
		total++;
		if (result) { passes++; }
		else { failures++; }
		System.out.printf("%-64s\t%s\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/** Print a final summary */
	private void printFinalSummary() {
		System.out.printf("\nTotal Tests: %d,  Passed: %d,  Failed: %d\n",
				total, passes, failures);
	}

	/** XXX runTests()
	 * Run tests to confirm required functionality from list constructors and methods */
	private void runTests() {
		//recommended scenario naming: start_change_result
		test_newList(); //aka noList_constructor_emptyList
		test_emptyList_addToFrontA_A();
		test_emptyList_addToRearA_A();
		test_emptyList_addA_A();
		test_emptyList_addAtIndexOf0A_A();
		test_A_removeFirst_emptyList();
		test_A_removeLast_emptyList();
		test_A_removeA_emptyList();
		test_A_addToFrontB_BA();
		test_A_addToRearB_AB();
		test_A_setAtIndexOf0B_B();
		test_AB_removeFirst_B();
		test_AB_removeLast_A();
		test_AB_removeA_B();
		test_AB_removeB_A();
		test_AB_addC_ABC();
//		test_A_addAfterAB_AB();
		//and so on
		
		// report final verdict
		printFinalSummary();
	}

	
	
	//////////////////////////////////////
	// SCENARIO: NEW EMPTY LIST
	//  XXX NEW EMPTY LIST
	//////////////////////////////////////
	
	/**
	 * Returns a IndexedUnorderedListADT for the "new empty list" scenario.
	 * Scenario(1): no list -> constructor -> [ ]
	 * 
	 * @return a new, empty IndexedUnorderedListADT
	 */
	private IndexedUnorderedListADT<Integer> newList() {
		IndexedUnorderedListADT<Integer> listToUse;
		switch (LIST_TO_USE) {
//		case goodList:
//			listToUse = new GoodList<Integer>();
//			break;
//		case badList:
//			listToUse = new BadList<Integer>();
//			break;
		case arrayList:
			listToUse = new ArrayList<Integer>();
			break;
		// case singleLinkedList:
		// listToUse = new SingleLinkedList<Integer>();
		// break;
		// case doubleLinkedList:
		// listToUse = new DoubleLinkedList<Integer>();
		// break;
		default:
			listToUse = null;
		}
		return listToUse;
	}

	/** Run all tests on scenario(1): no list -> constructor -> [ ] */
	private void test_newList() {
		// recommended test naming: start_change_result_testName
		// e.g. A_addToFront_BA_testSize
		// AB_addC1_ACB_testFirst
		// A_remove0_empty_testLast

		//try-catch is necessary to prevent an Exception from the scenario builder method from bringing
		//down the whole test suite
		try {
			// ListADT
			System.out.print("\nSCENARIO: no list -> constructor -> [] \nTests: \n");
			printTest("newList_testRemoveFirst", testRemoveFirst(newList(), null, Result.EmptyCollection));
			printTest("newList_testRemoveLast", testRemoveLast(newList(), null, Result.EmptyCollection));
			printTest("newList_testRemoveA", testRemoveElement(newList(), null, Result.ElementNotFound));
			printTest("newList_testFirst", testFirst(newList(), null, Result.EmptyCollection));
			printTest("newList_testLast", testLast(newList(), null, Result.EmptyCollection));
			printTest("newList_testContainsA", testContains(newList(), ELEMENT_A, Result.False));
			printTest("newList_testIsEmpty", testIsEmpty(newList(), Result.True));
			printTest("newList_testSize", testSize(newList(), 0));
			printTest("newList_testToString", testToString(newList(), Result.ValidString));
			// UnorderedListADT
			printTest("newList_testAddToFrontA", testAddToFront(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddToRearA", testAddToRear(newList(), ELEMENT_A, Result.NoException));
			printTest(	"newList_testAddAfterBA", testAddAfter(newList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("newList_testAddAtIndexNeg1", testAddAtIndex(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddAtIndex0", testAddAtIndex(newList(), 0, ELEMENT_A, Result.NoException));
			printTest("newList_testAddAtIndex1", testAddAtIndex(newList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testSetNeg1A", testSet(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testSet0A", testSet(newList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddA", testAdd(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testGetNeg1", testGet(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testGet0", testGet(newList(), 0, null, Result.IndexOutOfBounds));
			printTest("newList_testIndexOfA", testIndexOf(newList(), ELEMENT_A, -1));
			printTest("newList_testRemoveNeg1", testRemoveIndex(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testRemove0", testRemoveIndex(newList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("newList_testIterator", testIterator(newList(), Result.NoException));
			printTest("newList_testIteratorHasNext", testIteratorHasNext(newList().iterator(), Result.False));
			printTest("newList_testIteratorNext", testIteratorNext(newList().iterator(), null, Result.NoSuchElement));
			printTest("newList_testIteratorRemove", testIteratorRemove(newList().iterator(), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_newList");
			e.printStackTrace();
		}
	}

	
	////////////////////////////////////////////////
	// SCENARIO: [ ] -> addToFront(A) -> [A]
	//  XXX [ ] -> addToFront(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario(2): empty list -> addToFront(A) -> [A] 
	 * @return [A] after addToFront(A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_addToFrontA_A() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.addToFront(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: empty list -> addToFront(A) -> [A] */
	private void test_emptyList_addToFrontA_A() {
		// recommended test naming: start_change_result_testName
		// e.g. A_addToFront_BA_testSize
		// AB_addC1_ACB_testFirst
		// A_remove0_empty_testLast

		//try-catch is necessary to prevent an Exception from the scenario builder method from bringing
		//down the whole test suite
		try {
			// ListADT
			System.out.print("\nSCENARIO: [ ] -> addToFront(A) -> [A] \nTests: \n");
			printTest("emptyList_addToFrontA_A_testRemoveFirst", testRemoveFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveLast", testRemoveLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveA", testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveB", testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addToFrontA_A_testFirst", testFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testLast", testLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testContainsA", testContains(emptyList_addToFrontA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToFrontA_A_testContainsB", testContains(emptyList_addToFrontA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToFrontA_A_testIsEmpty", testIsEmpty(emptyList_addToFrontA_A(), Result.False));
			printTest("emptyList_addToFrontA_A_testSize", testSize(emptyList_addToFrontA_A(), 1));
			printTest("emptyList_addToFrontA_A_testToString", testToString(emptyList_addToFrontA_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_addToFrontA_A_testAddToFrontB", testAddToFront(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddToRearB", testAddToRear(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest(	"emptyList_addToFrontA_A_testAddAfterCB", testAddAfter(emptyList_addToFrontA_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest(	"emptyList_addToFrontA_A_testAddAfterAB", testAddAfter(emptyList_addToFrontA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_addToFrontA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addToFrontA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testSetNeg1B", testSet(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testSet0B", testSet(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddB", testAdd(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testGetNeg1", testGet(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testGet0", testGet(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIndexOfA", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToFrontA_A_testIndexOfB", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_B, -1));
			printTest("emptyList_addToFrontA_A_testRemoveNeg1", testRemoveIndex(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testRemove0", testRemoveIndex(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemove1", testRemoveIndex(emptyList_addToFrontA_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_addToFrontA_A_testIterator", testIterator(emptyList_addToFrontA_A(), Result.NoException));
			printTest("emptyList_addToFrontA_A_testIteratorHasNext", testIteratorHasNext(emptyList_addToFrontA_A().iterator(), Result.True));
			printTest("emptyList_addToFrontA_A_testIteratorNext", testIteratorNext(emptyList_addToFrontA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIteratorRemove", testIteratorRemove(emptyList_addToFrontA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(emptyList_addToFrontA_A(), 1), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(emptyList_addToFrontA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1), Result.NoException));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1)), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	
	////////////////////////////////////////////////
	// SCENARIO: [ ] -> addToRear(A) -> [A]
	//  XXX [ ] -> addToRear(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario(3): empty list -> addToRear(A) -> [A] 
	 * @return [A] after addToRear(A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_addToRearA_A() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.addToRear(ELEMENT_A);
		return list;
	}
	
	/** Run all tests on scenario: empty list -> addToRear(A) -> [A] */
	private void test_emptyList_addToRearA_A() {
	
		try {
			// ListADT
			System.out.print("\nSCENARIO: [ ] -> addToRear(A) -> [A] \nTests: \n");
			printTest("emptyList_addToRearA_A_testRemoveFirst", testRemoveFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveLast", testRemoveLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveA", testRemoveElement(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveB", testRemoveElement(emptyList_addToRearA_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addToRearA_A_testFirst", testFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testLast", testLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testContainsA", testContains(emptyList_addToRearA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToRearA_A_testContainsB", testContains(emptyList_addToRearA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToRearA_A_testIsEmpty", testIsEmpty(emptyList_addToRearA_A(), Result.False));
			printTest("emptyList_addToRearA_A_testSize", testSize(emptyList_addToRearA_A(), 1));
			printTest("emptyList_addToRearA_A_testToString", testToString(emptyList_addToRearA_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_addToRearA_A_testAddToFrontB", testAddToFront(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddToRearB", testAddToRear(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest(	"emptyList_addToRearA_A_testAddAfterCB", testAddAfter(emptyList_addToRearA_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest(	"emptyList_addToRearA_A_testAddAfterAB", testAddAfter(emptyList_addToRearA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_addToRearA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addToRearA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testSetNeg1B", testSet(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testSet0B", testSet(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddB", testAdd(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testGetNeg1", testGet(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testGet0", testGet(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIndexOfA", testIndexOf(emptyList_addToRearA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToRearA_A_testIndexOfB", testIndexOf(emptyList_addToRearA_A(), ELEMENT_B, -1));
			printTest("emptyList_addToRearA_A_testRemoveNeg1", testRemoveIndex(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testRemove0", testRemoveIndex(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemove1", testRemoveIndex(emptyList_addToRearA_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_addToRearA_A_testIterator", testIterator(emptyList_addToRearA_A(), Result.NoException));
			printTest("emptyList_addToRearA_A_testIteratorHasNext", testIteratorHasNext(emptyList_addToRearA_A().iterator(), Result.True));
			printTest("emptyList_addToRearA_A_testIteratorNext", testIteratorNext(emptyList_addToRearA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIteratorRemove", testIteratorRemove(emptyList_addToRearA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToRearA_A_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(emptyList_addToRearA_A(), 1), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(emptyList_addToRearA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1), Result.NoException));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1)), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToRearA_A");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [ ] -> add(A) -> [A]
	//  XXX [ ] -> add(A) -> [A]
	////////////////////////////////////////////////

	/** Scenario(4): empty list -> add(A) -> [A] 
* @return [A] after add(A)
*/
	private IndexedUnorderedListADT<Integer> emptyList_addA_A() {
IndexedUnorderedListADT<Integer> list = newList(); 
list.add(ELEMENT_A);
return list;
}

	/** Run all tests on scenario: empty list -> add(A) -> [A] */
	private void test_emptyList_addA_A() {

try {
// ListADT
System.out.print("\nSCENARIO: [ ] -> add(A) -> [A] \nTests: \n");
printTest("emptyList_addA_A_testRemoveFirst", testRemoveFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testRemoveLast", testRemoveLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testRemoveA", testRemoveElement(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testRemoveB", testRemoveElement(emptyList_addA_A(), ELEMENT_B, Result.ElementNotFound));
printTest("emptyList_addA_A_testFirst", testFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testLast", testLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testContainsA", testContains(emptyList_addA_A(), ELEMENT_A, Result.True));
printTest("emptyList_addA_A_testContainsB", testContains(emptyList_addA_A(), ELEMENT_B, Result.False));
printTest("emptyList_addA_A_testIsEmpty", testIsEmpty(emptyList_addA_A(), Result.False));
printTest("emptyList_addA_A_testSize", testSize(emptyList_addA_A(), 1));
printTest("emptyList_addA_A_testToString", testToString(emptyList_addA_A(), Result.ValidString));
// UnorderedListADT
printTest("emptyList_addA_A_testAddToFrontB", testAddToFront(emptyList_addA_A(), ELEMENT_B, Result.NoException));
printTest("emptyList_addA_A_testAddToRearB", testAddToRear(emptyList_addA_A(), ELEMENT_B, Result.NoException));
printTest("emptyList_addA_A_testAddAfterCB", testAddAfter(emptyList_addA_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
printTest("emptyList_addA_A_testAddAfterAB", testAddAfter(emptyList_addA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
// IndexedListADT
printTest("emptyList_addA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
printTest("emptyList_addA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
printTest("emptyList_addA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addA_A(), 1, ELEMENT_B, Result.NoException));
printTest("emptyList_addA_A_testSetNeg1B", testSet(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
printTest("emptyList_addA_A_testSet0B", testSet(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
printTest("emptyList_addA_A_testAddB", testAdd(emptyList_addA_A(), ELEMENT_B, Result.NoException));
printTest("emptyList_addA_A_testGetNeg1", testGet(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
printTest("emptyList_addA_A_testGet0", testGet(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testIndexOfA", testIndexOf(emptyList_addA_A(), ELEMENT_A, 0));
printTest("emptyList_addA_A_testIndexOfB", testIndexOf(emptyList_addA_A(), ELEMENT_B, -1));
printTest("emptyList_addA_A_testRemoveNeg1", testRemoveIndex(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
printTest("emptyList_addA_A_testRemove0", testRemoveIndex(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testRemove1", testRemoveIndex(emptyList_addA_A(), 1, null, Result.IndexOutOfBounds));
// Iterator
printTest("emptyList_addA_A_testIterator", testIterator(emptyList_addA_A(), Result.NoException));
printTest("emptyList_addA_A_testIteratorHasNext", testIteratorHasNext(emptyList_addA_A().iterator(), Result.True));
printTest("emptyList_addA_A_testIteratorNext", testIteratorNext(emptyList_addA_A().iterator(), ELEMENT_A, Result.MatchingValue));
printTest("emptyList_addA_A_testIteratorRemove", testIteratorRemove(emptyList_addA_A().iterator(), Result.IllegalState));
printTest("emptyList_addA_A_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(emptyList_addA_A(), 1), Result.False));
printTest("emptyList_addA_A_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(emptyList_addA_A(), 1), null, Result.NoSuchElement));
printTest("emptyList_addA_A_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(emptyList_addA_A(), 1), Result.NoException));
printTest("emptyList_addA_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addA_A(), 1)), Result.False));
printTest("emptyList_addA_A_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addA_A(), 1)), null, Result.NoSuchElement));
printTest("emptyList_addA_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(emptyList_addA_A(), 1)), Result.IllegalState));
System.out.println("");
} catch (Exception e) {
System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addA_A");
e.printStackTrace();
}
}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [ ] -> add(0, A) -> [A]
	//  XXX [ ] -> add(0, A) -> [A]
	////////////////////////////////////////////////

	/** Scenario(5): empty list -> add(0, A) -> [A] 
	 * @return [A] after add(0, A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_addAtIndexOf0A_A() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.add(0, ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: empty list -> add(0, A) -> [A] */
	private void test_emptyList_addAtIndexOf0A_A() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [ ] -> add(0, A) -> [A] \nTests: \n");
			printTest("emptyList_addAtIndexOf0A_A_testRemoveFirst", testRemoveFirst(emptyList_addAtIndexOf0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testRemoveLast", testRemoveLast(emptyList_addAtIndexOf0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testRemoveA", testRemoveElement(emptyList_addAtIndexOf0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testRemoveB", testRemoveElement(emptyList_addAtIndexOf0A_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addAtIndexOf0A_A_testFirst", testFirst(emptyList_addAtIndexOf0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testLast", testLast(emptyList_addAtIndexOf0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testContainsA", testContains(emptyList_addAtIndexOf0A_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addAtIndexOf0A_A_testContainsB", testContains(emptyList_addAtIndexOf0A_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addAtIndexOf0A_A_testIsEmpty", testIsEmpty(emptyList_addAtIndexOf0A_A(), Result.False));
			printTest("emptyList_addAtIndexOf0A_A_testSize", testSize(emptyList_addAtIndexOf0A_A(), 1));
			printTest("emptyList_addAtIndexOf0A_A_testToString", testToString(emptyList_addAtIndexOf0A_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_addAtIndexOf0A_A_testAddToFrontB", testAddToFront(emptyList_addAtIndexOf0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testAddToRearB", testAddToRear(emptyList_addAtIndexOf0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testAddAfterCB", testAddAfter(emptyList_addAtIndexOf0A_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addAtIndexOf0A_A_testAddAfterAB", testAddAfter(emptyList_addAtIndexOf0A_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_addAtIndexOf0A_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addAtIndexOf0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addAtIndexOf0A_A_testAddAtIndex0B", testAddAtIndex(emptyList_addAtIndexOf0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testAddAtIndex1B", testAddAtIndex(emptyList_addAtIndexOf0A_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testSetNeg1B", testSet(emptyList_addAtIndexOf0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addAtIndexOf0A_A_testSet0B", testSet(emptyList_addAtIndexOf0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testAddB", testAdd(emptyList_addAtIndexOf0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testGetNeg1", testGet(emptyList_addAtIndexOf0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addAtIndexOf0A_A_testGet0", testGet(emptyList_addAtIndexOf0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testIndexOfA", testIndexOf(emptyList_addAtIndexOf0A_A(), ELEMENT_A, 0));
			printTest("emptyList_addAtIndexOf0A_A_testIndexOfB", testIndexOf(emptyList_addAtIndexOf0A_A(), ELEMENT_B, -1));
			printTest("emptyList_addAtIndexOf0A_A_testRemoveNeg1", testRemoveIndex(emptyList_addAtIndexOf0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addAtIndexOf0A_A_testRemove0", testRemoveIndex(emptyList_addAtIndexOf0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testRemove1", testRemoveIndex(emptyList_addAtIndexOf0A_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_addAtIndexOf0A_A_testIterator", testIterator(emptyList_addAtIndexOf0A_A(), Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_testIteratorHasNext", testIteratorHasNext(emptyList_addAtIndexOf0A_A().iterator(), Result.True));
			printTest("emptyList_addAtIndexOf0A_A_testIteratorNext", testIteratorNext(emptyList_addAtIndexOf0A_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addAtIndexOf0A_A_testIteratorRemove", testIteratorRemove(emptyList_addAtIndexOf0A_A().iterator(), Result.IllegalState));
			printTest("emptyList_addAtIndexOf0A_A_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(emptyList_addAtIndexOf0A_A(), 1), Result.False));
			printTest("emptyList_addAtIndexOf0A_A_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(emptyList_addAtIndexOf0A_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addAtIndexOf0A_A_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(emptyList_addAtIndexOf0A_A(), 1), Result.NoException));
			printTest("emptyList_addAtIndexOf0A_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addAtIndexOf0A_A(), 1)), Result.False));
			printTest("emptyList_addAtIndexOf0A_A_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addAtIndexOf0A_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addAtIndexOf0A_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(emptyList_addAtIndexOf0A_A(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_add0A_A");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A] -> removeFirst() -> [ ]
	//  XXX [A] -> removeFirst() -> [ ]
	////////////////////////////////////////////////

	/** Scenario(6): [A] -> removeFirst() -> empty list
	 * @return empty list after removeFirst()
	 */
	private IndexedUnorderedListADT<Integer> A_removeFirst_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		
		list.removeFirst();
		return list;
	}

	/** Run all tests on scenario: [A] -> removeFirst() -> [ ] */
	private void test_A_removeFirst_emptyList() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A] -> removeFirst() -> [ ] \nTests: \n");
			printTest("A_removeFirst_emptyList()_testRemoveFirst", testRemoveFirst(A_removeFirst_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList()_testRemoveLast", testRemoveLast(A_removeFirst_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList()_testRemoveA", testRemoveElement(A_removeFirst_emptyList(), ELEMENT_A, Result.ElementNotFound));
			printTest("A_removeFirst_emptyList()_testRemoveB", testRemoveElement(A_removeFirst_emptyList(), ELEMENT_B, Result.ElementNotFound));
			printTest("A_removeFirst_emptyList()_testFirst", testFirst(A_removeFirst_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList()_testLast", testLast(A_removeFirst_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList()_testContainsA", testContains(A_removeFirst_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeFirst_emptyList()_testContainsB", testContains(A_removeFirst_emptyList(), ELEMENT_B, Result.False));
			printTest("A_removeFirst_emptyList()_testIsEmpty", testIsEmpty(A_removeFirst_emptyList(), Result.True));
			printTest("A_removeFirst_emptyList()_testSize", testSize(A_removeFirst_emptyList(), 0));
			printTest("A_removeFirst_emptyList()_testToString", testToString(A_removeFirst_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_removeFirst_emptyList()_testAddToFrontB", testAddToFront(A_removeFirst_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeFirst_emptyList()_testAddToRearB", testAddToRear(A_removeFirst_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeFirst_emptyList()_testAddAfterCB", testAddAfter(A_removeFirst_emptyList(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("A_removeFirst_emptyList()_testAddAfterAB", testAddAfter(A_removeFirst_emptyList(), ELEMENT_A, ELEMENT_B, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_removeFirst_emptyList()_testAddAtIndexNeg1B", testAddAtIndex(A_removeFirst_emptyList(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testAddAtIndex0B", testAddAtIndex(A_removeFirst_emptyList(), 0, ELEMENT_B, Result.NoException));
			printTest("A_removeFirst_emptyList()_testAddAtIndex1B", testAddAtIndex(A_removeFirst_emptyList(), 1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testSetNeg1B", testSet(A_removeFirst_emptyList(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testSet0B", testSet(A_removeFirst_emptyList(), 0, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testAddB", testAdd(A_removeFirst_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeFirst_emptyList()_testGetNeg1", testGet(A_removeFirst_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testGet0", testGet(A_removeFirst_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testIndexOfA", testIndexOf(A_removeFirst_emptyList(), ELEMENT_A, -1));
			printTest("A_removeFirst_emptyList()_testIndexOfB", testIndexOf(A_removeFirst_emptyList(), ELEMENT_B, -1));
			printTest("A_removeFirst_emptyList()_testRemoveNeg1", testRemoveIndex(A_removeFirst_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testRemove0", testRemoveIndex(A_removeFirst_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList()_testRemove1", testRemoveIndex(A_removeFirst_emptyList(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_removeFirst_emptyList()_testIterator", testIterator(A_removeFirst_emptyList(), Result.NoException));
			printTest("A_removeFirst_emptyList()_testIteratorHasNext", testIteratorHasNext(A_removeFirst_emptyList().iterator(), Result.False));
			printTest("A_removeFirst_emptyList()_testIteratorNext", testIteratorNext(A_removeFirst_emptyList().iterator(), ELEMENT_A, Result.NoSuchElement));
			printTest("A_removeFirst_emptyList()_testIteratorRemove", testIteratorRemove(A_removeFirst_emptyList().iterator(), Result.IllegalState));
			printTest("A_removeFirst_emptyList()_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(A_removeFirst_emptyList(), 0), Result.False));
			printTest("A_removeFirst_emptyList()_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(A_removeFirst_emptyList(), 0), null, Result.NoSuchElement));
			printTest("A_removeFirst_emptyList()_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(A_removeFirst_emptyList(), 0), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_removeFirst_emptyList");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A] -> removeLast() -> [ ]
	//  XXX [A] -> removeLast() -> [ ]
	////////////////////////////////////////////////

	/** Scenario(7): [A] -> removeLast() -> empty list
	 * @return empty list after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> A_removeLast_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: [A] -> removeLast() -> [ ] */
	private void test_A_removeLast_emptyList() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A] -> removeLast() -> [ ] \nTests: \n");
			printTest("A_removeLast_emptyList()_testRemoveFirst", testRemoveFirst(A_removeLast_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeLast_emptyList()_testRemoveLast", testRemoveLast(A_removeLast_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeLast_emptyList()_testRemoveA", testRemoveElement(A_removeLast_emptyList(), ELEMENT_A, Result.ElementNotFound));
			printTest("A_removeLast_emptyList()_testRemoveB", testRemoveElement(A_removeLast_emptyList(), ELEMENT_B, Result.ElementNotFound));
			printTest("A_removeLast_emptyList()_testFirst", testFirst(A_removeLast_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeLast_emptyList()_testLast", testLast(A_removeLast_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeLast_emptyList()_testContainsA", testContains(A_removeLast_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeLast_emptyList()_testContainsB", testContains(A_removeLast_emptyList(), ELEMENT_B, Result.False));
			printTest("A_removeLast_emptyList()_testIsEmpty", testIsEmpty(A_removeLast_emptyList(), Result.True));
			printTest("A_removeLast_emptyList()_testSize", testSize(A_removeLast_emptyList(), 0));
			printTest("A_removeLast_emptyList()_testToString", testToString(A_removeLast_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_removeLast_emptyList()_testAddToFrontB", testAddToFront(A_removeLast_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeLast_emptyList()_testAddToRearB", testAddToRear(A_removeLast_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeLast_emptyList()_testAddAfterCB", testAddAfter(A_removeLast_emptyList(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("A_removeLast_emptyList()_testAddAfterAB", testAddAfter(A_removeLast_emptyList(), ELEMENT_A, ELEMENT_B, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_removeLast_emptyList()_testAddAtIndexNeg1B", testAddAtIndex(A_removeLast_emptyList(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testAddAtIndex0B", testAddAtIndex(A_removeLast_emptyList(), 0, ELEMENT_B, Result.NoException));
			printTest("A_removeLast_emptyList()_testAddAtIndex1B", testAddAtIndex(A_removeLast_emptyList(), 1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testSetNeg1B", testSet(A_removeLast_emptyList(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testSet0B", testSet(A_removeLast_emptyList(), 0, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testAddB", testAdd(A_removeLast_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeLast_emptyList()_testGetNeg1", testGet(A_removeLast_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testGet0", testGet(A_removeLast_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testIndexOfA", testIndexOf(A_removeLast_emptyList(), ELEMENT_A, -1));
			printTest("A_removeLast_emptyList()_testIndexOfB", testIndexOf(A_removeLast_emptyList(), ELEMENT_B, -1));
			printTest("A_removeLast_emptyList()_testRemoveNeg1", testRemoveIndex(A_removeLast_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testRemove0", testRemoveIndex(A_removeLast_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList()_testRemove1", testRemoveIndex(A_removeLast_emptyList(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_removeLast_emptyList()_testIterator", testIterator(A_removeLast_emptyList(), Result.NoException));
			printTest("A_removeLast_emptyList()_testIteratorHasNext", testIteratorHasNext(A_removeLast_emptyList().iterator(), Result.False));
			printTest("A_removeLast_emptyList()_testIteratorNext", testIteratorNext(A_removeLast_emptyList().iterator(), ELEMENT_A, Result.NoSuchElement));
			printTest("A_removeLast_emptyList()_testIteratorRemove", testIteratorRemove(A_removeLast_emptyList().iterator(), Result.IllegalState));
			printTest("A_removeLast_emptyList()_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(A_removeLast_emptyList(), 0), Result.False));
			printTest("A_removeLast_emptyList()_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(A_removeLast_emptyList(), 0), null, Result.NoSuchElement));
			printTest("A_removeLast_emptyList()_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(A_removeLast_emptyList(), 0), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_removeLast_emptyList");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A] -> remove(A) -> [ ]
	//  XXX [A] -> remove(A) -> [ ]
	////////////////////////////////////////////////

	/** Scenario(8): [A] -> remove(A) -> empty list
	 * @return empty list after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> A_removeA_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		
		list.remove(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: [A] -> remove(A) -> [ ] */
	private void test_A_removeA_emptyList() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A] -> remove(A) -> [ ] \nTests: \n");
			printTest("A_removeA_emptyList()_testRemoveFirst", testRemoveFirst(A_removeA_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeA_emptyList()_testRemoveLast", testRemoveLast(A_removeA_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeA_emptyList()_testRemoveA", testRemoveElement(A_removeA_emptyList(), ELEMENT_A, Result.ElementNotFound));
			printTest("A_removeA_emptyList()_testRemoveB", testRemoveElement(A_removeA_emptyList(), ELEMENT_B, Result.ElementNotFound));
			printTest("A_removeA_emptyList()_testFirst", testFirst(A_removeA_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeA_emptyList()_testLast", testLast(A_removeA_emptyList(), ELEMENT_A, Result.EmptyCollection));
			printTest("A_removeA_emptyList()_testContainsA", testContains(A_removeA_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeA_emptyList()_testContainsB", testContains(A_removeA_emptyList(), ELEMENT_B, Result.False));
			printTest("A_removeA_emptyList()_testIsEmpty", testIsEmpty(A_removeA_emptyList(), Result.True));
			printTest("A_removeA_emptyList()_testSize", testSize(A_removeA_emptyList(), 0));
			printTest("A_removeA_emptyList()_testToString", testToString(A_removeA_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_removeA_emptyList()_testAddToFrontB", testAddToFront(A_removeA_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeA_emptyList()_testAddToRearB", testAddToRear(A_removeA_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeA_emptyList()_testAddAfterCB", testAddAfter(A_removeA_emptyList(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("A_removeA_emptyList()_testAddAfterAB", testAddAfter(A_removeA_emptyList(), ELEMENT_A, ELEMENT_B, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_removeA_emptyList()_testAddAtIndexNeg1B", testAddAtIndex(A_removeA_emptyList(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testAddAtIndex0B", testAddAtIndex(A_removeA_emptyList(), 0, ELEMENT_B, Result.NoException));
			printTest("A_removeA_emptyList()_testAddAtIndex1B", testAddAtIndex(A_removeA_emptyList(), 1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testSetNeg1B", testSet(A_removeA_emptyList(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testSet0B", testSet(A_removeA_emptyList(), 0, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testAddB", testAdd(A_removeA_emptyList(), ELEMENT_B, Result.NoException));
			printTest("A_removeA_emptyList()_testGetNeg1", testGet(A_removeA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testGet0", testGet(A_removeA_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testIndexOfA", testIndexOf(A_removeA_emptyList(), ELEMENT_A, -1));
			printTest("A_removeA_emptyList()_testIndexOfB", testIndexOf(A_removeA_emptyList(), ELEMENT_B, -1));
			printTest("A_removeA_emptyList()_testRemoveNeg1", testRemoveIndex(A_removeA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testRemove0", testRemoveIndex(A_removeA_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList()_testRemove1", testRemoveIndex(A_removeA_emptyList(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_removeA_emptyList()_testIterator", testIterator(A_removeA_emptyList(), Result.NoException));
			printTest("A_removeA_emptyList()_testIteratorHasNext", testIteratorHasNext(A_removeA_emptyList().iterator(), Result.False));
			printTest("A_removeA_emptyList()_testIteratorNext", testIteratorNext(A_removeA_emptyList().iterator(), ELEMENT_A, Result.NoSuchElement));
			printTest("A_removeA_emptyList()_testIteratorRemove", testIteratorRemove(A_removeA_emptyList().iterator(), Result.IllegalState));
			printTest("A_removeA_emptyList()_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(A_removeA_emptyList(), 0), Result.False));
			printTest("A_removeA_emptyList()_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(A_removeA_emptyList(), 0), null, Result.NoSuchElement));
			printTest("A_removeA_emptyList()_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(A_removeA_emptyList(), 0), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_removeA_emptyList");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A] -> addToFront(B) -> [B, A]
	//  XXX [A] -> addToFront(B) -> [B, A]
	////////////////////////////////////////////////

	/** Scenario(9): [A] -> addToFront(B) -> [B, A]
	 * @return empty list after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> A_addToFrontB_BA() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);

		list.addToFront(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> addToFront(B) -> [B, A] */
	private void test_A_addToFrontB_BA() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A] -> addToFront(B) -> [B, A] \nTests: \n");
			printTest("A_addToFrontB_BA()_testRemoveFirst", testRemoveFirst(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testRemoveLast", testRemoveLast(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testRemoveA", testRemoveElement(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testRemoveB", testRemoveElement(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testRemoveC", testRemoveElement(A_addToFrontB_BA(), ELEMENT_C, Result.ElementNotFound));
			printTest("A_addToFrontB_BA()_testFirst", testFirst(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testLast", testLast(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testContainsA", testContains(A_addToFrontB_BA(), ELEMENT_A, Result.True));
			printTest("A_addToFrontB_BA()_testContainsB", testContains(A_addToFrontB_BA(), ELEMENT_B, Result.True));
			printTest("A_addToFrontB_BA()_testContainsC", testContains(A_addToFrontB_BA(), ELEMENT_C, Result.False));
			printTest("A_addToFrontB_BA()_testIsEmpty", testIsEmpty(A_addToFrontB_BA(), Result.False));
			printTest("A_addToFrontB_BA()_testSize", testSize(A_addToFrontB_BA(), 2));
			printTest("A_addToFrontB_BA()_testToString", testToString(A_addToFrontB_BA(), Result.ValidString));
			// UnorderedListADT
			printTest("A_addToFrontB_BA()_testAddToFrontC", testAddToFront(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddToRearC", testAddToRear(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddAfterCB", testAddAfter(A_addToFrontB_BA(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddAfterCA", testAddAfter(A_addToFrontB_BA(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddAfterCD", testAddAfter(A_addToFrontB_BA(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_addToFrontB_BA()_testAddAtIndexNeg1C", testAddAtIndex(A_addToFrontB_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testAddAtIndex0C", testAddAtIndex(A_addToFrontB_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddAtIndex1C", testAddAtIndex(A_addToFrontB_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddAtIndex2C", testAddAtIndex(A_addToFrontB_BA(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testAddAtIndex3C", testAddAtIndex(A_addToFrontB_BA(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testSetNeg1D", testSet(A_addToFrontB_BA(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testSet0D", testSet(A_addToFrontB_BA(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addToFrontB_BA()_testSet1D", testSet(A_addToFrontB_BA(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addToFrontB_BA()_testSet2D", testSet(A_addToFrontB_BA(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testAddC", testAdd(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA()_testGetNeg1", testGet(A_addToFrontB_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testGet0", testGet(A_addToFrontB_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testGet1", testGet(A_addToFrontB_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testGet2", testGet(A_addToFrontB_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testIndexOfB", testIndexOf(A_addToFrontB_BA(), ELEMENT_B, 0));
			printTest("A_addToFrontB_BA()_testIndexOfA", testIndexOf(A_addToFrontB_BA(), ELEMENT_A, 1));
			printTest("A_addToFrontB_BA()_testIndexOfC", testIndexOf(A_addToFrontB_BA(), ELEMENT_C, -1));
			printTest("A_addToFrontB_BA()_testRemoveNeg1", testRemoveIndex(A_addToFrontB_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA()_testRemove0", testRemoveIndex(A_addToFrontB_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testRemove1", testRemoveIndex(A_addToFrontB_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testRemove2", testRemoveIndex(A_addToFrontB_BA(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_addToFrontB_BA()_testIterator", testIterator(A_addToFrontB_BA(), Result.NoException));
			printTest("A_addToFrontB_BA()_testIteratorHasNext", testIteratorHasNext(A_addToFrontB_BA().iterator(), Result.True));
			printTest("A_addToFrontB_BA()_testIteratorNext", testIteratorNext(A_addToFrontB_BA().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_testIteratorRemove", testIteratorRemove(A_addToFrontB_BA().iterator(), Result.IllegalState));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorHasNext0", testIteratorHasNext(iteratorAfterNext(A_addToFrontB_BA(), 0), Result.True));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorHasNext1", testIteratorHasNext(iteratorAfterNext(A_addToFrontB_BA(), 1), Result.True));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorHasNext2", testIteratorHasNext(iteratorAfterNext(A_addToFrontB_BA(), 2), Result.False));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorNext1", testIteratorNext(iteratorAfterNext(A_addToFrontB_BA(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorNext0", testIteratorNext(iteratorAfterNext(A_addToFrontB_BA(), 0), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorNext2", testIteratorNext(iteratorAfterNext(A_addToFrontB_BA(), 2), null, Result.NoSuchElement));
			printTest("A_addToFrontB_BA()_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(A_addToFrontB_BA(), 1), Result.NoException));
			printTest("A_addToFrontB_BA()_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_addToFrontB_BA(), 1)), Result.True));
			printTest("A_addToFrontB_BA()_iteratorNextRemove_testIteratorHasNext2", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_addToFrontB_BA(), 2)), Result.False));
			printTest("A_addToFrontB_BA()_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(A_addToFrontB_BA(), 1)), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA()_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(A_addToFrontB_BA(), 2)), null, Result.NoSuchElement));
			printTest("A_addToFrontB_BA()_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(A_addToFrontB_BA(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToFrontB_BA");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A] -> addToRear(B) -> [A, B]
	//  XXX [A] -> addToRear(B) -> [A, B]
	////////////////////////////////////////////////

	/** Scenario(10): [A] -> addToRear(B) -> [A, B]
	 * @return empty list after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> A_addToRearB_AB() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);

		list.addToRear(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> addToRear(B) -> [A, B] */
	private void test_A_addToRearB_AB() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A] -> addToRear(B) -> [A, B] \nTests: \n");
			printTest("A_addToRearB_AB()_testRemoveFirst", testRemoveFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testRemoveLast", testRemoveLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testRemoveA", testRemoveElement(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testRemoveB", testRemoveElement(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testRemoveC", testRemoveElement(A_addToRearB_AB(), ELEMENT_C, Result.ElementNotFound));
			printTest("A_addToRearB_AB()_testFirst", testFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testLast", testLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testContainsA", testContains(A_addToRearB_AB(), ELEMENT_A, Result.True));
			printTest("A_addToRearB_AB()_testContainsB", testContains(A_addToRearB_AB(), ELEMENT_B, Result.True));
			printTest("A_addToRearB_AB()_testContainsC", testContains(A_addToRearB_AB(), ELEMENT_C, Result.False));
			printTest("A_addToRearB_AB()_testIsEmpty", testIsEmpty(A_addToRearB_AB(), Result.False));
			printTest("A_addToRearB_AB()_testSize", testSize(A_addToRearB_AB(), 2));
			printTest("A_addToRearB_AB()_testToString", testToString(A_addToRearB_AB(), Result.ValidString));
			// UnorderedListADT
			printTest("A_addToRearB_AB()_testAddToFrontC", testAddToFront(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddToRearC", testAddToRear(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddAfterCB", testAddAfter(A_addToRearB_AB(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddAfterCA", testAddAfter(A_addToRearB_AB(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddAfterCD", testAddAfter(A_addToRearB_AB(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_addToRearB_AB()_testAddAtIndexNeg1C", testAddAtIndex(A_addToRearB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testAddAtIndex0C", testAddAtIndex(A_addToRearB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddAtIndex1C", testAddAtIndex(A_addToRearB_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddAtIndex2C", testAddAtIndex(A_addToRearB_AB(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testAddAtIndex3C", testAddAtIndex(A_addToRearB_AB(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testSetNeg1D", testSet(A_addToRearB_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testSet0D", testSet(A_addToRearB_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB()_testSet1D", testSet(A_addToRearB_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB()_testSet2D", testSet(A_addToRearB_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testAddC", testAdd(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB()_testGetNeg1", testGet(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testGet0", testGet(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testGet1", testGet(A_addToRearB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testGet2", testGet(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testIndexOfB", testIndexOf(A_addToRearB_AB(), ELEMENT_A, 0));
			printTest("A_addToRearB_AB()_testIndexOfA", testIndexOf(A_addToRearB_AB(), ELEMENT_B, 1));
			printTest("A_addToRearB_AB()_testIndexOfC", testIndexOf(A_addToRearB_AB(), ELEMENT_C, -1));
			printTest("A_addToRearB_AB()_testRemoveNeg1", testRemoveIndex(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB()_testRemove0", testRemoveIndex(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testRemove1", testRemoveIndex(A_addToRearB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testRemove2", testRemoveIndex(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_addToRearB_AB()_testIterator", testIterator(A_addToRearB_AB(), Result.NoException));
			printTest("A_addToRearB_AB()_testIteratorHasNext", testIteratorHasNext(A_addToRearB_AB().iterator(), Result.True));
			printTest("A_addToRearB_AB()_testIteratorNext", testIteratorNext(A_addToRearB_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_testIteratorRemove", testIteratorRemove(A_addToRearB_AB().iterator(), Result.IllegalState));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorHasNext0", testIteratorHasNext(iteratorAfterNext(A_addToRearB_AB(), 0), Result.True));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorHasNext1", testIteratorHasNext(iteratorAfterNext(A_addToRearB_AB(), 1), Result.True));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorHasNext2", testIteratorHasNext(iteratorAfterNext(A_addToRearB_AB(), 2), Result.False));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorNext1", testIteratorNext(iteratorAfterNext(A_addToRearB_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorNext0", testIteratorNext(iteratorAfterNext(A_addToRearB_AB(), 0), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorNext2", testIteratorNext(iteratorAfterNext(A_addToRearB_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addToRearB_AB()_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(A_addToRearB_AB(), 1), Result.NoException));
			printTest("A_addToRearB_AB()_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 1)), Result.True));
			printTest("A_addToRearB_AB()_iteratorNextRemove_testIteratorHasNext2", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 2)), Result.False));
			printTest("A_addToRearB_AB()_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB()_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addToRearB_AB()_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToRearB_AB");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A] -> set(0, B) -> [B]
	//  XXX [A] -> set(0, B) -> [B]
	////////////////////////////////////////////////

	/** Scenario(13): [A] -> set(0, B) -> [B] 
	 * @return [A] after set(0, B)
	 */
	private IndexedUnorderedListADT<Integer> A_setAtIndexOf0B_B() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.add(ELEMENT_A);
		
		list.set(0, ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> set(0, B) -> [B] */
	private void test_A_setAtIndexOf0B_B() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A] -> set(0, B) -> [B] \nTests: \n");
			printTest("A_setAtIndexOf0B_B_testRemoveFirst", testRemoveFirst(A_setAtIndexOf0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testRemoveLast", testRemoveLast(A_setAtIndexOf0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testRemoveA", testRemoveElement(A_setAtIndexOf0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testRemoveB", testRemoveElement(A_setAtIndexOf0B_B(), ELEMENT_A, Result.ElementNotFound));
			printTest("A_setAtIndexOf0B_B_testFirst", testFirst(A_setAtIndexOf0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testLast", testLast(A_setAtIndexOf0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testContainsA", testContains(A_setAtIndexOf0B_B(), ELEMENT_B, Result.True));
			printTest("A_setAtIndexOf0B_B_testContainsB", testContains(A_setAtIndexOf0B_B(), ELEMENT_A, Result.False));
			printTest("A_setAtIndexOf0B_B_testIsEmpty", testIsEmpty(A_setAtIndexOf0B_B(), Result.False));
			printTest("A_setAtIndexOf0B_B_testSize", testSize(A_setAtIndexOf0B_B(), 1));
			printTest("A_setAtIndexOf0B_B_testToString", testToString(A_setAtIndexOf0B_B(), Result.ValidString));
			// UnorderedListADT
			printTest("A_setAtIndexOf0B_B_testAddToFrontB", testAddToFront(A_setAtIndexOf0B_B(), ELEMENT_A, Result.NoException));
			printTest("A_setAtIndexOf0B_B_testAddToRearB", testAddToRear(A_setAtIndexOf0B_B(), ELEMENT_A, Result.NoException));
			printTest("A_setAtIndexOf0B_B_testAddAfterCB", testAddAfter(A_setAtIndexOf0B_B(), ELEMENT_C, ELEMENT_A, Result.ElementNotFound));
			printTest("A_setAtIndexOf0B_B_testAddAfterAB", testAddAfter(A_setAtIndexOf0B_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			// IndexedListADT
			printTest("A_setAtIndexOf0B_B_testAddAtIndexNeg1B", testAddAtIndex(A_setAtIndexOf0B_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_setAtIndexOf0B_B_testAddAtIndex0B", testAddAtIndex(A_setAtIndexOf0B_B(), 0, ELEMENT_A, Result.NoException));
			printTest("A_setAtIndexOf0B_B_testAddAtIndex1B", testAddAtIndex(A_setAtIndexOf0B_B(), 1, ELEMENT_A, Result.NoException));
			printTest("A_setAtIndexOf0B_B_testSetNeg1B", testSet(A_setAtIndexOf0B_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_setAtIndexOf0B_B_testSet0B", testSet(A_setAtIndexOf0B_B(), 0, ELEMENT_A, Result.NoException));
			printTest("A_setAtIndexOf0B_B_testAddB", testAdd(A_setAtIndexOf0B_B(), ELEMENT_A, Result.NoException));
			printTest("A_setAtIndexOf0B_B_testGetNeg1", testGet(A_setAtIndexOf0B_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_setAtIndexOf0B_B_testGet0", testGet(A_setAtIndexOf0B_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testIndexOfA", testIndexOf(A_setAtIndexOf0B_B(), ELEMENT_B, 0));
			printTest("A_setAtIndexOf0B_B_testIndexOfB", testIndexOf(A_setAtIndexOf0B_B(), ELEMENT_A, -1));
			printTest("A_setAtIndexOf0B_B_testRemoveNeg1", testRemoveIndex(A_setAtIndexOf0B_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_setAtIndexOf0B_B_testRemove0", testRemoveIndex(A_setAtIndexOf0B_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testRemove1", testRemoveIndex(A_setAtIndexOf0B_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_setAtIndexOf0B_B_testIterator", testIterator(A_setAtIndexOf0B_B(), Result.NoException));
			printTest("A_setAtIndexOf0B_B_testIteratorHasNext", testIteratorHasNext(A_setAtIndexOf0B_B().iterator(), Result.True));
			printTest("A_setAtIndexOf0B_B_testIteratorNext", testIteratorNext(A_setAtIndexOf0B_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_setAtIndexOf0B_B_testIteratorRemove", testIteratorRemove(A_setAtIndexOf0B_B().iterator(), Result.IllegalState));
			printTest("A_setAtIndexOf0B_B_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(A_setAtIndexOf0B_B(), 1), Result.False));
			printTest("A_setAtIndexOf0B_B_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(A_setAtIndexOf0B_B(), 1), null, Result.NoSuchElement));
			printTest("A_setAtIndexOf0B_B_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(A_setAtIndexOf0B_B(), 1), Result.NoException));
			printTest("A_setAtIndexOf0B_B_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_setAtIndexOf0B_B(), 1)), Result.False));
			printTest("A_setAtIndexOf0B_B_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(A_setAtIndexOf0B_B(), 1)), null, Result.NoSuchElement));
			printTest("A_setAtIndexOf0B_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(A_setAtIndexOf0B_B(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_set0B_B");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> removeFirst() -> [B]
	//  XXX [A,B] -> removeFirst() -> [B]
	////////////////////////////////////////////////

	/** Scenario(16): [A, B] -> removeFirst() -> [B] 
	 * @return [A, B] after removeFirst()
	 */
	private IndexedUnorderedListADT<Integer> AB_removeFirst_B() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		
		list.removeFirst();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> removeFirst() -> [B] */
	private void test_AB_removeFirst_B() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A,B] -> removeFirst() -> [B] \nTests: \n");
			printTest("AB_removeFirst_B_testRemoveFirst", testRemoveFirst(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveLast", testRemoveLast(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveA", testRemoveElement(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveB", testRemoveElement(AB_removeFirst_B(), ELEMENT_A, Result.ElementNotFound));
			printTest("AB_removeFirst_B_testFirst", testFirst(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testLast", testLast(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testContainsA", testContains(AB_removeFirst_B(), ELEMENT_B, Result.True));
			printTest("AB_removeFirst_B_testContainsB", testContains(AB_removeFirst_B(), ELEMENT_A, Result.False));
			printTest("AB_removeFirst_B_testIsEmpty", testIsEmpty(AB_removeFirst_B(), Result.False));
			printTest("AB_removeFirst_B_testSize", testSize(AB_removeFirst_B(), 1));
			printTest("AB_removeFirst_B_testToString", testToString(AB_removeFirst_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeFirst_B_testAddToFrontB", testAddToFront(AB_removeFirst_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddToRearB", testAddToRear(AB_removeFirst_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddAfterCB", testAddAfter(AB_removeFirst_B(), ELEMENT_C, ELEMENT_A, Result.ElementNotFound));
			printTest("AB_removeFirst_B_testAddAfterAB", testAddAfter(AB_removeFirst_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			// IndexedListADT
			printTest("AB_removeFirst_B_testAddAtIndexNeg1B", testAddAtIndex(AB_removeFirst_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testAddAtIndex0B", testAddAtIndex(AB_removeFirst_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddAtIndex1B", testAddAtIndex(AB_removeFirst_B(), 1, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testSetNeg1B", testSet(AB_removeFirst_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testSet0B", testSet(AB_removeFirst_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddB", testAdd(AB_removeFirst_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testGetNeg1", testGet(AB_removeFirst_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testGet0", testGet(AB_removeFirst_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testIndexOfA", testIndexOf(AB_removeFirst_B(), ELEMENT_B, 0));
			printTest("AB_removeFirst_B_testIndexOfB", testIndexOf(AB_removeFirst_B(), ELEMENT_A, -1));
			printTest("AB_removeFirst_B_testRemoveNeg1", testRemoveIndex(AB_removeFirst_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testRemove0", testRemoveIndex(AB_removeFirst_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemove1", testRemoveIndex(AB_removeFirst_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeFirst_B_testIterator", testIterator(AB_removeFirst_B(), Result.NoException));
			printTest("AB_removeFirst_B_testIteratorHasNext", testIteratorHasNext(AB_removeFirst_B().iterator(), Result.True));
			printTest("AB_removeFirst_B_testIteratorNext", testIteratorNext(AB_removeFirst_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testIteratorRemove", testIteratorRemove(AB_removeFirst_B().iterator(), Result.IllegalState));
			printTest("AB_removeFirst_B_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(AB_removeFirst_B(), 1), Result.False));
			printTest("AB_removeFirst_B_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(AB_removeFirst_B(), 1), null, Result.NoSuchElement));
			printTest("AB_removeFirst_B_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(AB_removeFirst_B(), 1), Result.NoException));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeFirst_B(), 1)), Result.False));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_removeFirst_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_removeFirst_B(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeFirst_B");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> removeLast() -> [A]
	//  XXX [A,B] -> removeLast() -> [A]
	////////////////////////////////////////////////

	/** Scenario(17): [A,B] -> removeLast() -> [A] 
	 * @return [A,B] after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> AB_removeLast_A() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> removeLast() -> [A] */
	private void test_AB_removeLast_A() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A,B] -> removeLast() -> [A] \nTests: \n");
			printTest("AB_removeLast_A_testRemoveFirst", testRemoveFirst(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveLast", testRemoveLast(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveA", testRemoveElement(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveB", testRemoveElement(AB_removeLast_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeLast_A_testFirst", testFirst(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testLast", testLast(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testContainsA", testContains(AB_removeLast_A(), ELEMENT_A, Result.True));
			printTest("AB_removeLast_A_testContainsB", testContains(AB_removeLast_A(), ELEMENT_B, Result.False));
			printTest("AB_removeLast_A_testIsEmpty", testIsEmpty(AB_removeLast_A(), Result.False));
			printTest("AB_removeLast_A_testSize", testSize(AB_removeLast_A(), 1));
			printTest("AB_removeLast_A_testToString", testToString(AB_removeLast_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeLast_A_testAddToFrontB", testAddToFront(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddToRearB", testAddToRear(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddAfterCB", testAddAfter(AB_removeLast_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeLast_A_testAddAfterAB", testAddAfter(AB_removeLast_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_removeLast_A_testAddAtIndexNeg1B", testAddAtIndex(AB_removeLast_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testAddAtIndex0B", testAddAtIndex(AB_removeLast_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddAtIndex1B", testAddAtIndex(AB_removeLast_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testSetNeg1B", testSet(AB_removeLast_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testSet0B", testSet(AB_removeLast_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddB", testAdd(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testGetNeg1", testGet(AB_removeLast_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testGet0", testGet(AB_removeLast_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testIndexOfA", testIndexOf(AB_removeLast_A(), ELEMENT_A, 0));
			printTest("AB_removeLast_A_testIndexOfB", testIndexOf(AB_removeLast_A(), ELEMENT_B, -1));
			printTest("AB_removeLast_A_testRemoveNeg1", testRemoveIndex(AB_removeLast_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testRemove0", testRemoveIndex(AB_removeLast_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemove1", testRemoveIndex(AB_removeLast_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeLast_A_testIterator", testIterator(AB_removeLast_A(), Result.NoException));
			printTest("AB_removeLast_A_testIteratorHasNext", testIteratorHasNext(AB_removeLast_A().iterator(), Result.True));
			printTest("AB_removeLast_A_testIteratorNext", testIteratorNext(AB_removeLast_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testIteratorRemove", testIteratorRemove(AB_removeLast_A().iterator(), Result.IllegalState));
			printTest("AB_removeLast_A_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(AB_removeLast_A(), 1), Result.False));
			printTest("AB_removeLast_A_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(AB_removeLast_A(), 1), null, Result.NoSuchElement));
			printTest("AB_removeLast_A_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(AB_removeLast_A(), 1), Result.NoException));
			printTest("AB_removeLast_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeLast_A(), 1)), Result.False));
			printTest("AB_removeLast_A_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_removeLast_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeLast_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_removeLast_A(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeLast_A");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> remove(A) -> [B]
	//  XXX [A,B] -> remove(A) -> [B]
	////////////////////////////////////////////////

	/** Scenario(18): [A, B] -> removeFirst() -> [B] 
	 * @return [A, B] after removeFirst()
	 */
	private IndexedUnorderedListADT<Integer> AB_removeA_B() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		
		list.remove(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> remove(A) -> [B] */
	private void test_AB_removeA_B() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A,B] -> remove(A) -> [B] \nTests: \n");
			printTest("AB_removeA_B_testremoveA", testRemoveFirst(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveLast", testRemoveLast(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveA", testRemoveElement(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveB", testRemoveElement(AB_removeA_B(), ELEMENT_A, Result.ElementNotFound));
			printTest("AB_removeA_B_testFirst", testFirst(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testLast", testLast(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testContainsA", testContains(AB_removeA_B(), ELEMENT_B, Result.True));
			printTest("AB_removeA_B_testContainsB", testContains(AB_removeA_B(), ELEMENT_A, Result.False));
			printTest("AB_removeA_B_testIsEmpty", testIsEmpty(AB_removeA_B(), Result.False));
			printTest("AB_removeA_B_testSize", testSize(AB_removeA_B(), 1));
			printTest("AB_removeA_B_testToString", testToString(AB_removeA_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeA_B_testAddToFrontB", testAddToFront(AB_removeA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddToRearB", testAddToRear(AB_removeA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddAfterCB", testAddAfter(AB_removeA_B(), ELEMENT_C, ELEMENT_A, Result.ElementNotFound));
			printTest("AB_removeA_B_testAddAfterAB", testAddAfter(AB_removeA_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			// IndexedListADT
			printTest("AB_removeA_B_testAddAtIndexNeg1B", testAddAtIndex(AB_removeA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testAddAtIndex0B", testAddAtIndex(AB_removeA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddAtIndex1B", testAddAtIndex(AB_removeA_B(), 1, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testSetNeg1B", testSet(AB_removeA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testSet0B", testSet(AB_removeA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddB", testAdd(AB_removeA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testGetNeg1", testGet(AB_removeA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testGet0", testGet(AB_removeA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testIndexOfA", testIndexOf(AB_removeA_B(), ELEMENT_B, 0));
			printTest("AB_removeA_B_testIndexOfB", testIndexOf(AB_removeA_B(), ELEMENT_A, -1));
			printTest("AB_removeA_B_testRemoveNeg1", testRemoveIndex(AB_removeA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testRemove0", testRemoveIndex(AB_removeA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemove1", testRemoveIndex(AB_removeA_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeA_B_testIterator", testIterator(AB_removeA_B(), Result.NoException));
			printTest("AB_removeA_B_testIteratorHasNext", testIteratorHasNext(AB_removeA_B().iterator(), Result.True));
			printTest("AB_removeA_B_testIteratorNext", testIteratorNext(AB_removeA_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testIteratorRemove", testIteratorRemove(AB_removeA_B().iterator(), Result.IllegalState));
			printTest("AB_removeA_B_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(AB_removeA_B(), 1), Result.False));
			printTest("AB_removeA_B_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(AB_removeA_B(), 1), null, Result.NoSuchElement));
			printTest("AB_removeA_B_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(AB_removeA_B(), 1), Result.NoException));
			printTest("AB_removeA_B_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeA_B(), 1)), Result.False));
			printTest("AB_removeA_B_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_removeA_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeA_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_removeA_B(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeA_B");
			e.printStackTrace();
		}
	}
	
	
	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> remove(B) -> [A]
	//  XXX [A,B] -> remove(B) -> [A]
	////////////////////////////////////////////////

	/** Scenario(19): [A,B] -> remove(B) -> [A] 
	 * @return [A,B] after remove(B)
	 */
	private IndexedUnorderedListADT<Integer> AB_removeB_A() {
		IndexedUnorderedListADT<Integer> list = newList(); 
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		
		list.remove(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> remove(B) -> [A] */
	private void test_AB_removeB_A() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A,B] -> remove(B) -> [A] \nTests: \n");
			printTest("AB_removeB_A_testRemoveFirst", testRemoveFirst(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testremoveB", testRemoveLast(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveA", testRemoveElement(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveB", testRemoveElement(AB_removeB_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeB_A_testFirst", testFirst(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testLast", testLast(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testContainsA", testContains(AB_removeB_A(), ELEMENT_A, Result.True));
			printTest("AB_removeB_A_testContainsB", testContains(AB_removeB_A(), ELEMENT_B, Result.False));
			printTest("AB_removeB_A_testIsEmpty", testIsEmpty(AB_removeB_A(), Result.False));
			printTest("AB_removeB_A_testSize", testSize(AB_removeB_A(), 1));
			printTest("AB_removeB_A_testToString", testToString(AB_removeB_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeB_A_testAddToFrontB", testAddToFront(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddToRearB", testAddToRear(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddAfterCB", testAddAfter(AB_removeB_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeB_A_testAddAfterAB", testAddAfter(AB_removeB_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_removeB_A_testAddAtIndexNeg1B", testAddAtIndex(AB_removeB_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testAddAtIndex0B", testAddAtIndex(AB_removeB_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddAtIndex1B", testAddAtIndex(AB_removeB_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testSetNeg1B", testSet(AB_removeB_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testSet0B", testSet(AB_removeB_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddB", testAdd(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testGetNeg1", testGet(AB_removeB_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testGet0", testGet(AB_removeB_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testIndexOfA", testIndexOf(AB_removeB_A(), ELEMENT_A, 0));
			printTest("AB_removeB_A_testIndexOfB", testIndexOf(AB_removeB_A(), ELEMENT_B, -1));
			printTest("AB_removeB_A_testRemoveNeg1", testRemoveIndex(AB_removeB_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testRemove0", testRemoveIndex(AB_removeB_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemove1", testRemoveIndex(AB_removeB_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeB_A_testIterator", testIterator(AB_removeB_A(), Result.NoException));
			printTest("AB_removeB_A_testIteratorHasNext", testIteratorHasNext(AB_removeB_A().iterator(), Result.True));
			printTest("AB_removeB_A_testIteratorNext", testIteratorNext(AB_removeB_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testIteratorRemove", testIteratorRemove(AB_removeB_A().iterator(), Result.IllegalState));
			printTest("AB_removeB_A_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(AB_removeB_A(), 1), Result.False));
			printTest("AB_removeB_A_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(AB_removeB_A(), 1), null, Result.NoSuchElement));
			printTest("AB_removeB_A_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(AB_removeB_A(), 1), Result.NoException));
			printTest("AB_removeB_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeB_A(), 1)), Result.False));
			printTest("AB_removeB_A_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_removeB_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeB_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_removeB_A(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeB_A");
			e.printStackTrace();
		}
	}
	

	////////////////////////////////////////////////
	// SCENARIO: [A, B] -> add(C) -> [A, B, C]
	//  XXX [A, B] -> add(C) -> [A, B, C]
	////////////////////////////////////////////////

	/** Scenario(28): [A, B] -> add(C) -> [A, B, C]
	 * @return empty list after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> AB_addC_ABC() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		
		list.add(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A, B] -> add(C) -> [A, B, C] */
	private void test_AB_addC_ABC() {

		try {
			// ListADT
			System.out.print("\nSCENARIO: [A, B] -> add(C) -> [A, B, C] \nTests: \n");
			printTest("AB_addC_ABC()_testRemoveFirst", testRemoveFirst(AB_addC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemoveLast", testRemoveLast(AB_addC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemoveA", testRemoveElement(AB_addC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemoveB", testRemoveElement(AB_addC_ABC(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemoveC", testRemoveElement(AB_addC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemoveD", testRemoveElement(AB_addC_ABC(), ELEMENT_D, Result.ElementNotFound));
			printTest("AB_addC_ABC()_testFirst", testFirst(AB_addC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC()_testLast", testLast(AB_addC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC()_testContainsA", testContains(AB_addC_ABC(), ELEMENT_A, Result.True));
			printTest("AB_addC_ABC()_testContainsB", testContains(AB_addC_ABC(), ELEMENT_B, Result.True));
			printTest("AB_addC_ABC()_testContainsC", testContains(AB_addC_ABC(), ELEMENT_C, Result.True));
			printTest("AB_addC_ABC()_testContainsD", testContains(AB_addC_ABC(), ELEMENT_D, Result.False));
			printTest("AB_addC_ABC()_testIsEmpty", testIsEmpty(AB_addC_ABC(), Result.False));
			printTest("AB_addC_ABC()_testSize", testSize(AB_addC_ABC(), 3));
			printTest("AB_addC_ABC()_testToString", testToString(AB_addC_ABC(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_addC_ABC()_testAddToFrontD", testAddToFront(AB_addC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddToRearD", testAddToRear(AB_addC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAfterDA", testAddAfter(AB_addC_ABC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAfterDB", testAddAfter(AB_addC_ABC(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAfterDC", testAddAfter(AB_addC_ABC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAfterDD", testAddAfter(AB_addC_ABC(), ELEMENT_D, ELEMENT_D, Result.ElementNotFound));
			// IndexedListADT
			printTest("AB_addC_ABC()_testAddAtIndexNeg1C", testAddAtIndex(AB_addC_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testAddAtIndex0D", testAddAtIndex(AB_addC_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAtIndex1D", testAddAtIndex(AB_addC_ABC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAtIndex2D", testAddAtIndex(AB_addC_ABC(), 2, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAtIndex2D", testAddAtIndex(AB_addC_ABC(), 3, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testAddAtIndex3D", testAddAtIndex(AB_addC_ABC(), 4, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testSetNeg1D", testSet(AB_addC_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testSet0D", testSet(AB_addC_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testSet1D", testSet(AB_addC_ABC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testSet2D", testSet(AB_addC_ABC(), 2, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testSet3D", testSet(AB_addC_ABC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testAddD", testAdd(AB_addC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC()_testGetNeg1", testGet(AB_addC_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testGet0", testGet(AB_addC_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC()_testGet1", testGet(AB_addC_ABC(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC()_testGet2", testGet(AB_addC_ABC(), 2, ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC()_testGet3", testGet(AB_addC_ABC(), 3, null, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testIndexOfA", testIndexOf(AB_addC_ABC(), ELEMENT_A, 0));
			printTest("AB_addC_ABC()_testIndexOfB", testIndexOf(AB_addC_ABC(), ELEMENT_B, 1));
			printTest("AB_addC_ABC()_testIndexOfC", testIndexOf(AB_addC_ABC(), ELEMENT_C, 2));
			printTest("AB_addC_ABC()_testIndexOfD", testIndexOf(AB_addC_ABC(), ELEMENT_D, -1));
			printTest("AB_addC_ABC()_testRemoveNeg1", testRemoveIndex(AB_addC_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC()_testRemove0", testRemoveIndex(AB_addC_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemove1", testRemoveIndex(AB_addC_ABC(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemove2", testRemoveIndex(AB_addC_ABC(), 2, ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC()_testRemove3", testRemoveIndex(AB_addC_ABC(), 3, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_addC_ABC()_testIterator", testIterator(AB_addC_ABC(), Result.NoException));
			printTest("AB_addC_ABC()_testIteratorHasNext", testIteratorHasNext(AB_addC_ABC().iterator(), Result.True));
			printTest("AB_addC_ABC()_testIteratorNext", testIteratorNext(AB_addC_ABC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC()_testIteratorRemove", testIteratorRemove(AB_addC_ABC().iterator(), Result.IllegalState));
			printTest("AB_addC_ABC()_iteratorNext_testIteratorHasNext", testIteratorHasNext(iteratorAfterNext(AB_addC_ABC(), 1), Result.True));
			printTest("AB_addC_ABC()_iteratorNext_testIteratorHasNext2", testIteratorHasNext(iteratorAfterNext(AB_addC_ABC(), 2), Result.True));
			printTest("AB_addC_ABC()_iteratorNext_testIteratorHasNext3", testIteratorHasNext(iteratorAfterNext(AB_addC_ABC(), 3), Result.False));
			printTest("AB_addC_ABC()_iteratorNext_testIteratorNext", testIteratorNext(iteratorAfterNext(AB_addC_ABC(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC()_iteratorNext_testIteratorRemove", testIteratorRemove(iteratorAfterNext(AB_addC_ABC(), 1), Result.NoException));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 1)), Result.True));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorHasNext2", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 2)), Result.True));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorHasNext3", testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 3)), Result.False));
//			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorNext0", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 0)), ELEMENT_A, Result.IllegalState));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorNext", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorNext2", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 2)), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorNext3", testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 3)), null, Result.NoSuchElement));
			printTest("AB_addC_ABC()_iteratorNextRemove_testIteratorRemove", testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 1)), Result.IllegalState));
			System.out.println("");
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_addC_ABC");
			e.printStackTrace();
		}
	}
	
	
	
	// //////////////////////////
	// XXX  LIST_ADT TESTS
	// //////////////////////////

	/**
	 * Runs removeFirst() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveFirst(IndexedUnorderedListADT<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeFirst();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveLast(IndexedUnorderedListADT<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeLast();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element element to remove
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveElement(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(element);
			if (retVal.equals(element)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (ElementNotFoundException e) {
			result = Result.ElementNotFound;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveElement", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs first() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testFirst(IndexedUnorderedListADT<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.first();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs last() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testLast(IndexedUnorderedListADT<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.last();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs contains() method on a given list and element and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testContains(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			if (list.contains(element)) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testContains", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs isEmpty() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIsEmpty(IndexedUnorderedListADT<Integer> list, Result expectedResult) {
		Result result;
		try {
			if (list.isEmpty()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIsEmpty", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs size() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedSize
	 * @return test success
	 */
	private boolean testSize(IndexedUnorderedListADT<Integer> list, int expectedSize) {
		try {
			return (list.size() == expectedSize);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSize", e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Runs toString() method on given list and attempts to confirm non-default or empty String
	 * difficult to test - just confirm that default address output has been overridden
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testToString(IndexedUnorderedListADT<Integer> list, Result expectedResult) {
		Result result;
		try {
			String str = list.toString();
			System.out.println("toString() output: " + str);
			if (str.length() == 0) {
				result = Result.Fail;
			}
			char lastChar = str.charAt(str.length() - 1);
			if (str.contains("@")
					&& !str.contains(" ")
					&& Character.isLetter(str.charAt(0))
					&& (Character.isDigit(lastChar) || (lastChar >= 'a' && lastChar <= 'f'))) {
				result = Result.Fail; // looks like default toString()
			} else {
				result = Result.ValidString;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testToString", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	
	// /////////////////////////////////////////
	// XXX UNORDERED_LIST_ADT TESTS
	// /////////////////////////////////////////

	/**
	 * Runs addToFront() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToFront(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToFront(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToFront",  e.toString());
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addToRear() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToRear(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToRear(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToRear", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addAfter() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param target
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAfter(IndexedUnorderedListADT<Integer> list, Integer target, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addAfter(element, target);
			result = Result.NoException;
		} catch (ElementNotFoundException e) {
			result = Result.ElementNotFound;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAfter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	
	// /////////////////////////////////////
	// XXX INDEXED_LIST_ADT TESTS
	// /////////////////////////////////////

	/**
	 * Runs add(int, T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAtIndex(IndexedUnorderedListADT<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs add(T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAdd(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs set(int, T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testSet(IndexedUnorderedListADT<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.set(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs get() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testGet(IndexedUnorderedListADT<Integer> list, int index, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.get(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testGet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs remove(index) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveIndex(IndexedUnorderedListADT<Integer> list, int index, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs indexOf() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedIndex
	 * @return test success
	 */
	private boolean testIndexOf(IndexedUnorderedListADT<Integer> list, Integer element, int expectedIndex) {
		//Result result;
		try {
			return list.indexOf(element) == expectedIndex;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIndexOf", e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	
	////////////////////////////
	// XXX  ITERATOR TESTS
	////////////////////////////
	
	/**
	 * Runs iterator() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterator(IndexedUnorderedListADT<Integer> list, Result expectedResult) {
		Result result;
		try {
			//Iterator<Integer> it = list.iterator();
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterator", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator hasNext() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIteratorHasNext(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			if (iterator.hasNext()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIteratorHasNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator next() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedValue the Integer expected from next() or null if an exception is expected
	 * @param expectedResult MatchingValue or expected exception
	 * @return test success
	 */
	private boolean testIteratorNext(Iterator<Integer> iterator, Integer expectedValue, Result expectedResult) {
		Result result;
		try {
			Integer retVal = iterator.next();
			if (retVal.equals(expectedValue)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIteratorNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator remove() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to remove()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIteratorRemove(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			iterator.remove();
			result = Result.NoException;
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIteratorRemove", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	
	//////////////////////////////////////////////////////////
	// XXX HELPER METHODS FOR TESTING ITERATORS 
	//////////////////////////////////////////////////////////
	/**
	 * Helper for testing iterators. Return an Iterator that has been advanced numCallsToNext times.
	 * @param list
	 * @param numCallsToNext
	 * @return Iterator for given list, after numCallsToNext
	 */
	private Iterator<Integer> iteratorAfterNext(IndexedUnorderedListADT<Integer> list, int numCallsToNext) {
		Iterator<Integer> it = list.iterator();
		for (int i = 0; i < numCallsToNext; i++) {
			it.next();
		}
		return it;
	}
	
	/**
	 * Helper for testing iterators. Return an Iterator that has had remove() called once.
	 * @param iterator
	 * @return same Iterator following a call to remove()
	 */
	private Iterator<Integer> iteratorAfterRemove(Iterator<Integer> iterator) {
		iterator.remove();
		return iterator;
	}
		
}// end class ListTester
