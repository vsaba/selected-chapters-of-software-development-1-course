package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node which stores the elements from the {@link Element} class
 * @author Vito Sabalic
 *
 */
public class Node {
	
	
	/**
	 * An internal collection which stores the child nodes of the current node
	 */
	ArrayIndexedCollection col;
	
	/**
	 * A simple constructor
	 */
	public Node() {
		col = new ArrayIndexedCollection();
	}

	/**
	 * Adds a child node to the {@link ArrayIndexedCollection} collection
	 * @param child the node to be added
	 */
	public void addChildNode(Node child) {
		col.add(child);
	}
	
	/**
	 * Returns the current number of children of this node stored in the collection
	 * @return the current number of children of this node stored in the collection
	 */
	public int numberOfChildren() {
		return col.size();
	}
	
	/**
	 * Fetches a child currently situated at the provided index
	 * @param index the index from which the child is retrieved
	 * @return returns the obtained child node
	 */
	public Node getChild(int index) {
		return (Node)col.get(index);
	}
}
