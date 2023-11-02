package hr.fer.oprpp1.custom.collections;


/**
 * A class which implements a collection using an array
 * @author Vito Sabalic
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	private int size;
	private Object[] elements;
	
	
	/**
	 * A constructor which delegates the default initial capacity to more complex constructor
	 * to a more complex constructor
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	
	/**
	 * A constructor which creates a new instance of an {@link ArrayIndexedCollection}
	 * @param initalCapacity the initial capacity of the array
	 */
	public ArrayIndexedCollection(int initalCapacity) {
		
		if(initalCapacity < 1) {
			throw new IllegalArgumentException("Inital capacity cannot be less than 1");
		}
		
		this.size = 0;
		this.elements = new Object[initalCapacity];
	}
	
	
	
	/**
	 * A more complex constructor which allocates the size of the array to 16 
	 * if the given collection is smaller than the aforementioned size,
	 * otherwise the size of the array is the collection size.
	 * Copies all elements from the collection to the array
	 * @param collection the given collection
	 * @throws throws a {@link NullPointerException} if the given collection is null
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}
	/**
	 * A more complex constructor which allocates the size of the array to the initial capacity 
	 * if the given collection is smaller than the aforementioned size,
	 * otherwise the size of the array is the collection size.
	 * Copies all elements from the collection to the array
	 * @param collection the given collection
	 * @param initalCapacity the given initial capacity
	 * @throws throws a {@link NullPointerException} if the given collection is null
	 */
	public ArrayIndexedCollection(Collection collection, int initalCapacity) {
		
		if(initalCapacity < 1) {
			throw new IllegalArgumentException("Inital capacity cannot be less than 1");
		}
		
		
		if(collection == null) {
			throw new NullPointerException("The given collection is null");
		}
		
		if(collection.size() <= initalCapacity) {
			this.elements = new Object[initalCapacity];
		}
		else {
			this.elements = new Object[collection.size()];
		}
		
		this.size = 0;
		
		this.addAll(collection);
		
		this.size = collection.size();
	
	}
	
	
	/**
	 * Adds a value to the array.
	 * If the array size is not large enough it doubles it.
	 * @throws throws {@link NullPointerException} if the value to be added is null
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("A null value cannot be added");
		}
		
		if(size == elements.length) {
			Object[] tmpElements = elements;
			elements = new Object[tmpElements.length * 2];
			for(int i = 0; i < tmpElements.length; i++) {
				elements[i] = tmpElements[i];
			}
		}
		
		elements[size++] = value;
		
		return;
	}
	
	
	/**
	 * @param index the index from which the element is returned
	 * @return returns the value from the given index
	 * @throws throws an {@link IndexOutOfBoundsException} if the given index is smaller than 0 or larger than the size
	 * @throws throws a {@link NullPointerException} if the array is empty and a null element is returned
	 */
	public Object get(int index) {
		
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The given integer for the index is larger than the current size of the array");
		}
		
		if(elements[index] == null) {
			throw new NullPointerException("There are no elements in the array, therefore a null element cannot be returned");
		}
		return elements[index];
	}
	
	
	
	/**
	 * Clears the array
	 */
	@Override
	public void clear() {
		
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
		size = 0;
		
		return;
	}
	
	
	/**
	 * Inserts a value into the given position.
	 * If the array size is not large enough it doubles it.
	 * @param value the value to be inserted
	 * @param position the position into which the value will be inserted
	 * @throws throws a {@link NullPointerException} if the value to be added is null
	 */
	public void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("The requested position is out of bounds");
		}
		
		if(value == null) {
			throw new NullPointerException("A null value cannot be added");
		}
		
		if(size == elements.length) {
			Object[] tmpElements = elements;
			elements = new Object[tmpElements.length * 2];
			for(int i = 0; i < tmpElements.length; i++) {
				elements[i] = tmpElements[i];
			}
		}
		
		for (int i = size-1; i >= position; i--) {                
		    elements[i+1] = elements[i];
		}
		
		elements[position] = value;
		size++;
	}
	
	
	
	/**
	 * Searches for the index of the given value
	 * @param value the value whose index is returned
	 * @return returns the index if the value is found, otherwise returns -1
	 */
	public int indexOf(Object value) {
		
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	
	/**
	 * Removes the value which is positioned at the given index
	 * @param index the index whose value will be removed
	 */
	public void remove(int index) {
		
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("The given index is larger than the current size of the array");
		}
		
		for (int i = index; i < size - 1; i++) {                
		    elements[i] = elements[i + 1];
		}
		
		elements[size - 1] = null;
		size--;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	
	@Override
	public boolean remove(Object value) {
		
		if(value == null) {
			throw new NullPointerException("The given value cannot be null");
		}
		
		
		int index = this.indexOf(value);
		
		if(index >= 0) {
			this.remove(index);
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.elements.length];
		newArray = this.elements;
		
		for(int i = 0; i < this.size; i++) {
			newArray[i] = this.elements[i];
		}
		
		return newArray;
	}
	
	@Override
	public void forEach(Processor processor) {
		
		for(int i = 0; i < this.size; i++) {
			processor.process(elements[i]);
		}
		
		return;
	}
	
}
