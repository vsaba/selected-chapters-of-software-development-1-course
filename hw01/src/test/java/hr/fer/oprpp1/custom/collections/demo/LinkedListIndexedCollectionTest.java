package hr.fer.oprpp1.custom.collections.demo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;


public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testBasicConstructor() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testComplexConstructor() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(col);
		
		assertArrayEquals(col.toArray(), list.toArray());
	}
	
	@Test
	public void testMethodAddAndMethodToArray() {

		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);

		Object[] expected = new Object[] { 1, 2, 3, 4, 5 };

		assertArrayEquals(expected, col.toArray());
		
		assertThrows(NullPointerException.class, () -> col.add(null));

	}
    
	
	@Test
	public void testMethodGet() {
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		
		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertEquals(3, col.get(2));
		
		
	}

	@Test
	public void testMethodClearAndToArray() {

		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.clear();
		
		Object[] obj = new Object[5];
		
		assertArrayEquals(obj, col.toArray());
		
	}
	
	@Test
	public void insertExceptionTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		
		assertThrows(NullPointerException.class, () -> col.insert(null, 1));
		
		
		
	}

	@Test
	public void testMethodInsertInTheMiddleAndDoublingOfArraySize() {
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.insert(99, 3);
		
		Object[] obj = new Object[] {1, 2, 3, 99, 4, 5};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodInsertInTheBeginningAndDoublingOfArraySize() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		
		col.insert(99, 0);
		
		Object[] obj = new Object[] {99, 1, 2, 3, 4};
		
		assertArrayEquals(obj, col.toArray());
		
	}

	@Test
	public void testMethodInsertInTheEnd() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		
		col.insert(99, 3);
		
		Object[] obj = new Object[] {1, 2, 3, 99, 4};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodIndexOf() {
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		
		assertEquals(2, col.indexOf(3));
		
		
	}

	@Test
	public void testMethodRemoveFirst() {

		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.remove(0);
		
		Object[] obj = new Object[] {2, 3, 4, 5};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodRemoveFromTheMiddle() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.remove(3);
		
		Object[] obj = new Object[] {1, 2, 3, 5};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodRemoveLastAndGetSizeMethod() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.remove(col.size() - 1);
		
		Object[] obj = new Object[] {1, 2, 3, 4};
		
		assertArrayEquals(obj, col.toArray());
	}
	

	@Test
	public void removeExeptionTest() {
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		
		assertThrows(NullPointerException.class, () -> col.remove(null));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
