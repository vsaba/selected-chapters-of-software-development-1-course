package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * An implementation of the {@link MultipleDocumentModel} interface and an 
 * instance of the {@link JTabbedPane} class
 * @author Vito Sabalic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The currently active document
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * Index of the current document
	 */
	private int currentDocument;
	
	/**
	 * The subscribed listeners
	 */
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * The saved file image icon
	 */
	private ImageIcon savedIcon;
	
	/**
	 * The not saved file image icon
	 */
	private ImageIcon notSavedIcon;
	
	
	/**
	 * A constructor which initializes the variables, loads the icons and adds a change listener to the {@link JTabbedPane}
	 */
	public DefaultMultipleDocumentModel() {
		this.documents = new ArrayList<>();
		this.currentDocument = -1;
		this.listeners = new ArrayList<>();
		this.savedIcon = loadIcon("greenSaveIcon.png");
		this.notSavedIcon = loadIcon("redSaveIcon.png");
		
		
		this.addChangeListener(l -> {
			currentDocument = this.getSelectedIndex();
			
			
			if(currentDocument == -1) {
				return;
			}
			
			listeners.forEach(a -> a.currentDocumentChanged(null, documents.get(currentDocument)));
		});
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		
		DefaultSingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
		model.addSingleDocumentListener(listener);
		
		documents.add(model);
		this.addTab("unnamed", savedIcon, new JScrollPane(model.getTextComponent()), "unnamed");
		this.setSelectedIndex(documents.size() - 1);
		
		
		listeners.forEach(l -> l.documentAdded(model));
		
		
		return getCurrentDocument();
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		
		if(path == null) {
			JOptionPane.showMessageDialog(this, "The file path is not valid");
			return null;
		}
		
		
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(this, "No file at the provided path exists");
		}
		
		String text = new String();
		
		try {
			text = Files.readString(path, StandardCharsets.UTF_8);
		}
		catch(IOException exc) {
			JOptionPane.showMessageDialog(this, "An error has occurred while reading the document");
		}
		
		SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		
		if(documents.contains(model)) {
			currentDocument = this.documents.indexOf(model);
			this.setSelectedIndex(currentDocument);
			return getCurrentDocument();
		}
		else {
			model.addSingleDocumentListener(listener);
			documents.add(model);
			this.addTab(path.getFileName().toString(), savedIcon, new JScrollPane(model.getTextComponent()),
					path.toAbsolutePath().toString());
			this.setSelectedIndex(documents.size() - 1);
		}
		
		listeners.forEach(l -> l.documentAdded(model));
		
		return getCurrentDocument();
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		if(newPath == null) {
			writeToDisk(model, model.getFilePath());
			model.setModified(false);
			this.setIconAt(currentDocument, savedIcon);
			return;
		}
		
		
		
		writeToDisk(model, newPath);
		
		model.setFilePath(newPath);
		model.setModified(false);
		this.setIconAt(currentDocument, savedIcon);
		this.setToolTipTextAt(currentDocument, newPath.toAbsolutePath().toString());
		
		listeners.forEach(l -> l.currentDocumentChanged(null, model));
		
		return;	
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		
		this.documents.remove(currentDocument);
		
		this.remove(currentDocument);
		
		listeners.forEach(l -> l.documentRemoved(model));
		
		return;
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
		
	}
	

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return documents.get(currentDocument);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		
		return documents.iterator();
	}
	
	
	/**
	 * A listener to be added to a {@link SingleDocumentModel} instance
	 */
	private final SingleDocumentListener listener = new SingleDocumentListener() {
		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if(model.isModified()) {
				setIconAt(currentDocument, notSavedIcon);
			}			
			
			listeners.forEach(l -> l.currentDocumentChanged(null, getCurrentDocument()));
			
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			
			DefaultMultipleDocumentModel.this.setTitleAt(currentDocument, model.getFilePath().getFileName().toString());
			
		}
	};
	
	
	/**
	 * Writes the provided {@link SingleDocumentModel} to the provided path
	 * @param model The provided model
	 * @param path The provided path
	 */
	private void writeToDisk(SingleDocumentModel model, Path path) {
		
		String s = model.getTextComponent().getText();
		
		try {
			Files.writeString(path, s, StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(this, "Unable to save document");
		}
		
		return;
		
	}
	
	/**
	 * Loads the image icon from the resources
	 * @param icon The image icon to be loaded
	 * @return Returns the loaded image icon
	 */
	private ImageIcon loadIcon(String icon) {
		
		byte[] bytes;
		
		try(InputStream is = this.getClass().getResourceAsStream("icons/" + icon)){
			if(is == null) {
				throw new RuntimeException("There was an error while loading the icon");
			}
			
			bytes = is.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException("There was an error while loading the icon");
		}
		
		return new ImageIcon(bytes);
		
	}

}
