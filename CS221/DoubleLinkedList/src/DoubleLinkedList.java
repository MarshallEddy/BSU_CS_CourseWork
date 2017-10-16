import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Fully implemented DoubleLinkedList including full functional
 * Iterator and ListIterator with optional add(), set(), and 
 * remove() methods. This is a node-based, doubly linked list.
 * 
 * @author MarshallEddy
 *
 * @implements DoubleLinkedListADT<T> and IndexedUnorderedListADT<T>
 * @param <T>
 */
public class DoubleLinkedList<T> implements DoubleLinkedListADT<T>, IndexedUnorderedListADT<T>
{
	public int count; 					// number of element in the list
	public int modCount;				// running total of the number of modifications made to the list
	public LinearNode<T> head, tail; 	// first element/last element
//	UnorderedListADT<T> UL = new DoubleLinkedList<T>();
//	UnorderedListADT<T> UL = new DoubleLinkedListADT<T>();
	/**
	 * Creates an empty list.
	 */
	public DoubleLinkedList()
	{
		count = 0;
		head = tail = null;
		modCount = 0;
	}
	
	/**
	 * Removes the first element in this list and returns a reference
	 * to it. Throws an EmptyCollectionException if the list is empty.
	 *	XXX-T removeFirst
	 *
	 * @return a reference to the first element of this list
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T removeFirst() {
		if (isEmpty()) {
			throw new EmptyCollectionException("LinkedList");
		}
		
		LinearNode<T> tempNode = head;
		T retVal = tempNode.getElement();
		if (head.getNext() == null) {
			tail = null;
		} 
		head = head.getNext();
		count --;
		modCount++;
		return retVal;
	}

	/**
	 * Removes the last element in this list and returns a reference
	 * to it. Throws an EmptyCollectionException if the list is empty.
	 *	XXX-T removeLast()
	 *
	 * @return the last element in this list
	 * @throws EmptyCollectionException if the list is empty    
	 */
	public T removeLast() {
		if (isEmpty()) {
			throw new EmptyCollectionException("LinkedList");
		}
		
		LinearNode<T> tempNode = tail;
		T retVal = tempNode.getElement();
		if (head.getNext() == null) {
			head = null;
		}
		tail = tail.getPrevious();
		count--;
		modCount++;
		if (count == 1) {
			head.setNext(null);
		}
		return retVal;
	}

	/**
	 * Removes the first instance of the specified element from this
	 * list and returns a reference to it. Throws an EmptyCollectionException 
	 * if the list is empty. Throws a ElementNotFoundException if the 
	 * specified element is not found in the list.
	 *	XXX-T remove(T targetElement)
	 *
	 * @param  targetElement the element to be removed from the list
	 * @return a reference to the removed element
	 * @throws ElementNotFoundException if the target element is not found
	 */
	public T remove(T targetElement) {
		if (isEmpty())
			throw new ElementNotFoundException("LinkedList");
		
		boolean found = false;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;

		while (current != null && !found) {
			if (targetElement.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		if (!found)
			throw new ElementNotFoundException("LinkedList");
		if (size() == 1)
			head = tail = null;
		else if (current.equals(head))
			head = current.getNext();
		else if (current.equals(tail)) {
			tail = previous;
			tail.setNext(null);
		} else
			previous.setNext(current.getNext());
		
		count--;
		modCount++;
		return current.getElement();
	}

	/**
	 * Returns the first element in this list without removing it. 
	 *	XXX-T first()
	 *
	 * @return the first element in this list
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T first() {
		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");
		
		return head.getElement();
	}

	/**
	 * Returns the last element in this list without removing it. 
	 *	XXX-T last()
	 *
	 * @return the last element in this list  
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T last() {
		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");
		
		return tail.getElement();
	}

	/**
	 * Returns true if the specified element is found in this list and 
	 * false otherwise.
	 *	XXX-contains(T targetElement)
	 *
	 * @param  targetElement the element that is sought in the list
	 * @return true if the element is found in this list
	 */
	public boolean contains(T targetElement) {
		boolean contained = false;
		for (T element : this) {
			if (element == targetElement) {
				contained = true;
				break;
			}
		}
		return contained;
	}

	/**
	 * Returns true if this list is empty and false otherwise.
	 *	XXX-isEmpty()
	 *
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return head == null && tail == null;
	}

	/**
	 * Returns the number of elements in this list.
	 *	XXX-size()
	 *
	 * @return the number of elements in the list
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns a string representation of this list.
	 *	XXX-String toString()
	 *
	 * @return a string representation of the list    
	 */
	public String toString()
	{
		LinearNode<T> current = head;
		String s = "";
		s += "[";
		for(int i = 0; i < count; i++)  {
			s += "" + current.getElement() + ", ";
			current = current.getNext();
		}
		s += "]";
		s = s.replace(", ]", "]");
//		s = s.replace("1", "A");
//		s = s.replace("2", "B");
//		s = s.replace("3", "C");
//		s = s.replace("4", "D");
		s = s.replace("[]", "[ ]");
		return s;
	}

	/**
	 * Adds the specified element to the front of this list.
	 *	XXX-addToFront(T element)
	 *
	 * @param element the element to be added to the list
	 */
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (isEmpty()) {
			tail = newNode;
		} else {
			head.setPrevious(newNode);
		}
		newNode.setNext(head);
		head = newNode;
		count++;	
		modCount++;
	}

	/**
	 * Adds the specified element to the rear of this list.
	 *	XXX-addToRead(T element)
	 *
	 * @param element the element to be added to the list
	 */
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (isEmpty()) {
			head = newNode;
		} else {
			tail.setNext(newNode);
		}
		newNode.setPrevious(tail);
		tail = newNode;
		count++;
		modCount++;
	}

