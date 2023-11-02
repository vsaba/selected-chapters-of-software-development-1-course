package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract implementation of the {@link ILocalizationProvider} interface
 * @author Vito Sabalic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * The subscribed listeners
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * A simple constructor
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Notifies all subscribed listeners
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
	

}
