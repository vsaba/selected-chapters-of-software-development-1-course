package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A singleton class which further implements the {@link ILocalizationProvider} interface
 * by extending the {@link AbstractLocalizationProvider} class
 * @author Vito Sabalic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	
	/**
	 * The current language
	 */
	private String language;
	/**
	 * The current language bundle. The language keys are stored here.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Part of the singleton class instance initializer
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Other part of the singleton class instance initializer
	 */
	private LocalizationProvider() {
		language = "en";
		setBundle();
	}
	
	/**
	 * Returns the only instance of this class
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String str) {
		return bundle.getString(str);
	}
	
	/**
	 * Sets the current language to the provided language. Also notifies all subscribed listeners
	 * @param language The provided language
	 */
	public void setLanguage(String language) {
		this.language = language;
		setBundle();
		fire();
	}
	
	
	/**
	 * Updates the current bundle. Called when a new language is set.
	 */
	private void setBundle() {
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
