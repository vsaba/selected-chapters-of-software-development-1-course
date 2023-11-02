package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTabbedPane;

/**
 * Represents a model capable of holding zero, one or multiple references
 * to {@link SingleDocumentModel}
 * @author Vito Sabalic
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	
	/**
	 * Creates a new document, adds a new instance of {@link SingleDocumentModel} interface
	 * @return Returns the added model
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Getter for the current model
	 * @return Returns the currently active model based on the {@link JTabbedPane} class
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Finds the document based on the provided path, and creates a new instance of {@link SingleDocumentModel} interface
	 * with the provided path and read text
	 * @param path The provided path
	 * @return The added model
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the provided {@link SingleDocumentModel}.
	 * If the provided path is null, saves the document to its current path.
	 * Otherwise, saves the document to the provided path and sets that path as its new path
	 * @param model The model to be saved
	 * @param newPath The path at which the model will be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Removes the provided document from the collection of documents and removes the tab of the {@link JTabbedPane} class
	 * which held this document open
	 * @param model The model whose tab should close
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds a document listener
	 * @param l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes a document listener
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Getter for the number of documents
	 * @return
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns the currently active document
	 * @param index
	 * @return
	 */
	SingleDocumentModel getDocument(int index);

}
