package hr.fer.oprpp1.custom.collections;

/**
 * A class which represents a simple implementation of a map in Java
 * @author Vito Sabalic
 *
 * @param <K> The key 
 * @param <V> The value
 */
public class Dictionary<K, V> {


	/**
	 * A private class which represents a pair of a key and a value
	 * @author Vito Sabalic
	 *
	 * @param <K> The key
	 * @param <V> The value
	 */
	private static class Pair<K, V>{
		/**
		 * The key of the pair
		 */
		K key;
		
		
		/**
		 * The value of the pair
		 */
		V value;
		
		/**
		 * A simple constructor which assigns the key and the value to the respective atributes of this class
		 * @param key The key value
		 * @param value The value of the pair
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}		
	}
	
	/**
	 * The adapter collection which stores instances of the {@link Pair} class
	 */
	ArrayIndexedCollection<Pair<K,V>> col;
	
	
	/**
	 * A simple constructor which creates a new instance of the adapter collection
	 */
	public Dictionary() {
		this.col = new ArrayIndexedCollection<Pair<K,V>>();
	}
	
	
	/**
	 * A constructor which creates a new instance of the adapter collection and creates
	 * a new instance of the {@link Pair} class with the given parameters
	 * @param key The key value
	 * @param value The value of the pair
	 */
	public Dictionary(K key, V value) {
		
		Pair<K, V> pair = new Pair<>(key, value);
		this.col = new ArrayIndexedCollection<Pair<K,V>>();
		
		col.add(pair);
	}
	
	
	/**
	 * Checks whether the Dictionary is empty
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * @return Returns the size of the dictionary
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Clears the dictionary collection
	 */
	public void clear() {
		col.clear();
	}
	
	
	/**
	 * Puts a new key into the dictionary collection.
	 * If a pair which contains the provided key already exists, replaces the old value with the provided value.
	 * Otherwise, creates a new pair and inserts it into the collection
	 * @param key The key value
	 * @param value The value of the pair
	 * @return Returns the old value of a pair if the key already exists, otherwise returns null
	 */
	public V put(K key, V value) {
		if(key == null) {
			throw new IllegalArgumentException("The key cannot be null");
		}
		
		if(isEmpty()) {
			col.add(new Pair<>(key, value));
			return null;
		}
		
		
		for(int i = 0; i < col.size(); i++) {
			if(col.get(i).key == key) {
				V oldValue = col.get(i).value;
				col.get(i).value = value;
				return oldValue;
			}
		}
		
		col.add(new Pair<>(key, value));
		return null;
	}
	

	/**
	 * Finds and returns the value of the pair with the provided key
	 * @param key The key value
	 * @return Returns the value of the pair with the provided key if it exists, otherwise returns null
	 */
	public V get(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("The key cannot be null");
		}
		
		
		
		for(int i = 0; i < col.size(); i++) {
			if(col.get(i).key == key) {
				return col.get(i).value;
			}
		}	
		
		return null;
	}
	
	
	/**
	 * Removes the pair with the provided key from the collection, if the pair exists
	 * @param key The key value
	 * @return Returns the value of the pair with the provided key if the pair exists, otherwise returns null
	 */
	public V remove(K key) {
		if(key == null) {
			throw new IllegalArgumentException("The key cannot be null");
		}
		
		
		for(int i = 0; i < col.size(); i++) {
			if(col.get(i).key == key) {
				V oldValue = col.get(i).value;
				col.remove(i);
				return oldValue;
			}
		}
		
		return null;
		
	}
}
