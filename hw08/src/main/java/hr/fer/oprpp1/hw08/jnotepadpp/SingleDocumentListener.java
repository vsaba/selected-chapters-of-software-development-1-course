package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JTextArea;

/**
 * A listener for the {@link SingleDocumentModel} implementation
 * @author Vito Sabalic
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Called when a modification occurs to the current {@link JTextArea} instance
	 * @param model The model on whom the change has been made
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Called when the path has been updated for the current {@link SingleDocumentModel} implementation
	 * @param model The model on whom the change has been made
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
