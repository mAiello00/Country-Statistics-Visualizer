package statsVisualiser.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * @author      Group 19 
 * CS 2212 Final Project
 * This following class is the implementation of the facade design pattern.
 * This is the central hub with most of the methods concerning user choice
 * that was done in the main UI.
 */
public class RenderViewers {
	/**
	 * The title of the first series, represents the first y dataset
	 * and is rendered on the left y-axis when relevant
	 */
	String seriesTitle1;
	/**
	 * The title of the second series, represents the second y dataset
	 * and is rendered on the right y-axis when relevant
	 */
	String seriesTitle2;
	/**
	 * The analysis method, will also serve as the title of the chart
	 */
	String chartTitle;
	/**
	 * Array storing the user selected range of years for analysis
	 */
	int[] yearRange;
	/**
	 * Array storing the data for the first series of viewer
	 */
	double[] seriesData1;
	/**
	 * Array storing the data for the second series of viewer
	 */
	double[] seriesData2;
	
	/**
	 * Constructor for the RenderViewer object, possess the arrays 
	 * containing the year range and analysis data. These represent 
	 * the x-axis and y-axis respectively
	 * @param int[] yearRange, representing the user selected range of year
	 * @param double[] seriesData1, representing the first series of data
	 * @param double[] seriesData2, representing the second series of data
	 */
	
	public RenderViewers(int[] yearRange, double[] seriesData1, double[] seriesData2) {
		this.yearRange = yearRange;
		this.seriesData1 = seriesData1;
		this.seriesData2 = seriesData2;
	}
	
	/**
	 * This method will serve to read viewerList and then proceed to 
	 * call the methods of each respective viewer in the list
	 * 
	 * @param  ArrayList<String> viewerList, user selected list of charts to be rendered        
	 * @return JFreeChart chart, returns the resultant array of charts
	 */
	
	public JFreeChart[] createCharts(ArrayList<String> viewerList) {
		JFreeChart[] chart = new JFreeChart[viewerList.size()];
		
		//Loop through all userselect viewers and add them to returning array
		for (int i = 0; i < viewerList.size(); i++){
			String curr = viewerList.get(i);
			
			if (curr.equals("None Selected")) {
				return null;
			}
			
			else if (curr.contains("Line Chart")) {
				chart[i] = createLine();
			}
			
			else if (curr.equals("Bar Chart")) {
				chart[i] = createBar();
			}
			
			else if (curr.equals("Scatter Chart")) {
				chart[i] = createScatter();
			}
			
			else if (curr.equals("Report")) {
				chart[i] = null;
			}
		}
		return chart;
	}

	/**
	 *  This method creates a viewer containing the results of the analysis
	 *  which have been textualized and ordered by year
	 *
	 * @param  N/A         
	 * @return JTextArea report, completed results from analysis
	 */
	
	public JTextArea createReport() {
		//Prepare report panel
		JTextArea report = new JTextArea();
		report.setEditable(false);
		report.setPreferredSize(new Dimension(400, 300));
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		
		//Add textualized analysis
		String reportMessage = chartTitle + "\n" + "======================\n";
		
		for (int i = 0; i < yearRange.length; i++) {
			reportMessage += "Year " + yearRange[i] + ": \n" + "\t" + seriesTitle1 + " => ";
			if (seriesData1[i] == 0)
				reportMessage += "No Data Available";
			else
				reportMessage += seriesData1[i];
			
			reportMessage += "\n" + "\t" + seriesTitle2 + " => ";
			
			if (seriesData2[i] == 0)
				reportMessage += "No Data Available";
			else
				reportMessage += seriesData2[i]; 
			
			reportMessage +="\n" + "\n";
		}

		report.setText(reportMessage);
		return report;
	}

	/**
	 * This method serves to create the viewer containing a scatter chart
	 * plotting the series of the analysis against the year range specified
	 *
	 * @param  N/A         
	 * @return JFreeChart, completed viewer to be rendered
	 */
	
