package uno.game.utility;

import java.util.Collection;

/**
 * CircularLinkedList<E> class is two way implemented circular linked list,
 * where last node always point to first node and vice versa. Being two way
 * circular implementation helps to play in round robin pattern. And also helps
 * in navigates to previous, current and next player.
 *
 * @param <E>
 *            the E type, can be custom defined
 * 
 * @author Amar Verma
 */
public class CircularLinkedList<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;
    private Node<E> current;

    /**
     * Adds the object to the linked list.
     *
     * @param object
     *            the object of E type
     * @return true, if added successfully
     */
    public boolean add(E object) {
	Node<E> ln = last;
	Node<E> node = new Node<E>(ln, object, first);
	last = node;
	if (ln == null) {
	    first = node;
	    current = first;
	} else {
	    ln.next = last;
	    first.prev = last;
	}
	size++;
	return true;
    }

    /**
     * Add all the collection to the current linked list.
     *
     * @param collection
     *            the collection
     * @return true, added all
     */
    public boolean addAll(Collection<E> collection) {

	if (collection.size() > 0) {
	    for (E object : collection) {
		if (object != null) {
		    add(object);
		}
	    }
	    return true;
	}
	return false;
    }

    /**
     * Points to Next node in the linked list.
     *
     * @return the object in the next node.
     */
    public E next() {
	final Node<E> cn = current;
	current = current.next;
	return cn.item;
    }

    /**
     * Points to Previous node in the linked list.
     *
     * @return the object in the previous node.
     */
    public E previous() {
	final Node<E> cn = current;
	current = current.prev;
	return cn.item;
    }

    /**
     * Current node object
     *
     * @return the object in the current node
     */
    public E current() {
	return current.item;
    }

    /**
     * Reset to first node
     *
     * @return the current pointing node object
     */
    public E resetToFirst() {
	current = first;
	return current.item;
    }

    /**
     * Removes the node from linked list
     *
     * @param object
     *            the object to be removed
     * @return true, if removes successfully
     */
    public boolean remove(E object) {
	if (object != null && size > 0) {
	    Node<E> n = first;
	    do {
		if (n.item.equals(object)) {
		    Node<E> next = n.next;
		    Node<E> prev = n.prev;
		    prev.next = next;
		    next.prev = prev;
		    size--;
		}
		n = n.next;
	    } while (n != first);
	    return true;
	}
	return false;
    }

    /**
     * Size of linked list.
     *
     * @return the int size
     */
    public int size() {
	return size;
    }

    /**
     * The Class Node, contains previous and next node pointers and E type item
     * (can assign custom defined objects)
     *
     * @param <E>
     *            the element type, custom type
     */
    private static class Node<E> {

	E item;
	Node<E> prev;
	Node<E> next;

	public Node(Node<E> previous, E element, Node<E> next) {
	    this.item = element;
	    this.prev = previous;
	    this.next = next;
	}
    }

}
