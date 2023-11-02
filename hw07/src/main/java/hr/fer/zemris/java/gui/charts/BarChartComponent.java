package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Represents the {@link JComponent} which is used to draw the bar chart
 * @author Vito Sabalic
 *
 */
public class BarChartComponent extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The chart to be drawn
	 */
	private BarChart chart;
	
	
	/**
	 * A simple constructor which assigns the provided chart to the current char value
	 * @param chart The provided chart value
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension dim = this.getSize();
		int maxHeight = (int)dim.getHeight();
		int maxWidth = (int)dim.getWidth();
		
		int minHeight = 0;
		int minWidth = 0;
		
		
		AffineTransform at = new AffineTransform();
		at.rotate(- Math.PI / 2);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform defaultAt = g2d.getTransform();
		g2d.setTransform(at);
		String s = this.chart.getY_name();
		int width = g.getFontMetrics().stringWidth(s) / 2;
		
		minWidth = 20;
		g2d.drawString(s, (-maxHeight / 2) - width, minWidth);
		g2d.setTransform(defaultAt);
		
		
		maxHeight -= 20;
		s = this.chart.getX_name();
		width = g.getFontMetrics().stringWidth(s) / 2;
		g.drawString(s, maxWidth / 2 - width, maxHeight);
		
		
		
		String maxNumber = String.valueOf(this.chart.getMaxY());
		minWidth += 20;
		
		
		int oldMinWidth = minWidth;
		minWidth += g.getFontMetrics().stringWidth(maxNumber) + 20;
		
		
		int[] xPointsY_axis = {minWidth, minWidth - 5, minWidth + 5};
		int[] yPointsY_axis = {0, 5, 5};
		
		g.fillPolygon(xPointsY_axis, yPointsY_axis, 3);
		
		minHeight = 5;
		
		maxHeight -= 20;
		
		int oldMaxHeight = maxHeight;
		
		
		maxHeight -= 20;
		
		
		g.drawLine(minWidth, minHeight, minWidth, maxHeight);
		
		
		int[] xPointsX_axis = {maxWidth, maxWidth - 5, maxWidth - 5};
		int[] yPointsX_axis = {maxHeight, maxHeight + 5, maxHeight - 5};
		
		g.fillPolygon(xPointsX_axis, yPointsX_axis, 3);
		
		maxWidth -= 5;
		
		
		g.drawLine(minWidth, maxHeight, maxWidth, maxHeight);
		
		
		int deltaY = (maxHeight - minHeight) /  ((this.chart.getMaxY() - this.chart.getMinY())/this.chart.getDeltaY());
		
		int brojac = 0;
		
		for(int i = this.chart.getMinY(); i < this.chart.getMaxY(); i = i + this.chart.getDeltaY()) {
			
			g.drawString(addPaddingToChartNumbers(i), oldMinWidth, maxHeight - brojac * deltaY);
			brojac++;
		}
		
		g.drawString(addPaddingToChartNumbers(this.chart.getMaxY()), oldMinWidth, maxHeight - brojac * deltaY + 3);
		
		
		int deltaX = (maxWidth - minWidth) / this.chart.getValues().size();
		
		brojac = 0;
		
		for(int i = 1; i <= this.chart.getValues().size(); i++) {
			String str = String.valueOf(this.chart.getValues().get(i - 1).getX());
			g.drawString(str,minWidth + brojac * deltaX + deltaX / 2 - g.getFontMetrics().stringWidth(str) / 2, oldMaxHeight);
			brojac++;
		}		
		
		
		g.setColor(Color.ORANGE);
		
		for(int i = 0; i < this.chart.getValues().size(); i++) {
			
			
			int x1 = minWidth + i * deltaX;
			int x2 = minWidth + (i+1) * deltaX;
			
			int y1 = maxHeight;

			int y2 = ((maxHeight - minHeight) * this.chart.getValues().get(i).getY()) / this.chart.getMaxY();
			
			int[] xPoints = {x1 + 1, x2, x2, x1 + 1};
			int[] yPoints = {y1, y1, y1 - y2, y1 - y2};
			
			
			g.fillPolygon(xPoints, yPoints, 4);
		}
		
	}
	
	
	/**
	 * Adds necessary padding to the beginning of the string value of the provided number.
	 * Padding is added based on the size of the maximum number of the chart
	 * @param number The number whose string value will be padded
	 * @return Returns the padded string
	 */
	private String addPaddingToChartNumbers(int number) {
		
		String s = String.valueOf(number);
		
		String maxS = String.valueOf(this.chart.getMaxY());
		
		
		for(int i = 0; i < maxS.length() - s.length(); i++) {
			s = " " + s;
		}
		
		return s;
	}
	
	

}
