package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * A class which is used as proxy for the {@link LocalizationProviderBridge} class
 * @author Vito Sabalic
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{
	
	/**
	 * Adds a {@link WindowListener} which disconnects the {@link LocalizationProviderBridge} from the {@link AbstractLocalizationProvider} class
	 * which in turns means a safe closure of the provided {@link JFrame}
	 * @param provider The {@link ILocalizationProvider} instance
	 * @param frame The {@link JFrame} to be closed
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		WindowListener l = new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		};
		
		
		frame.addWindowListener(l);
		
	}

}
