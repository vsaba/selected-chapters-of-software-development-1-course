package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A class which implements a {@link ListModel}. Used for calculating and storing prime numbers
 * @author Vito Sabalic
 *
 */
public class PrimListModel implements ListModel<Integer>{

	/**
	 * The current primary number
	 */
	private int primary;
	
	/**
	 * A list used for storage of primary numbers
	 */
	private List<Integer> primaryNumbers;
	
	/**
	 * List of listeners
	 */
	private List<ListDataListener> listeners;

	
	
	/**
	 * A simple constructor which initializes all the current values
	 */
	public PrimListModel() {
		this.primary = 1;
		this.primaryNumbers = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}
	
	/**
	 * Calculates the next primary number and informs all listeners of a change
	 */
	public void next() {
		
		while(!isPrimary(primary)) {
			primary++;
		}
		
		primaryNumbers.add(primary++);
		
		ListDataEvent event = new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED,
				this.primaryNumbers.size() - 1,
				this.primaryNumbers.size() - 1);
		
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
		
	}
	
	
	@Override
	public int getSize() {
		return this.primaryNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return this.primaryNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		this.listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		this.listeners.remove(l);
		
	}
	
	
	private boolean isPrimary(int number) {
		
		int brojac = 0;
		
		for(int i = 1; i <= number; i++) {
			if(number % i == 0) {
				brojac++;
			}
		}
		
		return brojac <= 2;
	}

}
