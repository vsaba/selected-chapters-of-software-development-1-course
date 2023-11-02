package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A class used whose main method is used as the EDT for the primary numbers GUI
 * @author Vito Sabalic
 *
 */
public class PrimDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor which initiates the {@link JFrame}
	 */
	public PrimDemo() {
		super("Primary Numbers");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		initGui();
	}
	
	/**
	 * Initializes the GUI
	 */
	public void initGui() {
		
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		
		JList<Integer> lista1 = new JList<>(model);
		JList<Integer> lista2 = new JList<>(model);
		
		lista1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lista2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(new JScrollPane(lista1));
		panel.add(new JScrollPane(lista2));
		
		cp.add(panel, BorderLayout.CENTER);
		
		JButton addPrimary = new JButton("Add a primary number!");
		
		addPrimary.addActionListener(e -> {
			model.next();
		});
		
		cp.add(addPrimary, BorderLayout.PAGE_END);
	}
	
	/**
	 * The main method of this class used as the EDT
	 * @param args The provided arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}

}
