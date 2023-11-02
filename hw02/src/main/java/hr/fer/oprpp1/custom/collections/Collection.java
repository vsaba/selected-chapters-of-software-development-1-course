package hr.fer.oprpp1.custom.collections;


/**
 * A collection interface used to implement a simple array collection
 * 
 * @author Vito Sabalic
 *
 */
public interface Collection {	
	
	
	/**
	 * the method checks whether the implemented array is empty
	 * @return if size of the array is greater than 0 return true, otherwise returns false
	 */
	default boolean isEmpty() {
		
		return size() <= 0;
	
	}
	
	
	
	/**
	 * @return return the size of the array
	 */
	int size();
	
	/**
	 * Adds an element to the array
	 * @param value
	 */
	void add(Object value);
	
	
	/**
	 * Checks whether the array contains the given value
	 * @param value value to be checked
	 * @return returns true if the array contains the value, otherwise returns false
	 */
	public boolean contains(Object value);
	
	
	/**
	 * Removes the given value from the array
	 * @param value value to be removed
	 * @return returns true if successfully removed, otherwise returns false
	 */
	public boolean remove(Object value);
	
	
	/**
	 * Allocates new array with size equal to the size of the array implemented in this collection,
	 * fills it with the content of this collection.
	 * This method never returns <code>null</code>
	 * @return returns the newly allocated array
	 */
	public Object[] toArray();
	
	
	
	/**
	 * Method calls the process from the processor given in the argument,
	 * and executes the process for each element in the current array
	 * @param processor the processor with the required process method
	 */
	default void forEach(Processor processor) {
		
		ElementsGetter getter = this.createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
		
		return;
	}

	
	/**
	 * Method adds all elements from the other collection to the current collection
	 * @param other the collection from which the elements are added
	 */
	default void addAll(Collection other) {
		
		class AddAllProcessor implements Processor{
			
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
	void clear();
	
	
	/**
	 * Creates a new instance of the implementation of the ElementsGetter interface
	 * @return returns the new instance
	 */
	ElementsGetter createElementsGetter();
	
	
	
	
	/**
	 * Performs the provided test on the every element from the provided collection.
	 * If the object passes the test it is added to the current collection
	 * @param col the collection whose objects are tested
	 * @param tester the test which is performed on the objects
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		
		ElementsGetter getter = col.createElementsGetter();
		
		Object element;
		
		
		while(getter.hasNextElement()) {
			element = getter.getNextElement();
			if(tester.test(element)) {
				this.add(element);
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
