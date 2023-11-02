package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * An action instance used of localization of swing components
 * @author Vito Sabalic
 *
 */
public abstract class LocalizableAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A simple constructor which initializes the name and subscribes the instance to the {@link FormLocalizationProvider} class
	 * @param key The name
	 * @param lp The instance of the {@link FormLocalizationProvider} class
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		super();
		
		putValue(NAME, lp.getString(key));
		
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
				
			}
		});
	}

}
