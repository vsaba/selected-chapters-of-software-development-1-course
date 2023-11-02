package hr.fer.oprpp1.custom.collections;


/**
 * A collection class used to implement a simple array collection
 * 
 * @author Vito Sabalic
 *
 */
public class Collection {
	
	
	
	/**
	 * A default protected constructor
	 */
	protected Collection() {
		
	}
	
	
	
	/**
	 * the method checks whether the implemented array is empty
	 * @return if size of the array is greater than 0 return true, otherwise returns false
	 */
	public boolean isEmpty() {
		
		return size() <= 0;
	
	}
	
	
	
	/**
	 * @return return the size of the array
	 */
	public int size() {
		
		return 0;
		
	}
	
	/**
	 * Adds an element to the array
	 * @param value
	 */
	public void add(Object value) {
		return;
	}
	
	
	/**
	 * Checks whether the array contains the given value
	 * @param value value to be checked
	 * @return returns true if the array contains the value, otherwise returns false
	 */
	public boolean contains(Object value) {
		
		return false;
		
	}
	
	
	/**
	 * Removes the given value from the array
	 * @param value value to be removed
	 * @return returns true if successfully removed, otherwise returns false
	 */
	public boolean remove(Object value) {
		
		return false;
		
	}
	
	
	/**
	 * Allocates new array with size equal to the size of the array implemented in this collection,
	 * fills it with the content of this collection.
	 * This method never returns <code>null</code>
	 * @return returns the newly allocated array
	 */
	public Object[] toArray() {
		
		throw new UnsupportedOperationException();
		
	}
	
	
	
	/**
	 * Method calls the process from the processor given in the argument,
	 * and executes the process for each element in the current array
	 * @param processor the processor with the required process method
	 */
	public void forEach(Processor processor) {
		return;
	}

	
	/**
	 * Method adds all elements from the other collection to the current collection
	 * @param other the collection from which the elements are added
	 */
	public void addAll(Collection other) {
		
		class AddAllProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new AddAllProcessor());
		
		return;
		
	}
	
	
	/**
	 * Removes all elements from this collection
	 */
	void clear() {
		return;
	}
	
}
