package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * A bridge class used for safe usage of Localization in a program
 * @author Vito Sabalic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	
	/**
	 * Whether there has been a listener registered to the {@link ILocalizationProvider} instance
	 */
	private boolean connected;
	
	/**
	 * The {@link ILocalizationProvider} instance
	 */
	private ILocalizationProvider provider;
	
	/**
	 * The currently selected language
	 */
	private String language;
	
	/**
	 * The listener which notifies when a change has been made
	 */
	private ILocalizationListener listener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			language = provider.getCurrentLanguage();
			fire();
		}
	};
	
	/**
	 * A simple constructor
	 * @param provider The provided {@link ILocalizationProvider} instance
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.connected = false;
		this.provider = provider;
		this.language = provider.getCurrentLanguage();
	}
	
	
	/**
	 * Connects to the provided instance
	 */
	public void connect() {
		
		if(connected) {
			return;
		}
		
		if(!(this.language.equalsIgnoreCase(provider.getCurrentLanguage()))) {
			fire();
		}
		
		connected = true;
		
		provider.addLocalizationListener(listener);
		return;
		
	}
	
	/**
	 * Disconnects from the provided instance
	 */
	public void disconnect() {
		
		if(!connected) {
			return;
		}
		
		provider.removeLocalizationListener(listener);
		connected = false;
		
		return;
	}
	
	
	
	@Override
	public String getString(String str) {
		String s = provider.getString(str);
		return s;
	}


	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