	/**
	 * Adds the specified element to this list after the given target.
	 *	XXX-addAfter(T element, T target)
	 *
	 * @param  element the element to be added to this list
	 * @param  target the target element to be added after
	 * @throws ElementNotFoundException if the target is not found
	 */
	public void addAfter(T element, T target) {
		if (isEmpty())
			throw new ElementNotFoundException("LinkedList");
		
		LinearNode<T> current = head;
		LinearNode<T> newNode = new LinearNode<T>(element);
		while (current.getElement() != target) {
			current = current.getNext();
			if (current == null) {
				throw new ElementNotFoundException("LinkedList");
			}
		}
		if (current == tail) {
			newNode.setNext(null);
			tail = newNode;
		} else {
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			current.setPrevious(newNode);
		}
		newNode.setPrevious(current);
		current.setNext(newNode);
		count++;
		modCount++;
	}
	
	/**  
	 * Inserts the specified element at the specified index. 
	 * 	XXX-add(int index, T element)
	 * 
	 * @param index   the index into the array to which the element is to be
	 *                inserted.
	 * @param element the element to be inserted into the array   
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	public void add(int index, T element) {
		if (index < 0 || index > count)
			throw new IndexOutOfBoundsException("LinkedList");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		LinearNode<T> newNode = new LinearNode(element);
		if (isEmpty()) {
			head = tail = newNode;
			count++;
		} else {
			if (index == 0) addToFront(element);
			else if (index == size()) addToRear(element);
			else {
				LinearNode<T> before = null;
				LinearNode<T> after = head;
				while (index > 0) {
					before = after;
					after = after.getNext();
					index--;
				}
				LinearNode<T> current = new LinearNode<T>(element);
				current.setNext(after);
				current.setPrevious(before);
			
				before.setNext(current);
				after.setPrevious(current);
				count++;
			}
		}
			modCount++;
	}		
	
	/**  
	 * Adds the specified element to the rear of this list. 
	 *	XXX-add(T element)
	 *
	 * @param element  the element to be added to the rear of the list    
	 */
	public void add(T element) {
		addToRear(element);
	}

