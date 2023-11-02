package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A class which initiates the bar chart and its main method is used as the EDT of this swing project
 * @author Vito Sabalic
 *
 */
public class BarChartDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * A constructor which initiates the {@link JFrame}
	 * @param chart The bar chart to be passed on to the {@link BarChartComponent}
	 * @param path The path to be added to the JFrame
	 */
	public BarChartDemo(BarChart chart, String path) {
		super("Bar chart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 500);
		initGUI(chart, path);
	}
	
	
	/**
	 * Initializes the GUI
	 * @param chart The bar chart to be passed on to the {@link BarChartComponent}
	 * @param path The path to be added to the JFrame
	 */
	private void initGUI(BarChart chart, String path) {
		getContentPane().setLayout(new BorderLayout());
		
		JComponent komponenta = new BarChartComponent(chart);
		
		JLabel label = new JLabel(path);
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		getContentPane().add(komponenta, BorderLayout.CENTER);
		getContentPane().add(label, BorderLayout.PAGE_START);
	}
	
	/**
	 * The main method of this class used as the EDT
	 * @param args The provided arguments, necessary for the Swing project to work properly
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		
		if(args.length != 1) {
			throw new IllegalArgumentException("There is no provided file path");
		}
		
		
		Path p = Paths.get(args[0]);
		
		if(!Files.isRegularFile(p)) {
			throw new IllegalArgumentException("Please ente a valid file");
		}
		
		
		List<String> values = Files.readAllLines(p);
		
		
		BarChart chart = parseBarChart(values);
		
		SwingUtilities.invokeLater(() ->
			
		new BarChartDemo(chart, p.toAbsolutePath().toString()).setVisible(true));
	}
	
    /**
     * Parses the lines of the provided file
     * @param fileLines A list containing the lines of the provided file
     * @return Returns a bar chart with all the assigned values
     */
    private static BarChart parseBarChart(List<String> fileLines) {
    	
    	if(fileLines.size() < 5) {
    		throw new IllegalArgumentException("The file is incorrectly written");
    	}
    	
    	String xName = fileLines.get(0);
    	String yName = fileLines.get(1);
    	
    	String[] pom = fileLines.get(2).split(" ");
    	
    	List<XYValue> xyvalues = new ArrayList<>();
    	
    	for(int i = 0; i < pom.length; i++) {
    		String[] temp = pom[i].split(",");
    		
    		xyvalues.add(new XYValue(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
    	}
    	
    	int minY = Integer.parseInt(fileLines.get(3));
    	
    	int maxY = Integer.parseInt(fileLines.get(4));
    	
    	int deltaY = Integer.parseInt(fileLines.get(5));
    	
    	
    	
    	return new BarChart(xyvalues, xName, yName, minY, maxY, deltaY);
    }

}
