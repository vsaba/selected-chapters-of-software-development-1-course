package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface which enables language localization
 * @author Vito Sabalic
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds a listener localization listener
	 * @param listener The listener to be added
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes the localization listener
	 * @param listener The listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Returns the appropriate term in the currently selected language based on the provided key
	 * @param str The provided key
	 * @return Returns the term in the currently selected language
	 */
	String getString(String str);
	
	/**
	 * A getter for the current language
	 * @return Returns the currently selected language
	 */
	String getCurrentLanguage();

}
