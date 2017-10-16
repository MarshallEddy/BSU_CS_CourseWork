/**
 * LinearNode represents a node in a double-linked list.
 *
 * @author Java Foundations, MarshallEddy(edited for double linked list use)
 * @version 4.0
 */
public class LinearNode<E>
{
    private LinearNode<E> next;
    private LinearNode<E> prev;
    private E element;
    
    /**
     * Creates an empty node.
     */
    public LinearNode()
    {
        next = null;
        prev = null;
        element = null;
    }
    
    /**
     * Creates a node storing the specified element.
     *
     * @param elem the element to be stored within the new node
     */
    public LinearNode(E elem)
    {
        next = null;
        prev = null;
        element = elem;
    }
    
    /**
     * Returns the node that follows this one.
     *
     * @return the node that follows the current one
     */
    public LinearNode<E> getNext()
    {
        return next;
    }
    
    /**
     * Returns the node that precedes this one.
     * 
     * @return the node that precedes the current one
     */
    public LinearNode<E> getPrevious()
    {
    	return prev;
    }
    
    /**
     * Sets the node that follows this one.
     *
     * @param node the node to be set to follow the current one
     */
    public void setNext(LinearNode<E> node)
    {
        next = node;
    }
    
    /**
     * Sets the node that precedes this one.
     * 
     * @param node the node to be set to precede the current one
     */
    public void setPrevious(LinearNode<E> node)
    {
    	prev = node;
    }
    
    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in this node
     */
    public E getElement()
    {
        return element;
    }
    
    /**
     * Sets the element stored in this node.
     *
     * @param elem the element to be stored in this node
     */
    public void setElement(E elem)
    {
        element = elem;
    }
}