	private JFreeChart createScatter() {
		//Prepare dataset for scatter chart
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
	
		TimeSeries series = new TimeSeries(seriesTitle1);
		TimeSeries series2 = new TimeSeries(seriesTitle2);
		
		for (int i = 0; i < yearRange.length; i++) {
			if (seriesData1[i] != 0) 
				series.add(new Year(yearRange[i]), seriesData1[i]);
			
			if (seriesData2[i] != 0)		
				series2.add(new Year(yearRange[i]), seriesData2[i]);
		}
		
		dataset.addSeries(series);
		dataset2.addSeries(series2);
		
		//Render scatter plot
		XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, dataset);
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(seriesTitle1));		//1st y-axis

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis(seriesTitle2));		//2nd y-axis

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart(chartTitle,
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
        
        return scatterChart;
	}

	/**
	 * This method servers to create the viewer for the Bar graph where the value
	 * of each series will be plotted for each year in the range specified
	 *	
	 * @param  N/A         
	 * @return JFreeChart, completed viewer to be rendered
	 */
	
	private JFreeChart createBar() {
		//Prepare dataset for bar chart
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

		for (int i = 0; i < yearRange.length; i++) {
			if (seriesData1[i] != 0)
				dataset.setValue(seriesData1[i], new Year(yearRange[i]), seriesTitle1);
			if (seriesData2[i] != 0)		
				dataset2.setValue(seriesData2[i], new Year(yearRange[i]), seriesTitle2);
		}

		//Render bar plot
		 CategoryPlot plot = new CategoryPlot();
	     BarRenderer barrenderer1 = new BarRenderer();
	     BarRenderer barrenderer2 = new BarRenderer();

	     plot.setDataset(0, dataset);
	     plot.setRenderer(0, barrenderer1);
	     CategoryAxis domainAxis = new CategoryAxis("Year");
	     plot.setDomainAxis(domainAxis);
	     plot.setRangeAxis(new NumberAxis(seriesTitle1));   //1st y-axis

	     plot.setDataset(1, dataset2);
	     plot.setRenderer(1, barrenderer2);
	     plot.setRangeAxis(1, new NumberAxis(seriesTitle2));   //2nd y-axis

	     plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
	     plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

	     JFreeChart barChart = new JFreeChart(chartTitle,(
	             new Font("Serif", java.awt.Font.BOLD, 18)), plot, true);
	     return barChart;
	}

	/**
	 * This method will serve to create the viewer for the Line chart which will plot
	 * the two data series against the years of the range specified
	 *
	 * @param  N/A         
	 * @return JFreeChart, completed viewer to be rendered
	 */
	
	private JFreeChart createLine() {
		//Prepare dataset for line graph
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries series = new XYSeries(seriesTitle1);
		XYSeries series2 = new XYSeries(seriesTitle2);
	
		for (int i = 0; i < yearRange.length; i++) {
			if (seriesData1[i] != 0) 						
				series.add(yearRange[i], seriesData1[i]);
			
			if (seriesData2[i] != 0)		
				series2.add(yearRange[i], seriesData2[i]);
		}
		
		dataset.addSeries(series);
		dataset.addSeries(series2);

		//Render line graph
		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, "Years", seriesTitle1, dataset,
				PlotOrientation.VERTICAL, true, true, false);
	   
		XYPlot plot = chart.getXYPlot();
	    
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		
		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));

		return chart;
	}
	
	/**
	 * A method to set the series of the viewers based on the user-selected
	 * analysis type for the object. Data will then be accessible through object
	 *
	 * @param  String analysisMethod, user selected method of analysis       
	 * @return N/A
	 */
	
	public void getSeries(String analysisMethod) {
		chartTitle = analysisMethod;
		String[] parts = analysisMethod.split(" vs ");
		
		//First series
		if (parts[0].equals("Population Total")) {
			seriesTitle1 = parts[0];
		}
			
		else if (parts[0].contains("GDP Per Capita")) {
			seriesTitle1 = parts[0];
		}
			
		else if (parts[0].equals("CO2 Emissions Per Capita")) {
			seriesTitle1 = parts[0];
		}
			
		//Second series
		if (parts[1].equals("CO2 Emissions Per Capita")) {
			seriesTitle2 = parts[1];
		}
			
		else if (parts[1].contains("% Forest Area For Total Land Area")) {
			seriesTitle2 = parts[1];
		}
			
		else if (parts[1].equals("Energy Use Per Capita")) {
			seriesTitle2 = parts[1];
		}
		
		else if (parts[1].equals("GDP Per Capita")) {
			seriesTitle2 = parts[1];
		}
	}

	/**
	 * A method to set the  first series of the viewers based on the 
	 * user-selected analysis
	 * 
	 * @param  double[] parArr, temporary array to supply series 1
	 * @return N/A
	 */
	
	public void setSeriesData1(double[] parArr) {
		seriesData1 = parArr;
		
	}
	
	/**
	 * A method to set the second series of the viewers based on 
	 * the user-selected analysis
	 * 
	 * @param  double[] parArr, temporary array to supply series 2
	 * @return N/A
	 */
	
	public void setSeriesData2(double[] parArr) {
		seriesData2 = parArr;
	}
}
