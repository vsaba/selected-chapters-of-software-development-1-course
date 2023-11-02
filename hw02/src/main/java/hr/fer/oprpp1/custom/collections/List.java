package hr.fer.oprpp1.custom.collections;

/**
 * An interface which extends the interface collection and adds new methods to be implemented
 * @author Vito Sabalic
 *
 */
public interface List extends Collection{
	
	/**
	 * returns the object which is positioned on the given index
	 * @param index the index from which the object is returned
	 * @return the object on the provided index
	 */
	Object get(int index);
	
	/**
	 * Inserts a value into the given position.
	 * If the array size is not large enough it doubles it.
	 * @param value the value to be inserted
	 * @param position the position into which the value will be inserted
	 * @throws throws a {@link NullPointerException} if the value to be added is null
	 */
	void insert(Object value, int position);
	
	/**
	 * Searches for the index of the given value
	 * @param value the value whose index is returned
	 * @return returns the index if the value is found, otherwise returns -1
	 */
	int indexOf(Object value);
	
	/**
	 * Removes the value which is positioned at the given index
	 * @param index the index whose value will be removed
	 */
	void remove(int index);


}
