package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents a model of a single document
 * @author Vito Sabalic
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Returns the text component of this model
	 * @return
	 */
	JTextArea getTextComponent();
	
	
	/**
	 * Returns the file path
	 * @return
	 */
	Path getFilePath();
	
	/**
	 * Sets the file path to the provided path
	 * @param path The provided path
	 */
	void setFilePath(Path path);
	
	/**
	 * Checks whether the document has been modified
	 * @return True if it has, false otherwise
	 */
	boolean isModified();
	
	/**
	 * Sets the modification status of the document to the provided value
	 * @param modified The provided value
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds the provided listener to this instance
	 * @param l The provided listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the provided listener from this instance
	 * @param l The provided listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);


}
