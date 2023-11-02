package hr.fer.oprpp1.custom.collections;

/**
 * An implementation of a linked list collection with all the basic and necessary methods to use a linked list.
 * Uses a private class to represent each node in the list.
 * @author Vito Sabalic
 *
 */
public class LinkedListIndexedCollection extends Collection{
	
	
	
	/**
	 * A class which represents a node inn a Linked List
	 * @author Vito Sabalic
	 *
	 */
	private static class ListNode{
		private ListNode previous;
		private ListNode next;
		private Object value;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	
	/**
	 * A basic constructor which assigns a null value to all the pointers and 0 to the value variable
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	/**
	 * A constructor which uses a previous implementation of a collection and assigns each variable of the 
	 * aforementioned collection in this collection
	 * @param collection The collection whose values are imported into this class
	 */
	public LinkedListIndexedCollection(Collection collection) {
		
		class AddToLinkedListProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		collection.forEach(new AddToLinkedListProcessor());
	}
	
	

	/**
	 * Creates a new node and assigns the value from the parameter to the node
	 *@param The value to be added
	 *@throws throws a {@link NullPointerException} if a null value is to be added
	 */
	@Override
	public void add(Object value) {
		
		if(value == null) {
			throw new NullPointerException("A null value cannot be added");
		}
		
		ListNode newNode = new ListNode();
		
		newNode.value = value;
		newNode.next = null;
		
		if(last == null) {
			first = newNode;
		}
		else {
			last.next = newNode;
		}
		newNode.previous = last;
		last = newNode;
		
		size++;
	}
	
	/**
	 * Gets a value from a node based on its index.
	 * The index is taken care internally.
	 * @param index the index from which the value is taken 
	 * @return returns the taken value
	 */
	public Object get(int index) {
		
		if(index < 0 || index >= size) {
			throw new IllegalArgumentException("The index is out of bounds");
		}
		
		ListNode p = new ListNode();
		int i = 0;
		for(p = first; !(p == null) && i < index; p = p.next) {
			i++;
		}
		
		return p.value;
	}
	
	/**
	 *Clears the entire list class
	 */
	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		
		return;
		
	}
	
	/**
	 * Creates and inserts a new node whose value is assigned from the given parameter.
	 * The entire list is shifted by one index and the aforementioned node is inserted.
	 * @param value the value to be added
	 * @param position the position into which the node is inserted
	 * @throws throws a {@link NullPointerException} if a null value is to be added.
	 */
	public void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IllegalArgumentException("Invalid position");
		}
		
		if(value == null) {
			throw new NullPointerException("A null value cannot be added");
		}
		
		ListNode p = new ListNode();
		int i = 0;
		for(p = first; !(p == null) && i < position; p = p.next) {
			i++;
		}
		
		ListNode newNode = new ListNode();
		
		newNode.value = value;
		
		if(p == first) {
			newNode.next = p;
			p.previous = newNode;
			first = newNode;
		}
		else if(first == null) {
			first = newNode;
			last = newNode;
		}
		else {
			newNode.previous = p.previous;
			newNode.next = p;
			p.previous.next = newNode;
			p.previous = newNode;
		}
		
		size++;
		
		return;
		
	}
	
	/**
	 * Returns the index of the node which has the value from the parameter.
	 * @param value the searched value
	 * @return returns the position of the node in the form of an index
	 */
	public int indexOf(Object value) {
		
		if(value == null) {
			return -1;
		}
		
		ListNode p = new ListNode();
		int i = 0;
		for(p = first; !(p == null); p = p.next ) {
			if(p.value.equals(value)) {
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	/**
	 * Removes the node which is positioned at the given index.
	 * @param index the index which represents the position of the node to be removed.
	 * @throw throws a {@link IllegalArgumentException} if the index is out of range of the list
	 */
	public void remove(int index) {
		
		if(index < 0 && index > size) {
			throw new IllegalArgumentException("The given index is larger than the size of the array");
		}
		
		ListNode p = new ListNode();
		int i = 0;
		for(p = first; !(p == null) && i < index; p = p.next) {
			i++;
		}
		
		if(p.next == null) {
			p.previous.next = null;
			last = p.previous;
		}
		else if(p.previous == null) {
			p.next.previous = null;
			first = p.next;
		}
		else {
			p.previous.next = p.next;
			p.next.previous = p.previous;
		}
		
		p = null;
		size--;
		
		return;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	
	@Override
	public boolean contains(Object value) {
		
		return indexOf(value) != -1;
		
	}
	
	@Override
	public boolean remove(Object value) {
		
		if(value == null) {
			throw new NullPointerException("The given index is larger than the size of the array");
		}
		
		ListNode p = new ListNode();
		for(p = first; !(p == null); p = p.next) {
			
			if(p.value.equals(value)) {
				if(p.next == null) {
					p.previous.next = null;
					last = p.previous;
				}
				else if(p.previous == null) {
					p.next.previous = null;
					first = p.next;
				}
				else {
					p.previous.next = p.next;
					p.next.previous = p.previous;
				}
				
				p = null;
				size--;
				return true;
			}
			
		}
		
		
		return false;
		
	}
	
	@Override
	public Object[] toArray() {
		
		if(size <= 0) {
			throw new NullPointerException("The array cannot contain null values");
		}
		
		Object[] array = new Object[size];
		
		ListNode p = new ListNode();
		int index = 0;
		
		for(p = first; !(p == null); p = p.next) {
			
			array[index] = p.value;
			index++;
			
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		
		ListNode p = new ListNode();
		
		for(p = first; !(p == null); p = p.next) {
			processor.process(p.value);
		}
		
		return;
		
	}
	
	
	
	
	
	
	













}
