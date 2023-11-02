package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of {@link SingleDocumentModel} interface
 * @author Vito Sabalic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{
	
	/**
	 * The current file path of the document
	 */
	private Path filePath;
	/**
	 * The text component associated with the document
	 */
	private JTextArea textComponent;
	/**
	 * Flag that checks whether document has been modified
	 */
	private boolean isModified;
	/**
	 * The subscribed listeners
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * A constructor which assigns the variables and adds a new {@link DocumentListener} and {@link CaretListener} to the text component
	 * @param filePath The provided path
	 * @param textContent The text content to be added to the text component
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		this.isModified = false;
		this.textComponent = new JTextArea(textContent);
		this.listeners = new ArrayList<>();
		
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
								
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
				
			}
		});
		
		textComponent.addCaretListener(l -> {
			listeners.forEach(a -> a.documentModifyStatusUpdated(this));
		});
		
	}

	@Override
	public JTextArea getTextComponent() {
		
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null) {
			throw new IllegalArgumentException("File path cannot be null");
		}
		
		this.filePath = path;
		
		listeners.forEach(l -> l.documentFilePathUpdated(this));

	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DefaultSingleDocumentModel)) {
			return false;
		}
		
		
		SingleDocumentModel model = (DefaultSingleDocumentModel) obj;
		
		if(this.getFilePath().equals(model.getFilePath())) {
			return true;
		}
		
		
		return false;
	}

}