	/**  
	 * Sets the element at the specified index. 
	 *	XXX-set(int index, T element)
	 *
	 * @param index   the index into the array to which the element is to be set
	 * @param element the element to be set into the list
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	@SuppressWarnings("unchecked")
	public void set(int index, T element) {
		if (index < 0 || index > size()-1) {
				throw new IndexOutOfBoundsException("LinkedList");
		} else if (isEmpty() && index == 0) {
				throw new IndexOutOfBoundsException("LinkedList");
		} else if (isEmpty()) {
			@SuppressWarnings("rawtypes")
			LinearNode<T> newNode = new LinearNode(element);
			head = tail = newNode;
			count++;
		} else{
			LinearNode<T> current = head;
			for (int i = 0; i < index; i ++) {
				current = current.getNext();
			}
			current.setElement(element);
			if (index == 0) {
				head = current;
			}
		}
		modCount++;
	}

	/**  
	 * Returns a reference to the element at the specified index. 
	 *	XXX-T get(int index)
	 *
	 * @param index  the index to which the reference is to be retrieved from
	 * @return the element at the specified index    
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	public T get(int index) {
		if (index < 0 || index > size()-1)
			throw new IndexOutOfBoundsException("LinkedList");

		if (isEmpty())
			throw new IndexOutOfBoundsException("LinkedList");
		
		LinearNode<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getElement();
	}
	
	/**  
	 * Returns the index of the specified element. 
	 *	XXX-indexOf(T element)
	 *
	 * @param element  the element for the index is to be retrieved
	 * @return the integer index for this element or -1 if element is not in the list
	 */
	public int indexOf(T element) {
		LinearNode<T> current = head;
		int retVal = -1;
		for (int i = 0; i < count; i++) {
			if (current.getElement() == element) {
				retVal = i;
				break;
			} else {current = current.getNext();}
		}	
		return retVal;
	}

	/**  
	 * Returns the element at the specified index and then removes it. 
	 *	XXX-T remove(int index)
	 *
	 * @param index the index of the element to be retrieved
	 * @return the element at the given index    
	 * @throws IndexOutOfBoundsException for invalid index
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T remove(int index) {
		if (index < 0 || index > count-1)
			throw new IndexOutOfBoundsException("LinkedList");
		else if (isEmpty() && index == 0) 
			throw new IndexOutOfBoundsException("LinkedList");
		
		else if (isEmpty()) 
			throw new EmptyCollectionException("LinkedList");
		
		if (index == 0) return removeFirst();
		else if (index == size()-1) return removeLast();
		
		LinearNode<T> previous = null;
		LinearNode<T> current = head;
		while (index > 0) {
			previous = current;
			current = current.getNext();
			index--;
		}
		previous.setNext(current.getNext());
		current.getNext().setPrevious(previous);
		current.setPrevious(previous);
		count --;
		modCount++;
		return current.getElement();
	}

	/**
	 * XXX-Iterator<T> iterator() 
	 * 				   listIterator()
	 */
	
	/**
	 * Returns an iterator for the elements in this list. 
	 *
	 * @return an iterator over the elements of the list
	 */
	public Iterator<T> iterator()
	{
		return new LinkedListIterator();
	}
	/**
	 * Returns an iterator for the elements in this list.
	 * 
	 * @param index Starting Index of iterator
	 * @return an iterator over the elements of the list
	 */															
	public Iterator<T> iterator(int index) {
		if (index < 0 || index > count)					    	// ////////////////////////////////////
			throw new IndexOutOfBoundsException("LinkedList");	// Do not think this is needed, but	//
		return new LinkedListIterator(index);					// have it for a precaution.	   //
	}															// /////////////////////////////////
	
	/**
	 * Returns a listIterator for the elements in this list.
	 * 
	 * @return an iterator over the elements of the list
	 */
	public ListIterator<T> listIterator() {
		return new LinkedListIterator();
	}
	
	/**
	 * Returns a list-iterator of the elements in this list 
	 * (in proper sequence), starting at the specified position
	 *  in the list.
	 *  
	 *  @param index the starting index of the iterator.
	 *  @throws 
	 *  @return	it LinkedListIterator-and iterator over the elements
	 *  		   of the list
	 */
	public ListIterator<T> listIterator(int startingIndex) {
		if (startingIndex < 0 || startingIndex > count)
			throw new IndexOutOfBoundsException("LinkedList");
		return new LinkedListIterator(startingIndex);
	}
	
