package hr.fer.oprpp1.custom.collections.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class ArrayIndexedCollectionTest {

	@Test
	public void testConstructors() {
		
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertEquals(arr.toArray().length, 16);
		
		arr = new ArrayIndexedCollection(5);
		assertEquals(arr.toArray().length, 5);
		
		ArrayIndexedCollection arrayTest = new ArrayIndexedCollection(arr);
		assertEquals(arrayTest.toArray().length, 16);
		
		arrayTest = new ArrayIndexedCollection(arr, 10);
		assertEquals(arrayTest.toArray().length, 10);
		
		arr.add(1);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		arr.add(5);
		
		arrayTest = new ArrayIndexedCollection(arr, 5);
		Object[] expected = new Object[] { 1, 2, 3, 4, 5};
		
		assertArrayEquals(expected, arrayTest.toArray());
		
		
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
		
	}

	@Test
	public void testMethodAddAndMethodToArray() {

		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

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
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(6);

		
		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertEquals(3, col.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
		
	}

	@Test
	public void testMethodClearAndToArray() {

		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

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
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(1, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(1, col.size() +1));
		
		assertThrows(NullPointerException.class, () -> col.insert(null, 1));
		
		
		
	}

	@Test
	public void testMethodInsertInTheMiddleAndDoublingOfArraySize() {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.insert(99, 3);
		
		Object[] obj = new Object[] {1, 2, 3, 99, 4, 5, null, null, null, null};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodInsertInTheBeginningAndDoublingOfArraySize() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		
		col.insert(99, 0);
		
		Object[] obj = new Object[] {99, 1, 2, 3, 4, null, null, null};
		
		assertArrayEquals(obj, col.toArray());
		
	}

	@Test
	public void testMethodInsertInTheEnd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		
		col.insert(99, 3);
		
		Object[] obj = new Object[] {1, 2, 3, 99, 4,  null, null, null};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodIndexOf() {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		
		assertEquals(2, col.indexOf(3));
		
		
	}

	@Test
	public void testMethodRemoveFirst() {

		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.remove(0);
		
		Object[] obj = new Object[] {2, 3, 4, 5, null};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodRemoveFromTheMiddle() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.remove(3);
		
		Object[] obj = new Object[] {1, 2, 3, 5, null};
		
		assertArrayEquals(obj, col.toArray());
	}

	@Test
	public void testMethodRemoveLastAndGetSizeMethod() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.remove(col.size() - 1);
		
		Object[] obj = new Object[] {1, 2, 3, 4, null};
		
		assertArrayEquals(obj, col.toArray());
	}
	

	@Test
	public void removeExeptionTest() {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(col.size()));
		
		assertThrows(NullPointerException.class, () -> col.remove(null));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
