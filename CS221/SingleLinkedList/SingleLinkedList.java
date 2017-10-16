/**
 * SingleLinkedList represents a LinearNode-based implementation of both an unordered and indexed list.
 *
 * @author Java Foundations, Marshall Eddy
 * @version 4.0
 */
public class SingleLinkedList<T> extends AbstractLinkedList<T> implements IndexedUnorderedListADT<T>
{	
	/**
	 * Adds the specified element to the front of this list.
	 *
	 * @param element the element to be added to the list
	 */
	public void addToFront(T element)
	{
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(head == null) {
			head = newNode;
		}
		if(tail == null) {
			tail = head;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
		modCount++;
		count++;
	}

	/**
	 * Adds the specified element to the rear of this list.
	 *
	 * @param element the element to be added to the list
	 */
	public void addToRear(T element)
	{
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(head == null) {
			head = tail = newNode;
		} else {
			LinearNode<T> current = head;
			while(current.getNext() != null) {
				current = current.getNext();
			}
			LinearNode<T> tempNode = new LinearNode<T>(element);
			current.setNext(tempNode);
			tail = tempNode;
		}
		count++;
		modCount++;
		
	}	

	/**
	 * Adds the specified element to this list after the given target.
	 *
	 * @param  element the element to be added to this list
	 * @param  target the target element to be added after
	 * @throws ElementNotFoundException if the target is not found
	 */
	public void addAfter(T element, T target)
	{
		if(isEmpty())
			throw new ElementNotFoundException("LinkedList");
		
		LinearNode<T> current = head;
		LinearNode<T> newNode = new LinearNode<T>(element);
		LinearNode<T> tempNode = null;
		while(current.getElement() != target) {
			current = current.getNext();
			if(current == null) {
				throw new ElementNotFoundException("LinkedList");
			}
		}
		if(current.getNext() != null) {
			tempNode = current.getNext();
		}
		current.setNext(newNode);
		newNode.setNext(tempNode);
		count++;
		modCount++;
		
	}

	/**  
	 * Inserts the specified element at the specified index. 
	 * 
	 * @param index   the index into the array to which the element is to be
	 *                inserted.
	 * @param element the element to be inserted into the array   
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	@SuppressWarnings("rawtypes")
	public void add(int index, T element) {
		@SuppressWarnings("unchecked")
		LinearNode<T> newNode = new LinearNode(element);
		if(index < 0 || index > count)
			throw new IndexOutOfBoundsException("LinkedList");
				
			if(isEmpty()) {
				head = tail = newNode;
			} else{
				if(index == 0) {
					newNode.setNext(head);
					head = newNode;
				} else {
					LinearNode<T> current = head;
					for(int i = 0; i < index-1; i++) {
						current = current.getNext();
					}
					newNode.setNext(current.getNext());
					current.setNext(newNode);
					if(current == tail) {
						tail = newNode;
					}
				}
			}
			count++;
			modCount++;
	}


	/**  
	 * Adds the specified element to the rear of this list. 
	 *
	 * @param element  the element to be added to the rear of the list    
	 */
	public void add(T element) {
		addToRear(element);
	}

	/**  
	 * Sets the element at the specified index. 
	 *
	 * @param index   the index into the array to which the element is to be set
	 * @param element the element to be set into the list
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	@SuppressWarnings("unchecked")
	public void set(int index, T element) {
		if(index < 0 || index > size()-1) {
				throw new IndexOutOfBoundsException("LinkedList");
		} else if(isEmpty() && index == 0) {
				throw new IndexOutOfBoundsException("LinkedList");
		} else if(isEmpty()) {
			@SuppressWarnings("rawtypes")
			LinearNode<T> newNode = new LinearNode(element);
			head = tail = newNode;
		} else{
			LinearNode<T> current = head;
			for(int i = 0; i < index; i ++) {
				current = current.getNext();
			}
			current.setElement(element);
			if(index == 0) {
				head = current;
			}
		}
		
	}

	/**  
	 * Returns a reference to the element at the specified index. 
	 *
	 * @param index  the index to which the reference is to be retrieved from
	 * @return the element at the specified index    
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	public T get(int index) {
		if(index < 0 || index > size()-1)
			throw new IndexOutOfBoundsException("LinkedList");

		if(isEmpty())
			throw new IndexOutOfBoundsException("LinkedList");
		
		LinearNode<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getElement();
	}

	/**  
	 * Returns the index of the specified element. 
	 *
	 * @param element  the element for the index is to be retrieved
	 * @return the integer index for this element or -1 if element is not in the list
	 */
	public int indexOf(T element) {
		LinearNode<T> current = head;
		int retVal = -1;
		for(int i = 0; i < count; i++) {
			if(current.getElement() == element) {
				retVal = i;
				break;
			} else {current = current.getNext();}
		}	
		return retVal;
	}

	/**  
	 * Returns the element at the specified element. 
	 *
	 * @param index the index of the element to be retrieved
	 * @return the element at the given index    
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	public T remove(int index) {
		if(index < 0 || index > count-1)
			throw new IndexOutOfBoundsException("LinkedList");
		else if(isEmpty() && index == 0) 
			throw new IndexOutOfBoundsException("LinkedList");
		
		else if(isEmpty()) 
			throw new EmptyCollectionException("LinkedList");
		
		T retVal = null;
		LinearNode<T> current = head;
		LinearNode<T> prevNode = head;
		if(index == 0 && count == 1)
		{
			retVal = current.getElement();
		} else {
			for(int i = 0; i < index; i++) {
				prevNode = current;
				current = current.getNext();
			}
		}
		
		
		if(index == 0 && count > 2) {
			retVal = head.getElement();
			LinearNode<T> tempNode = new LinearNode<T>(head.getNext().getElement());
			head = tempNode;
			tempNode.setNext(tail);
			tail = tempNode.getNext();
		}
		if(head == null) {
			retVal = null;
		} else {
			retVal = current.getElement();
			if(index == 1 && count == 3) {
				LinearNode<T> tempNode2 = current.getNext();
				current.setElement(null);
				
				head = prevNode;
				head.setNext(tail);
				tail = tempNode2;
				tail.setNext(null);
			}
			count--;
			modCount++;
			if(count == 1 && index == 0) {
				head = tail;
				head.setNext(null);
			}
			if(count == 1 && index == 1) {
			tail = head;
			head.setNext(null);
			}
		}
		return retVal;
	}
}