	// ///////////////////////////
   // XXX  LinkedListIterator  //
  // ///////////////////////////
/**
 * LinkedListIterator represents an iterator for a linked list of linear nodes.
 * Implements Iterator<T> and ListIterator<T>
 * @author MarshallEddy
 */
private class LinkedListIterator implements Iterator<T>, ListIterator<T>
{	//Integers
	private int iteratorModCount;  			// the "version" of the list at the time the Iterator was created
	private int position;					// the position of the iterator("cursor")
	//LinearNodes
	private LinearNode<T> current; 		    // the current position	
	private LinearNode<T> lastRetVal;		// the last returned value
	//Booleans
	private boolean canRemove = false; 		//checks to see if you can use the canRemove() method
	private boolean canSet = false; 		//checks to see if you can use the set(element) method

	/**
	 * Sets up this iterator using the specified items.
	 *
	 * @param collection  the collection the iterator will move over
	 * @param size        the integer size of the collection
	 */
	public LinkedListIterator()
	{
		current = head;
		position = 0;
		iteratorModCount = modCount;
	}
	
	/**
	 * Sets up this iterator using the specified items.
	 *
	 * @param collection  the collection the iterator will move over
	 * @param size        the integer size of the collection
	 */
	public LinkedListIterator(int index)
	{
		current = head;
		if (index < 0 || index > count)
			throw new IndexOutOfBoundsException("LinkedList");
		LinkedListIterator iterator = new LinkedListIterator();
		for (int i = 0; i < index && iterator.hasNext(); i++) {
			iterator.next();
		}
		position = index;
		iteratorModCount = modCount;
	}
	
	/**
	 * Returns a string representation of this list.
	 * Used to help test the correctness of an iterator
	 * vs. a list.
	 *	XXX-String toString()
	 *
	 * @return a string representation of the list    
	 */
	public String toString()
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		LinearNode<T> current = head;
		String s = "";
		s += "[";
		for(int i = 0; i < count; i++)  {
			s += "" + current.getElement() + ", ";
			current = current.getNext();
		}
		s += "]";
		s = s.replace(", ]", "]");
//		s = s.replace("1", "A");
//		s = s.replace("2", "B");
//		s = s.replace("3", "C");
//		s = s.replace("4", "D");
		s = s.replace("[]", "[ ]");
		return s;
	}
	
	/**
	 * Returns true if this iterator has at least one more element
	 * to deliver in the iteration.
	 *
	 * @return  true if this iterator has at least one more element to deliver
	 *          in the iteration
	 * @throws  ConcurrentModificationException if the collection has changed
	 *          while the iterator is in use
	 */
	public boolean hasNext() throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount) 
			throw new ConcurrentModificationException();
		
		return (current != null);
	}
	
	/**
	 * Returns true if this iterator has at least one more preceding element
	 * to deliver in the iterator.
	 * 
	 * @return true if this iterator has at least one more preceding element
	 * 		   to deliver in the iterator
	 * @throws ConcurrentModificationException if the collection has changed
	 * 		   while the iterator is in use
	 */
	public boolean hasPrevious() throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount) 
			throw new ConcurrentModificationException();
