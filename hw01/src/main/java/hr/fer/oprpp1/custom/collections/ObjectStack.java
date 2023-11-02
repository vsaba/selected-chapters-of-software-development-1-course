package hr.fer.oprpp1.custom.collections;

/**
 * An adaptor class which uses the {@link ArrayIndexedCollection} implementation to represent a stack 
 * @author Vito Sabalic
 *
 */
public class ObjectStack {

	
	private ArrayIndexedCollection array;
	
	
	/**
	 * A simple constructor which creates a new instance of this class
	 */
	public ObjectStack(){
		array = new ArrayIndexedCollection();
	}
	
	/**
	 * Determines whether the stack is empty
	 * @return returns true if it is empty, else otherwise
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	
	/**
	 * Determines the size of the stack
	 * @return returns the size of the stack
	 */
	public int size() {
		return array.size();
	}
	
	
	
	/**
	 * Adds a new object to the stack
	 * @param value the object to be added
	 */
	public void push(Object value) {
		array.add(value);
		
		return;
	}
	
	/**
	 * Removes an object from the top of the stack
	 * @return returns the removed object
	 */
	public Object pop() {
		
		if(this.size() == 0) {
			throw new EmptyStackException("The stack is empty and therefore cannot pop an object");
		}
		
		int size = array.size();
		
		Object value = array.get(size - 1);
		array.remove(size - 1);
		
		return value;
	}
	
	
	/**
	 * @return returns the object currently on the top of the stack
	 */
	public Object peek() {
		
		return array.get(array.size() - 1);
	}
	
	/**
	 * Clears the entire stack
	 */
	public void clear() {
		
		array.clear();
		
		return;
	}

}
