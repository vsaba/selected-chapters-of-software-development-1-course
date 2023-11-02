package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of a Map using the hash storing method
 * @author Vito Sabalic
 * 
 * @param <K> The key value
 * @param <V> The value assigned to the key
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	
	/**
	 * The table for the hash storing
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * The true size of the Map
	 */
	private int size;
	
	/**
	 * Tracks the number of modifications made to the current instance of the map
	 */
	private int modificationCount;
		
	
	
	/**
	 * An implementation of the {@link Iterator} interface
	 * @author Vito Sabalic
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>>{
		
		
		/**
		 * Represents the current entry at which the iterator is positioned
		 */
		private TableEntry<K,V> current;
		
		/**
		 * The current index of the table at which the iterator is positioned
		 */
		private int currentIndex;
		
		/**
		 * A boolean variable which tracks whether the iterator has made changes to the Map implementation.
		 * True if a change is made, false otherwise
		 */
		private boolean changed;
		
		/**
		 * The number of modifications made to the current instance at the time of the creation of the {@link IteratorImpl}
		 */
		private int savedModificationCount;
		
		/**
		 * A helper variable which tracks the remaining number of elements to be iterated through
		 */
		private int currentSize;
		
		/**
		 * A simple constructor which assigns new values to the variables
		 * @param size The size of the Map implementation at the time of the {@link IteratorImpl} creation
		 * @param modificationCount The number of modifications made to the Map implementation at the time of the {@link IteratorImpl} creation
		 */
		public IteratorImpl(int size, int modificationCount) {
			this.current = null;
			this.currentIndex = 0;
			this.changed = false;
			this.savedModificationCount = modificationCount;
			this.currentSize = size;
		}
		
		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			 return currentSize > 0;
		}

		@Override
		public TableEntry<K, V> next() {
			
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if(!(hasNext())) {
				throw new NoSuchElementException();
			}
			
			if(current == null) {
				for(; currentIndex< table.length; currentIndex++) {
					if(table[currentIndex] != null) {
						current = table[currentIndex];
						currentSize--;
						break;
					}
				}
			}
			else if(current.next == null) {
				currentIndex++;
				for(; currentIndex< table.length; currentIndex++) {
					if(table[currentIndex] != null) {
						current = table[currentIndex];
						currentSize--;
						break;
					}
				}
			}
			else {
				current = current.next;
				currentSize--;
			}
			
			changed = false;
			
			return current;
			
		}
		
		@Override
		public void remove() {
			
			if(changed == true) {
				throw new IllegalStateException();
			}
			
			SimpleHashtable.this.remove(current.getKey());
			savedModificationCount++;
			changed = true;
			return;
			
		}
	}
	
	
	/**
	 * A private class which represents a slot of the table of the Map implementation.
	 * It has a key and a values assigned to the key
	 * @author Vito Sabalic
	 *
	 * @param <K> The key value
	 * @param <V> The value assigned to the key
	 */
	public static class TableEntry<K, V>{
		
		/**
		 * The key value
		 */
		private K key;
		
		/**
		 * The value assigned to the key
		 */
		private V value;
		
		/**
		 * A pointer to the next instance of the {@link TableEntry}
		 */
		private TableEntry<K, V> next;
		
		/**
		 * A simple constructor which assigns provided values to the variables
		 * @param key The key value 
		 * @param value The value assigned to the key
		 * @param next A pointer to the next instance of the {@link TableEntry}
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * A getter for the value
		 * @return Returns the value of the entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * A setter for the value
		 * @param value The value to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * A getter for the key
		 * @return Returns the key of the entry
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public String toString() {
			return String.valueOf(this.key) + "=" + String.valueOf(this.value);
		}
			
	}
	
	
	/**
	 * A simple constructor which creates a new Array for the table and assigns begging values to the variables
	 */
	public SimpleHashtable() {
		this(16);
		
	}
	
	/**
	 * A constructor which creates a new Array for the table at the provided capacity.
	 * The capacity of the array is a magnitude of the power of the number 2
	 * @param capacity The provided capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be smaller than 1");
		}
		
		capacity = (int)Math.pow(2, Math.ceil(Math.log10(capacity)/Math.log10(2)));
		
		this.table = (TableEntry<K,V>[]) new TableEntry[capacity];
		this.size = 0;
		this.modificationCount = 0;
		
	}

	/**
	 * Creates a new {@link TableEntry} and inserts it into the array by calculating its hash value.
	 * If a table entry with the provided key already exists, replaces the old value to the new value and returns the old value.
	 * Otherwise, creates a new instance, adds it and returns null
	 * @param key The key value
	 * @param value The value assigned to the key
	 * @return Returns the old value if a key already exists, otherwise returns null
	 */
	public V put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Key cannot be null");
		}
		
		if(containsKey(key)) {
			return replaceValue(key, value);
		}
		
		checkSize();
		
		TableEntry<K, V> newEntry = new TableEntry<K, V>(key, value, null);
		
		
		int index = Math.abs(key.hashCode()%table.length);
		if(table[index] == null) {
			table[index] = newEntry;
			size++;
			modificationCount++;
			return null;
		}
		
		TableEntry<K, V> p = table[index];
		
		for(; p != null; p = p.next) {
			if(p.next == null) {
				p.next = newEntry;
				break;
			}
		}
		
		size++;
		modificationCount++;
		return null;
	}
	
	/**
	 * Returns the table {@link TableEntry} which contains the provided key
	 * @param key The key value
	 * @return The value of the {@link TableEntry} which contains the provided key exists, otherwise returns null
	 */
	public V get(Object key) {
		TableEntry<K, V> p = table[Math.abs(key.hashCode()%table.length)];
		
		for(; p != null; p = p.next) {
			if(p.getKey().equals(key)) {
				return p.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * @return Returns the number of {@link TableEntry} instances in this implementation
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks whether the implementation has a {@link TableEntry} with the provided key
	 * @param key The key value 
	 * @return True if the implementation has a {@link TableEntry} with the provided key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			throw new NullPointerException("Key cannot be null");
		}
		int index = Math.abs(key.hashCode()%table.length);
		TableEntry<K, V> p = table[index];
		for(; p != null; p = p.next) {
			if(p.getKey().equals(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks whether the implementation has a {@link TableEntry} with the provided value
	 * @param value The value assigned to a {@link TableEntry}
	 * @return True if the implementation has a {@link TableEntry} with the provided value, false otherwise
	 */
	public boolean containsValue(Object value) {
		if(value == null) {
			return true;
		}
		
		TableEntry<K, V>[] pom = this.toArray();
		for(int i = 0; i < pom.length; i++) {
			if(pom[i].getValue().equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes a {@link TableEntry} which contains the provided key, if it exists
	 * @param key The key value
	 * @return If it exists, returns the value of the {@link TableEntry} which contains the provided key.
	 * Otherwise, returns null
	 */
	public V remove(Object key) {
		if(key == null || !(containsKey(key))) {
			return null;
		}
		
		V toReturn;
		TableEntry<K, V> p = table[Math.abs(key.hashCode()%table.length)];
		
		if(p.getKey().equals(key)) {
			toReturn = p.getValue();
			table[Math.abs(key.hashCode()%table.length)] = p.next;
			size--;
			modificationCount++;
			return toReturn;
		}
		for(; p != null; p = p.next) {
			
			if(p.next != null) {
				if(p.next.getKey().equals(key)) {
					if(p.next.next == null) {
						toReturn = p.next.getValue();
						p.next = null;
						size--;
						modificationCount++;
						return toReturn;
					}
					else {
						toReturn = p.next.getValue();
						p.next = p.next.next;
						size--;
						modificationCount++;
						return toReturn;
					}
				}
			}
		}
		
		return null;
		
	}
	
	/**
	 * Checks whether the implementation has no entries
	 * @return True if there are no instances, false otherwise
	 */
	public boolean isEmpty() {
		return this.size <= 0;
	}
	
	/**
	 * @return Returns a new {@link TableEntry} array which contains all entries of this collection
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray(){
		TableEntry<K, V>[] newArray = (TableEntry<K,V>[]) new TableEntry[size];
		
		int index = 0;
		for(int i = 0; i < table.length; i++) {
			
			TableEntry<K, V> pom = table[i];
			
			for(; pom != null; pom = pom.next) {
				newArray[index++] = pom;
			}
		}
		
		return newArray;
	}
	
	@Override
	public String toString() {
		String s = "[";
		TableEntry<K, V>[] pom = this.toArray();
		
		for(int i = 0; i < pom.length; i++) {
			if(i + 1 == pom.length) {
				s += pom[i].toString() + "]";
				break;
			}
			
			s += pom[i].toString() + ", ";
		}
		
		return s;
	}
	
	/**
	 * Clears all the entries from the implementation
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		
		return;
	}
	
	/**
	 * Replaces the old value of the {@link TableEntry} which contains the provided key with the newly provided value
	 * @param key The key value
	 * @param value The new value assigned to the key
	 * @return Returns the old value if a {@link TableEntry} existed, null otherwise
	 */
	private V replaceValue(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Key cannot be null");
		}
		
		TableEntry<K,V> p = table[Math.abs(key.hashCode()%table.length)];
		
		for(; p != null; p = p.next) {
			if(p.getKey().equals(key)) {
				V toReturn = p.getValue();
				p.setValue(value);
				return toReturn;
			}
		}
		
		return null;
	}
	
	/**
	 * Checks whether the number of entries exceeds 75% of the possible capacity. If the number exceeds, doubles the capacity of the table
	 */
	@SuppressWarnings("unchecked")
	private void checkSize() {
		
		if(!(size/table.length > 0.75)) {
			return;
		}
		
		TableEntry<K,V>[] pom = this.toArray();
		int capacity = table.length;
		table = (TableEntry<K,V>[]) new TableEntry[capacity * 2];
		
		int oldSize = this.size;
		
		for(int i = 0; i < pom.length; i++) {
			this.put(pom[i].getKey(), pom[i].getValue());
		}
		
		this.size = oldSize;
		modificationCount++;
		
		return;
		
	}

	/**
	 *Returns a new instance of an iterator of the type {@link TableEntry}
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl(size, modificationCount);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
