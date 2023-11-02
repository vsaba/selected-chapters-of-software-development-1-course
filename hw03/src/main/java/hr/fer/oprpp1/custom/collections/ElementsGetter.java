package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface which represents a simple element retriever.
 * To be used as a private class inside an already implemented collection
 * @author Vito Sabalic
 *
 */
public interface ElementsGetter<E> {
	
	
	
	/**
	 * Checks whether the collection has at least one more element
	 * @return returns true if it has at least one more element, otherwise false
	 */
	boolean hasNextElement();
	
	
	/**
	 * @return returns the next element from the collection
	 * @throws throws {@link NoSuchElementException} if the collection is empty when this method is called
	 */
	E getNextElement();
	
	
	/**
	 * Applies the process from the processor which is presented through the argument
	 * @param p the processor which processes the remaining elements inside a collection
	 */
	default void processRemaining(Processor<? super E> p) {
		
		while(hasNextElement()) {
			p.process(getNextElement());
		}
		
	}
		
}
