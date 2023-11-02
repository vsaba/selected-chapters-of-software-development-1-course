package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of a custom layout creator.
 * Used for the creation of a calculator layout
 * @author Vito Sabalic
 *
 */
public class CalcLayout implements LayoutManager2{
	
	
	/**
	 * The gap between components
	 */
	private int gap;
	
	
	/**
	 * A map of the added components and their respective {@link RCPosition}
	 */
	private Map<Component, RCPosition> components = new HashMap<>();
	
	
	/**
	 * A simple constructor which initializes the values
	 */
	public CalcLayout() {
		this.gap = 0;
	}
	
	/**
	 * A constructor which assigns the provided gap value to the current gap value
	 * @param gap The provided gap value
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
	}
	

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		
		return findMaximum("preferred", parent.getInsets(), parent);
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		
		return findMaximum("minimum", parent.getInsets(), parent);

	}
	
	
	/**
	 * Finds the maximum dimension for the provided parent {@link Container}
	 * @param type the type of maximum to be found
	 * @param ins the insets of the container
	 * @param parent The parent conatiner
	 * @return the maximum dimension
	 */
	private Dimension findMaximum(String type, Insets ins, Container parent) {
		double maxHeight = 0;
		double maxWidth = 0;
		
		maxHeight += ins.top + ins.bottom;
		maxWidth += ins.left + ins.right;
		
		boolean first = true;
		
		boolean firstComponent = false;
		
		for(Component comp: parent.getComponents()) {
			
			Dimension dim;
			if(type.equalsIgnoreCase("Minimum")) {
				dim = comp.getMinimumSize();
			}
			else if(type.equalsIgnoreCase("Preferred")) {
				dim = comp.getPreferredSize();
			}
			else if(type.equalsIgnoreCase("Maximum")) {
				dim = comp.getMaximumSize();
			}
			else {
				throw new IllegalArgumentException("Incorrect type paramater");
			}
			
			RCPosition pos = this.components.get(comp);
			
			if(pos.equals(RCPosition.parse("1,1"))) {
				firstComponent = true;
			}
			
			if(first) {
				maxHeight = dim.getHeight();
				maxWidth = dim.getWidth();
				first = false;
				continue;
			}
			
			if(dim.getHeight() > maxHeight) {
				maxHeight = dim.getHeight();
			}
			
			if(dim.getWidth() > maxWidth) {
				maxWidth = dim.getWidth();
			}
			
		}
		
		
		Dimension dim;
		
		if(firstComponent) {
			int width = (int)(maxWidth - 4 * gap )/ 5;
			
			dim = new Dimension((int) maxWidth + 2 * gap + 2 * width, (int)maxHeight * 5 + 4 * gap);
		}
		else {
			dim = new Dimension((int) maxWidth * 7 + 6 * gap, (int)maxHeight * 5 + 4 * gap);
		}
		
		return dim;
		
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		
		return findMaximum("maximum", target.getInsets(), target);
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
		
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		if(comp == null) {
			throw new NullPointerException();
		}
		
		RCPosition position; 
		
		if(constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		}
		else if(constraints instanceof String) {
			position = RCPosition.parse((String) constraints);
		}
		else {
			throw new IllegalArgumentException("The provided arguments are not valid");
		}
		
		if(position.getX() == 1) {
			if(position.getY() > 1 && position.getY() < 6 ) {
				throw new CalcLayoutException();
			}
		}
		
		if(position.getX() < 1 || position.getX() > 5) {
			throw new CalcLayoutException();
		}
		
		if(position.getY() < 1 || position.getY() > 7) {
			throw new CalcLayoutException();
		}
		
		if(components.containsKey(comp) || components.containsValue(position)) {
			throw new CalcLayoutException();
		}
		
		
		components.put(comp, position);
		
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		
		if(comp == null) {
			throw new NullPointerException();
		}
		
		components.remove(comp);
		
		return;
		
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {

		return 0;
	}
	
	@Override
	public float getLayoutAlignmentY(Container target) {

		return 0;
	}
	
	@Override
	public void layoutContainer(Container parent) {
		
		Insets ins = parent.getInsets();
		
		int compWidth = (parent.getWidth() - ins.left - ins.right - 6 * this.gap) / 7;
		int compHeight = (parent.getHeight() - ins.top - ins.bottom - 4 * gap) / 5;
		int y = 0;
		int x = 0;
		
		List<Integer> xDistribution = uniformlyDistribute(parent.getWidth(), 7);
		List<Integer> yDistribution = uniformlyDistribute(parent.getHeight(), 5);
		
		y += ins.top;
		x += ins.left;
		
		for(Component comp: components.keySet()) {
			
			RCPosition pos = components.get(comp);
			
			if(pos.getX() == 1 && pos.getY() == 1) {
				comp.setBounds(ins.left, ins.top, compWidth*5 + 4*gap, compHeight);
				continue;
			}
			
//			int width = compWidth + xDistribution.get(pos.getY() - 1);
//			int height = compHeight + yDistribution.get(pos.getX() - 1);
			
			if(pos.getY() == 1) {
				x = ins.left;
			}
			else {
				x = ins.left + (pos.getY() - 1) * (compWidth) + (pos.getY() - 1) * gap;
			}
			
			if(pos.getX() == 1) {
				y = ins.top;
			}
			else {
				y = ins.top + (pos.getX() - 1) * compHeight + (pos.getX() - 1) * gap;
			}
			
			
			comp.setBounds(x + xDistribution.get(pos.getY() - 1), y + yDistribution.get(pos.getX() - 1), compWidth, compHeight);
			
			
		}
		
		
	}

	@Override
	public void invalidateLayout(Container target) {
		//ako ima neke spremljene vrijednosti onda bi trebalo to maknuti
		
	}
	
	/**
	 * Uniformly distributes the components
	 * @param size the size to uniformly distribute
	 * @param n how many distributions
	 * @return returns a binary list which contains distributions
	 */
	private List<Integer> uniformlyDistribute(int size, int n){
		int ostatak = size % n;
		List<Integer> distribucija = new ArrayList<>();
		
		int ostatakPom = ostatak;
		
		for(int i = 0; i < n; i++) {
			distribucija.add(0);
		}
		
		for(int i = 0; i < n && ostatakPom > 0; i++) {
			if(ostatak > n / 2) {
				//prvo popunjujemo svaki na parnoj poziciji
				if(i % 2 == 0) {
					distribucija.set(i, 1);
					ostatakPom--;
				}
			}
			else {
				//inace prvo popunjujemo svaki na neparnoj poziciji
				if(i % 2 != 0) {
					distribucija.set(i, 1);
					ostatakPom--;
				}
			}
		}
		
		ostatak = ostatakPom;
		
		int i = 0;
		while(ostatak > 0) {
			if(distribucija.get(i) != 1) {
				distribucija.set(i, 1);
				ostatak--;
			}
			i++;
		}
		
		
		return distribucija;
		
	}

}
