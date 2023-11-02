package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * A class which is the main class for the notepad project
 * @author Vito Sabalic
 *
 */
public class JNotepadPP extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Contains all the open documents and their information
	 */
	private DefaultMultipleDocumentModel documentModel;
	
	/**
	 * The status bar
	 */
	private JPanel statusBar;
	
	/**
	 * A clipboard for copy/cut/paste
	 */
	private String clipboard;
	
	/**
	 * The localization provider
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * initializes variables and adds a listener to the {@link DefaultMultipleDocumentModel} instance
	 */
	public JNotepadPP() {
		super("JNotepad++");
		documentModel = new DefaultMultipleDocumentModel();
		
		documentModel.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if(documentModel.getNumberOfDocuments() == 0) {
					
					JNotepadPP.this.setTitle("JNotepad++");
					saveDocument.setEnabled(false);
					saveDocumentAs.setEnabled(false);
					closeCurrentDocument.setEnabled(false);
					statisticalInfo.setEnabled(false);
					paste.setEnabled(false);
					resetStatusBar();
					
					return;
					
				}
				
				JNotepadPP.this.changeTitle(documentModel.getCurrentDocument());
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				
				JNotepadPP.this.changeTitle(model);
				
				saveDocument.setEnabled(true);
				saveDocumentAs.setEnabled(true);
				closeCurrentDocument.setEnabled(true);
				statisticalInfo.setEnabled(true);
				
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				
				JNotepadPP.this.changeTitle(currentModel);
				
				JTextArea text = currentModel.getTextComponent();
				updateStatusBar();
			
				
				if(Math.abs(text.getCaret().getDot() - text.getCaret().getMark()) != 0) {
					setModificationButtons(true);
					return;
				}
				
				setModificationButtons(false);
				
			}
		});
		
		statusBar = new JPanel(new GridLayout(1, 0));
		clipboard = new String();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(600, 600);
		setLocationRelativeTo(null);
		
		initGUI();
	}
	
	/**
	 * Initializes the gui
	 */
	public void initGUI() {
		
		
		JLabel length = new JLabel("length: 0");
		JLabel info = new JLabel("Ln: 0  Col: 0  Sel: 0");
		JLabel date = new JLabel("");
		statusBar.add(length);
		statusBar.add(info);
		statusBar.add(date);	
		info.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		addDateAndTime();
		
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(documentModel, BorderLayout.CENTER);
		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
		createMenus();
		createToolbar();
		initializeActions();
		
		
		this.addWindowListener(closingAdapter);
		
	}
	
	
	/**
	 * The main EDT
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
		
	}
	
	/**
	 * A window adapter which checks whether there are unsaved files in the current document model
	 */
	private final WindowAdapter closingAdapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			
			boolean saved = true;
			
			for(SingleDocumentModel model : documentModel) {
				if(model.isModified()) {
					saved = false;
					break;
				}
			}
			
			if(saved) {
				JNotepadPP.this.dispose();
				return;
			}

			
			int counter = 0;
			
			
			for(SingleDocumentModel model: documentModel) {
				
				if(model.isModified()) {
					documentModel.setSelectedIndex(counter);
					int rezultat = JOptionPane.showConfirmDialog(JNotepadPP.this, "There are unsaved documents. Do you want to save them?", "Alert!", JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.WARNING_MESSAGE);

					if(rezultat == JOptionPane.YES_OPTION) {
						saveDocument.actionPerformed(new ActionEvent(model, 0, "ClosingSave"));
					}
					else if(rezultat == JOptionPane.NO_OPTION) {
						counter++;
						continue;
					}
					else {
						return;
					}
				
				}
				
				counter++;
			}
			
			JNotepadPP.this.dispose();
			
		}
	};
	
	
	/**
	 * Creates a new document
	 */
	private final Action createNewDocument = new LocalizableAction("New", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			documentModel.createNewDocument();
			
		}
	};
	
	/**
	 * Opens a document selected with {@link JFileChooser}
	 */
	private final Action openDocument = new LocalizableAction("Open", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser jc = new JFileChooser("/");
			jc.setDialogTitle("Open new document");
			
			if(jc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path path = jc.getSelectedFile().toPath();
					
			documentModel.loadDocument(path);
			
		}
	};
	
	/**
	 * Saves a document
	 */
	private final Action saveDocument = new LocalizableAction("Save", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SingleDocumentModel model;
			
			if(e.getActionCommand().equalsIgnoreCase("ClosingSave")) {
				model = (SingleDocumentModel)e.getSource();
			}
			else {
				model = documentModel.getCurrentDocument();
			}
			
			if(model.getFilePath() == null) {
				saveDocumentAs.actionPerformed(e);
				return;
			}
			
			documentModel.saveDocument(model, null);
			
		}
	};
	
	/**
	 * Saves the document to the path chosen with {@link JFileChooser}
	 */
	private final Action saveDocumentAs = new LocalizableAction("Saveas", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			
			JFileChooser jc = new JFileChooser("/");
			jc.setDialogTitle("Save a document");
			
			
			while(true) {
				if(jc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				if(!(containsPath(jc.getSelectedFile().toPath())) || documentModel.getCurrentDocument().getFilePath().equals(jc.getSelectedFile().toPath())) {
					break;
				}
				
				JOptionPane.showMessageDialog(JNotepadPP.this, "The selected file is already opened in the editor.");
			}
			
			Path path = jc.getSelectedFile().toPath();
			
			
			SingleDocumentModel model;
			
			if(e.getActionCommand().equalsIgnoreCase("ClosingSave")) {
				model = (SingleDocumentModel)e.getSource();
			}
			else {
				model = documentModel.getCurrentDocument();
			}
			
			documentModel.saveDocument(model, path);
			
		}
	};
	
	/**
	 * Closes the currently active document
	 */
	private final Action closeCurrentDocument = new LocalizableAction("Close", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			documentModel.closeDocument(documentModel.getCurrentDocument());
			
		}
	};
	
	/**
	 * Cuts the highlighted text
	 */
	private final Action cut = new LocalizableAction("Cut", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manipulateText("Cut");
			
		}
	};
	
	/**
	 * Copies the highlighted text
	 */
	private final Action copy = new LocalizableAction("Copy", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manipulateText("Copy");
			
		}
	};
	
	/**
	 * Pastes the text from the clipboard
	 */
	private final Action paste = new LocalizableAction("Paste", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manipulateText("Paste");
			
		}
	};
	
	/**
	 * Calculates and shows statistical info of the currently active document
	 */
	private final Action statisticalInfo = new LocalizableAction("Statistics", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextArea current = documentModel.getCurrentDocument().getTextComponent();
			Document doc = current.getDocument();
			
			int allCharacters = doc.getLength();
			int characters = calculateCharacters(doc);
			int lines = current.getLineCount();
			
			JOptionPane.showMessageDialog(JNotepadPP.this, "No. of all characters: " + allCharacters +
					"\nNo. of characters: " + characters + "\nNo. of lines: " + lines);
			
		}
	};
	
	/**
	 * Exits the application
	 */
	private final Action exit = new LocalizableAction("Exit", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			closingAdapter.windowClosing(null);
			
		}
	};
	
	
	/**
	 * Changes the highlighted text to upper case
	 */
	private final Action upperCase = new LocalizableAction("Uppercase", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manipulateText("Uppercase");
			
		}
	};
	
	
	/**
	 * Changes the highlighted text to lower case
	 */
	private final Action lowerCase = new LocalizableAction("Lowercase", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manipulateText("Lowercase");
			
		}
	};
	
	
	/**
	 * Toggles the case of the highlighted text
	 */
	private final Action toggle = new LocalizableAction("Togglecase", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manipulateText("Toggle");
			
		}
	};
	
	
	/**
	 * Sorts selected lines in ascending order
	 */
	private final Action ascending = new LocalizableAction("Ascending", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortCharacters(true);
			
		}
	};
	
	
	/**
	 * Sorts selected lines in descending order
	 */
	private final Action descending = new LocalizableAction("Descending", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortCharacters(false);
			
		}
	};
	
	
	/**
	 * Removes duplicate lines from the highlighted lines
	 */
	private final Action unique = new LocalizableAction("Unique", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
									
			Set<String> uniqueList = new LinkedHashSet<>(getSelectedLines());
			
			String s = new String();
			
			for(String str : uniqueList) {
				s += str + "\n";
			}
			
			insertChangedLines(s);						
		}
	};
	
	
	/**
	 * Changes language to croatian
	 */
	private final Action croatian = new LocalizableAction("Croatian", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String current = LocalizationProvider.getInstance().getCurrentLanguage();
			
			if(current.equalsIgnoreCase("en")) {
				english.setEnabled(true);
			}
			else {
				german.setEnabled(true);
			}
			
			LocalizationProvider.getInstance().setLanguage("hr");
			
			croatian.setEnabled(false);
			
		}
	};
	
	
	/**
	 * Changes language to English
	 */
	private final Action english = new LocalizableAction("English", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String current = LocalizationProvider.getInstance().getCurrentLanguage();
			
			if(current.equalsIgnoreCase("hr")) {
				croatian.setEnabled(true);
			}
			else {
				german.setEnabled(true);
			}
			
			LocalizationProvider.getInstance().setLanguage("en");
			
			english.setEnabled(false);
			
		}
	};
	
	
	/**
	 * Changes language to German
	 */
	private final Action german = new LocalizableAction("German", flp) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String current = LocalizationProvider.getInstance().getCurrentLanguage();
			
			if(current.equalsIgnoreCase("en")) {
				english.setEnabled(true);
			}
			else {
				croatian.setEnabled(true);
			}
			
			LocalizationProvider.getInstance().setLanguage("de");
			
			german.setEnabled(false);
			
		}
	};
	
	
	
	/**
	 * Adds date and time counter to the status bar, called in status bar initialization
	 */
	private void addDateAndTime() {
				
		JLabel label = (JLabel) statusBar.getComponent(2);
			
		label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		

		Timer timer = new Timer(1000, l -> {
			
			label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		});
		
		timer.start();
		
	}
	
	/**
	 * Updates the status bar, called when a change is made to the document
	 */
	private void updateStatusBar() {
		JLabel length = (JLabel) statusBar.getComponent(0);
		JLabel info = (JLabel) statusBar.getComponent(1);
		
		JTextComponent c = documentModel.getCurrentDocument().getTextComponent();
		int pos = c.getCaretPosition();
		Document doc = c.getDocument();
		Element root = doc.getDefaultRootElement();
		
		int row = root.getElementIndex(pos);
		int col = pos - root.getElement(row).getStartOffset();
		
		int len = Math.abs(c.getCaret().getDot() - c.getCaret().getMark());
		
		row++;
		col++;
		
		length.setText("length: " + doc.getLength());
		info.setText("Ln: " + row + "  Col: " + col + "  Sel: " + len);
	}
	
	/**
	 * Resets the status bar to its initial value, called when no documents are opened
	 */
	private void resetStatusBar() {
		
		JLabel length = (JLabel) statusBar.getComponent(0);
		JLabel info = (JLabel) statusBar.getComponent(1);
		
		
		length.setText("length: 0");
		info.setText("Ln: 0  Col: 0  Sel: 0");
			
	}
	
	
	
	/**
	 * Creates the menus
	 */
	private void createMenus() {
		
		JMenuBar menuBar = new JMenuBar();
		
		
		
		JMenu fileMenu = new JMenu(new LocalizableAction("File", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		JMenu editMenu = new JMenu(new LocalizableAction("Edit", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
								
			}
		});
		JMenu optionsMenu = new JMenu(new LocalizableAction("Options", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		JMenu tools = new JMenu(new LocalizableAction("Tools", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		JMenu languages = new JMenu(new LocalizableAction("Languages", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		fileMenu.add(new JMenuItem(createNewDocument));
		fileMenu.add(new JMenuItem(openDocument));
		fileMenu.add(new JMenuItem(saveDocument));
		fileMenu.add(new JMenuItem(saveDocumentAs));
		fileMenu.add(new JMenuItem(closeCurrentDocument));
		
		
		editMenu.add(new JMenuItem(cut));
		editMenu.add(new JMenuItem(copy));
		editMenu.add(new JMenuItem(paste));
		
		
		optionsMenu.add(new JMenuItem(statisticalInfo));
		optionsMenu.add(new JMenuItem(exit));
		
		
		JMenu changeCase = new JMenu(new LocalizableAction("Changecase", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		JMenu sort = new JMenu(new LocalizableAction("Sort", flp) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		changeCase.add(new JMenuItem(upperCase));
		changeCase.add(new JMenuItem(lowerCase));
		changeCase.add(new JMenuItem(toggle));
		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));
		tools.add(changeCase);
		tools.add(sort);
		tools.add(new JMenuItem(unique));
		
		languages.add(new JMenuItem(croatian));
		languages.add(new JMenuItem(english));
		languages.add(new JMenuItem(german));
		
		
		
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(optionsMenu);
		menuBar.add(tools);
		menuBar.add(languages);
		
		this.setJMenuBar(menuBar);
		
	}
	
	
	/**
	 * Creates the toolbar
	 */
	private void createToolbar() {
		
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton(createNewDocument));
		toolbar.add(new JButton(openDocument));
		toolbar.add(new JButton(saveDocument));
		toolbar.add(new JButton(saveDocumentAs));
		toolbar.add(new JButton(closeCurrentDocument));
		
		toolbar.addSeparator();
		
		toolbar.add(new JButton(cut));
		toolbar.add(new JButton(copy));
		toolbar.add(new JButton(paste));
		
		toolbar.addSeparator();
		
		toolbar.add(new JButton(statisticalInfo));
		toolbar.add(new JButton(exit));
		
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
		
	}
	
	
	
	/**
	 * Initializes all the action to their respective accelerator keys, mnemonics,
	 * and adds a short description
	 */
	private void initializeActions() {
		
		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocument.putValue(Action.SHORT_DESCRIPTION, "Opens a new document");
		
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Opens an existing document");
		
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Saves the current document");
		saveDocument.setEnabled(false);
		
		saveDocumentAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveDocumentAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAs.putValue(Action.SHORT_DESCRIPTION, "Choose where to save the current document");
		saveDocumentAs.setEnabled(false);
		
		closeCurrentDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		closeCurrentDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeCurrentDocument.putValue(Action.SHORT_DESCRIPTION, "Closes the currently open document");
		closeCurrentDocument.setEnabled(false);
		
		
		
		
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift C"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		cut.putValue(Action.SHORT_DESCRIPTION, "Cuts the selected value");
		
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Copies the selected value");
		
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		paste.putValue(Action.SHORT_DESCRIPTION, "Pastes the previously copied value");
		paste.setEnabled(false);
		
		
		
		
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statisticalInfo.putValue(Action.SHORT_DESCRIPTION, "Some cool statistical info");
		statisticalInfo.setEnabled(false);
		
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exit.putValue(Action.SHORT_DESCRIPTION, "Exit the application");
		
		
		
		upperCase.putValue(Action.SHORT_DESCRIPTION, "Change all selected characters to upper case");
		
		lowerCase.putValue(Action.SHORT_DESCRIPTION, "Change all selected characters to lower case");
		
		toggle.putValue(Action.SHORT_DESCRIPTION, "Toggle the cases of all selected characters");
		
		ascending.putValue(Action.SHORT_DESCRIPTION, "Sort selected lines in ascending order");
		
		descending.putValue(Action.SHORT_DESCRIPTION, "Sort selected lines in descending order");
		
		unique.putValue(Action.SHORT_DESCRIPTION, "Remove all duplicate lines");
		
		croatian.putValue(Action.SHORT_DESCRIPTION, "Change language to Croatian");
		
		english.putValue(Action.SHORT_DESCRIPTION, "Change language to English");
		english.setEnabled(false);
		
		german.putValue(Action.SHORT_DESCRIPTION, "Change language to German");
		
		
		setModificationButtons(false);
		
	}
	
	/**
	 * Removes the highlighted text and adds new, changed text based on the provided method
	 * @param method The provided method
	 */
	private void manipulateText(String method) {
		
		JTextArea current = documentModel.getCurrentDocument().getTextComponent();
		Document doc = current.getDocument();
			
		int len = Math.abs(current.getCaret().getDot() - current.getCaret().getMark());
		
		if(len == 0 && !(method.equals("Paste"))) {
			return;
		}
		
		int offset = Math.min(current.getCaret().getDot(), current.getCaret().getMark());
		
		try {
			
			switch(method) {
			
			case "Copy":
				clipboard = doc.getText(offset, len);
				break;
			
			case "Cut":
				clipboard = doc.getText(offset, len);
				doc.remove(offset, len);
				break;
			
			case "Paste":
				removeAndInsertString(doc, clipboard, offset, len);
				break;
				
			case "Uppercase":
				removeAndInsertString(doc, doc.getText(offset, len).toUpperCase(), offset, len);
				break;
			
			case "Lowercase":
				removeAndInsertString(doc, doc.getText(offset, len).toLowerCase(), offset, len);
				break;
				
			case "Toggle":
				removeAndInsertString(doc, toggleCase(doc.getText(offset, len)), offset, len);
				break;
				
			}
		}
		catch(BadLocationException e) {
			e.printStackTrace();
		}
		finally {
			setModificationButtons(false);
		}
	}
	
	/**
	 * Removes the text from the provided document and inserts the provided string based
	 * on the provided parameters.
	 * Called in functions which manipulate text
	 * @param doc The document whose text should be changed
	 * @param s The string to be inserted
	 * @param offset The offset from which to insert the string
	 * @param len The length of the text to be removed
	 * @throws BadLocationException
	 */
	private void removeAndInsertString(Document doc, String s, int offset, int len) throws BadLocationException {
		doc.remove(offset, len);
		doc.insertString(offset, s, null);
	}
	
	/**
	 * Sorts the characters based on the provided argument.
	 * @param ascending
	 */
	private void sortCharacters(boolean ascending) {
	
		List<String> list = getSelectedLines();
		
		Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);
		
		Collections.sort(list, collator);
				
		String s = new String();
		if(ascending) {
			
			for(int i = 0; i < list.size(); i++) {
				s += list.get(i) + "\n";
			}
		}
		else {		
			for(int i = list.size() - 1; i >= 0; i--) {
				s += list.get(i) + "\n";
			}
		}
		
		insertChangedLines(s);
		
	}
	
	/**
	 * Removes the highlighted lines and inserts the lines provided from the argument
	 * Called in sortCharacters function and unique action
	 * @param lines The lines to be inserted
	 */
	private void insertChangedLines(String lines) {
		JTextArea text = documentModel.getCurrentDocument().getTextComponent();
		Document doc = text.getDocument();
		
		int docSize = text.getLineCount();
		int offset = Math.min(text.getCaret().getDot(), text.getCaret().getMark());
		int maxOffset = Math.max(text.getCaret().getDot(), text.getCaret().getMark());
		int len = 0;
		
		try {
			len = text.getLineEndOffset(text.getLineOfOffset(maxOffset)) - text.getLineStartOffset(text.getLineOfOffset(offset));
			
			if(text.getLineOfOffset(maxOffset) + 1 == docSize) {
				lines = lines.substring(0, lines.length()-1);
			}
			
			removeAndInsertString(doc, lines, text.getLineStartOffset(text.getLineOfOffset(offset)), len);
		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the highlighted lines from the document as a list of strings
	 * @return return the highlighted lines
	 */
	private List<String> getSelectedLines(){
		
		List<String> list = new ArrayList<>();
		
		JTextArea text = documentModel.getCurrentDocument().getTextComponent();
		Document doc = text.getDocument();
		
		int offsetStart = Math.min(text.getCaret().getDot(), text.getCaret().getMark());
		int offsetEnd = Math.max(text.getCaret().getDot(), text.getCaret().getMark());
		
		try {
			int start = text.getLineOfOffset(offsetStart);
			int end = text.getLineOfOffset(offsetEnd);
			
			
			
			for(int i = start; i <= end; i++) {
				String test = doc.getText(text.getLineStartOffset(i), text.getLineEndOffset(i) - text.getLineStartOffset(i));
				list.add(test.trim());
			}
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Toggles the case of the provided string, called in manipulateText method
	 * in the toggle action
	 * @param text The text whose case should be toggled
	 * @return Returns the toggled string 
	 */
	private String toggleCase(String text) {
		
		
		char[] data = text.toCharArray();
		
		for(int i = 0; i < data.length; i++) {
			
			if(Character.isUpperCase(data[i])) {
				data[i] = Character.toLowerCase(data[i]);
			}
			else if(Character.isLowerCase(data[i])) {
				data[i] = Character.toUpperCase(data[i]);
			}
		}
		
		return new String(data);
		
	}
	
	
	/**
	 * Calculates all non whitespace characters in the provided document
	 * Called in the statistics action
	 * @param doc The provided document
	 * @return Returns the count of characters
	 */
	private int calculateCharacters(Document doc) {
		
		int counter = 0;
		
		
		char[] data = new char[doc.getLength()];
		
		try {
			data = doc.getText(0, doc.getLength()).toCharArray();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < doc.getLength(); i++) {	
			
			if(!(Character.isWhitespace(data[i]))) {
				counter++;
			}
		}
		
		return counter;
	}
	
	
	/**
	 * Changes the title of the window to accommodate the current document path
	 * Called when a change to the path of a document has been made
	 * @param model
	 */
	private void changeTitle(SingleDocumentModel model) {
		
		if(model.getFilePath() == null) {
			this.setTitle("(unnamed) - JNotepad++");
			return;
		}
		
		this.setTitle(model.getFilePath().toAbsolutePath().toString() + " - JNotepad++");
	
	}
	
	
	/**
	 * Sets the enabled status of the text manipulation buttons,
	 * called when they are needed to be switched on or off based on the current document status
	 * @param value The value which determines whether these buttons will be enabled or disabled
	 */
	private void setModificationButtons(boolean value) {
		copy.setEnabled(value);
		cut.setEnabled(value);
		paste.setEnabled(!clipboard.isBlank());
		lowerCase.setEnabled(value);
		upperCase.setEnabled(value);
		toggle.setEnabled(value);
		ascending.setEnabled(value);
		descending.setEnabled(value);
		unique.setEnabled(value);
		
	}
	
	/**
	 * Checks whether a {@link SingleDocumentModel} instance contains the provided path
	 * @param path The provided path
	 * @return True if contains, false otherwise
	 */
	private boolean containsPath(Path path) {
		
		for(SingleDocumentModel model: documentModel) {
			
			if(model.getFilePath() == null) {
				continue;
			}
			
			if(model.getFilePath().equals(path)) {
				return true;
			}
		}
		
		return false;
	}
	
}