//		return (current != head);
		return (position > 0);
	}
	
	/**
	 * Returns the next element in the iteration. If there are no
	 * more elements in this iteration, a NoSuchElementException is
	 * thrown.
	 *
	 * @return the next element in the iteration
	 * @throws NoSuchElementException if the iterator is empty
	 * @throws ConcurrentModificationException if the collection has changed
	 * 		   while the iterator is in use
	 */
	public T next() throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		else if (!hasNext())
			throw new NoSuchElementException();
		lastRetVal = current;
		T result = current.getElement();
		current = current.getNext();
		
		position++;
		iteratorModCount++;
		modCount++;
		canRemove = canSet = true;
		
		return result;
	}
	
	/**
	 * Returns the previous element in the iteration. If there are no
	 * more elements in this iteration, a NoSuchElementException is
	 * thrown.
	 * 
	 * @return the previous element in the iteration
	 * @throws NoSuchElementException if the iterator is empty
	 * @throws ConcurrentModificationException if the collection had changed
	 * 		   while the iterator is in use
	 */
	public T previous() throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		else if (!hasPrevious())
			throw new NoSuchElementException();
		lastRetVal = current;
		T result = current.getElement();
		current = current.getPrevious();
		
		position--;
		iteratorModCount++;
		modCount++;
		canRemove = canSet = true;
		return result;

	}
	
	/**
	 * Returns the index of the element that would be returned
	 * by a subsequent call to next(). (Returns list size if
	 * the list iterator is at the end of the list.)
	 * 
	 * @return the index of the element that would be returned
	 * 		   by a subsequent call to next, or list size if the
	 * 		   list iterator is at the end of the list
	 * @throws ConcurrentModificationException if the collection had changed
	 * 		   while the iterator is in use
	 */
	public int nextIndex()
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		if (!hasNext())
			{return size();}
		
		else return (position+1);
	}
	
	/**Returns the index of the element that would be returned by
	 * a subsequent call to previous(). (Returns -1 if the list
	 * iterator is at the beginning of the list.)
	 * 
	 * @return the previous index, -1 if the list iterator is at
	 * the beginning of the list
	 * @throws ConcurrentModificationException if the collection had changed
	 * 		   while the iterator is in use
	 */
	public int previousIndex()
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		if (position == 0)
			{return -1;}
		
		else return (position-1);
	}
	
	/**
	 * Removes from the list the last element that was returned by
	 * next() or previous() (optional operation). This call can only
	 * be made once per call to next or previous. It can be made only
	 * if add(E) has not been called after the last call to next or
	 * previous.
	 * 
	 * @throws IllegalStateException if neither next nor previous have been
	 * called, or remove or add have been called after the last call to next
	 * or previous
	 * @throws ConcurrentModificationException if the collection had changed
	 * 		   while the iterator is in use
	 */
	public void remove() throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		if (!canRemove)
			throw new IllegalStateException("LinkedList");
		if (count == 1){
			head = tail = null;
		} else if (lastRetVal == head) {
			head = head.getNext();
			head.setPrevious(null);
		} else if (lastRetVal == tail) {
			tail = tail.getPrevious();
		} else {
			if(current == lastRetVal) {
				current = lastRetVal.getNext();
			} else {
				position--;
			}
		LinearNode<T> prevNode = lastRetVal.getPrevious();
		LinearNode<T> nextNode = lastRetVal.getNext();
		nextNode = prevNode.getNext();
		prevNode = nextNode.getPrevious();
		}
		canRemove = false;
		canSet = false;
		position--;
		count--;
		modCount++;
		iteratorModCount++;
	}
	
	/**
	 * Replaces the last element returned by next() or previous()
	 * with the specified element (optional operation). This call
	 * can be made only if neither remove() nor add(E) have been
	 * called after the last call to next or previous.
	 * 
	 * @param element - the element with which to replace the last element
	 * 		  returned by next or previous
	 * @throws ConcurrentModificationException if the collection had changed
	 * 		   while the iterator is in use
	 */
	public void set(T element) throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		if (canSet == true) {
			lastRetVal.setElement(element);
//			int i = indexOf(lastRetVal.getElement());
//			DoubleLinkedList.this.set(i, element);
		}
		canRemove = canSet = false;
		modCount++;
		iteratorModCount++;
	}
	
	/**
	 * Inserts the specified element into the list.
	 * The element is inserted immediately before the
	 * element that would be returned by next(), if any,
	 * and after the element that would be returned by previous(),
	 * if any. (If the list contains no elements, the new element
	 * becomes the sole element on the list.) The new element is
	 * inserted before the implicit cursor: a subsequent call to
	 * next would be unaffected, and a subsequent call to previous
	 * would return the new element. (This call increases by one the
	 * value that would be returned by a call to nextIndex or previousIndex.)
	 * 
	 * @param element - the element to insert
	 * @throws ConcurrentModificationException if the collection had changed
	 * 		   while the iterator is in use
	 */
	public void add(T element) throws ConcurrentModificationException
	{
		if (iteratorModCount != modCount)
			throw new ConcurrentModificationException();
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(count == 0) {
			head = tail = newNode;
			head.setElement(element);
			tail.setElement(element);
			newNode.setPrevious(null);
			newNode.setNext(tail);
			newNode.getNext().setNext(null);
		} else if (count == 1 || lastRetVal == head || lastRetVal == null) {
			DoubleLinkedList.this.add(position, element);
		} else {
			addAfter(element, lastRetVal.getElement());
		}
			canRemove = false;
			canSet = false;
			position++;
			modCount++;
			iteratorModCount++;
			count++;
		}
	}
}
