package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * An implementation of a linked list collection with all the basic and necessary methods to use a linked list.
 * Uses a private class to represent each node in the list.
 * @author Vito Sabalic
 *
 */
public class LinkedListIndexedCollection<E> implements List<E>{
	
	
	
	/**
	 * A class which represents a node inn a Linked List
	 * @author Vito Sabalic
	 *
	 */
	private static class ListNode<E>{
		
		/**
		 * Represents the previous node
		 */
		private ListNode<E> previous;
		
		/**
		 * Represents the next node
		 */
		private ListNode<E> next;
		
		/**
		 * Represents the value stored inside the node
		 */
		private E value;
	}
	
	/**
	 * Represents the current size of the collection
	 */
	private int size;
	
	/**
	 * Represents the first node of the collection
	 */
	private ListNode<E> first;
	
	/**
	 * Represents the last node of the collection
	 */
	private ListNode<E> last;
	
	/**
	 * the counter of modifications made to the collection
	 */
	private long modificationCount;
	
	
/**
 * An implementation of the interface {@link ElementsGetter}
 * @author Vito
 *
 */
private static class ElementsGetterFromLinkedListCollection<E> implements ElementsGetter<E>{
		
		/**
		 * the collection which uses this class
		 */
		private LinkedListIndexedCollection<E> arr;
		
		/**
		 * an internal tracker of the nodes of the collection
		 */
		private ListNode<E> nodeTracker;
		
		/**
		 * the saved count on modifications made to the collection
		 */
		private long savedModificationCount;
		
		/**
		 * A simple constructor which assigns the collection to this class
		 * @param col the collection which is assigned to this class
		 */
		public ElementsGetterFromLinkedListCollection(LinkedListIndexedCollection<E> col) {
			
			this.arr = col;
			this.nodeTracker = arr.first;
			this.savedModificationCount = arr.modificationCount;
			
		}
		
		@Override
		public boolean hasNextElement() {
			
			if(this.savedModificationCount < arr.modificationCount) {
				throw new ConcurrentModificationException("The collection has been modified");
			}
			
			boolean hasNextElement = true;
			
			if(nodeTracker == null) {
				hasNextElement = false;
			}
			
			
			return hasNextElement;
		}
		
		
		@Override
		public E getNextElement() {
			
			
			
			if(this.savedModificationCount < arr.modificationCount) {
				throw new ConcurrentModificationException("The collection has been modified");
			}
			
			E obj;
			
			if(!(hasNextElement())) {
				
				throw new NoSuchElementException("There is no element to be returned");
			}
			
			
			obj = nodeTracker.value;
			nodeTracker = nodeTracker.next;
			return obj;
			
			
		}
	}
	
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ElementsGetterFromLinkedListCollection<>(this);
	}
	
	
	/**
	 * A basic constructor which assigns a null value to all the pointers and 0 to the value variable
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * A constructor which uses a previous implementation of a collection and assigns each variable of the 
	 * aforementioned collection in this collection
	 * @param collection The collection whose values are imported into this class
	 */
	public LinkedListIndexedCollection(Collection<? extends E> collection) {
		
		this.modificationCount = 0;
		
		class AddToLinkedListProcessor<T extends E> implements Processor<T>{
			
			@Override
			public void process(T value) {
				add(value);
			}
		}
		
		collection.forEach(new AddToLinkedListProcessor<>());
	}
	
	

	/**
	 * Creates a new node and assigns the value from the parameter to the node
	 *@param The value to be added
	 *@throws throws a {@link NullPointerException} if a null value is to be added
	 */
	@Override
	public void add(E value) {
		
		if(value == null) {
			throw new NullPointerException("A null value cannot be added");
		}
		
		ListNode<E> newNode = new ListNode<>();
		
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
		modificationCount++;
	}
	
	/**
	 * Gets a value from a node based on its index.
	 * The index is taken care internally.
	 * @param index the index from which the value is taken 
	 * @return returns the taken value
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The given integer for the index is larger than the current size of the array");
		}
		
		ListNode<E> p = new ListNode<>();
		int i = 0;
		for(p = first; !(p == null) && i < index; p = p.next) {
			i++;
		}
		
		if(p == null) {
			throw new NullPointerException("There are no elements in the array, therefore a null element cannot be returned");
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
		this.size = 0;
		
		modificationCount++;
		
		return;
		
	}
	
	/**
	 * Creates and inserts a new node whose value is assigned from the given parameter.
	 * The entire list is shifted by one index and the aforementioned node is inserted.
	 * @param value the value to be added
	 * @param position the position into which the node is inserted
	 * @throws throws a {@link NullPointerException} if a null value is to be added.
	 */
	@Override
	public void insert(E value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("The given integer for the index is larger than the current size of the array");
		}
		
		if(value == null) {
			throw new NullPointerException("A null value cannot be added");
		}
		
		ListNode<E> p = new ListNode<>();
		int i = 0;
		for(p = first; !(p == null) && i < position; p = p.next) {
			i++;
		}
		
		ListNode<E> newNode = new ListNode<>();
		
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
		modificationCount++;
		
		return;
		
	}
	
	/**
	 * Returns the index of the node which has the value from the parameter.
	 * @param value the searched value
	 * @return returns the position of the node in the form of an index
	 */
	@Override
	public int indexOf(Object value) {
		
		ListNode<E> p = new ListNode<>();
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
	@Override
	public void remove(int index) {
		
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("The given index is larger than the size of the array");
		}
		
		ListNode<E> p = new ListNode<>();
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
		modificationCount++;
		
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
		
		modificationCount++;
		
		ListNode<E> p = new ListNode<>();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray() {
		
		if(size <= 0) {
			throw new NullPointerException("The array cannot contain null values");
		}
		
		E[] array = (E[]) new Object[size];
		
		ListNode<E> p = new ListNode<>();
		int index = 0;
		
		for(p = first; !(p == null); p = p.next) {
			
			array[index] = p.value;
			index++;
			
		}
		
		return Arrays.copyOf(array, size);
	}
	
	@Override
	public void forEach(Processor<? super E> processor) {
		
		ListNode<E> p = new ListNode<>();
		
		for(p = first; !(p == null); p = p.next) {
			processor.process(p.value);
		}
		
		return;
		
	}
	
	
	
	
	
	
	













}
