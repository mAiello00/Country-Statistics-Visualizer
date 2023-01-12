package statsVisualiser.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

/**
 * @author      Group 19 
 * CS 2212 Final Project
 * This following class is the implementation of the facade design pattern.
 * This is the central hub with most of the methods concerning user choice
 * that was done in the main UI.
 */
public class MainUI extends JFrame 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Single instance of MainUI generation. Singleton design structure
     */
    private static MainUI instance;

    /**
     * Method to get the instance of the mainUI
     * @return instance, returns mainUI
     */
    public static MainUI getInstance() 
    {
        if (instance == null)
            instance = new MainUI();

        return instance;
    }

    /**
     * Method to generate and act as the main user interface
     */
    private MainUI() 
    {
        // Set window title
        super("Country Statistics");
        // Set top bar
        JLabel chooseCountryLabel = new JLabel("Choose a country: ");
        final Vector<String> countriesNames = new Vector<String>();
        countriesNames.add("None Selected");
        try (BufferedReader br = new BufferedReader(new FileReader("cinfo.txt"))) 
        {
        	   String line;
        	   while ((line = br.readLine()) != null) 
        	   {
        	       countriesNames.add(line);
        	   }
        	} catch (IOException e) {
				e.printStackTrace();
			}
        
        final JComboBox<String> countriesList = new JComboBox<String>(countriesNames);
        
        //USER ACTION: Country for analysis selected, validity checked
		countriesList.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt) {
            	String s = (String) countriesList.getSelectedItem();
            	try 
            	{
					facade.setCountryChoice(s);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
		
		//Build year selection menu
        JLabel from = new JLabel("From");
        JLabel to = new JLabel("To");
        Vector<String> years = new Vector<String>();
        
        //Load year range
        for (int i = 2021; i >= 1962; i--) 
        {
            years.add("" + i);
        }
        
        final JComboBox<String> fromList = new JComboBox<String>(years);
        
        //USER ACTION: Start year selected, save selection and check validity
		fromList.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt) 
            {
            	String s = (String) fromList.getSelectedItem();
            	if (s == null) {
            		Component frame = null;
        			JOptionPane.showMessageDialog((Component) frame, "Error, start date not valid");
            	}
            	int start = Integer.parseInt(s);
            	facade.setStartDate(start);
            }
        });
		
        final JComboBox<String> toList = new JComboBox<String>(years);
        
        //USER ACTION: End year selected, save selection and check validity
		toList.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt) 
            {
            	String s = (String) toList.getSelectedItem();
            	int end = Integer.parseInt(s);
            	facade.setEndDate(end);
            }
        });


        //Set bottom bar of UI
        JButton recalculate = new JButton("Recalculate");
        
        //USER ACTION: Recalculate button selected
        recalculate.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	//If either the country, start date, or end date are null then we throw a pop-up message indicating an error
            	if(facade.getCountryChoice() == null || facade.getStartDate() == 0 || facade.getEndDate() == 0)
            	{
            		Component frame = null;
        			JOptionPane.showMessageDialog((Component) frame, "Error, please select a valid country and time frame");
        			return;
            	}
            	
            	//If no analysis method has been selected or the viewer list is empty we show a pop-up message 
            	if(facade.getMethodsChoice() == "None Selected" || facade.viewerList.size() == 0)
            	{
            		Component frame = null;
        			JOptionPane.showMessageDialog((Component) frame, "Error, please select a viewer");
        			return;
            	}
            	
            	if (getContentPane().getComponents().length == 4){
            		getContentPane().remove(3);
            	}
            	
            	JPanel west = new JPanel();
            	
            	//Creates the arrays that we store the data for each year in
            	int[] popArray = null;
            	double[] popArray2 = null;
            	double[] CO2Array = null;
            	double[] forestArray = null;
            	double[] GDPArray = null;
            	double[] energyUseArray = null;
            	boolean empty1 = false;
            	boolean empty2 = false;
            	String methodChoice = facade.getMethodsChoice();
            	dataConverter ret = new dataConverter(facade.getCountryChoice(), facade.getStartDate(), facade.getEndDate());
            	arrayChecker checker = new arrayChecker();

            	//Checks what method choice was selected and retrieves the data accordingly
            	if(methodChoice == "Population Total vs CO2 Emissions Per Capita")
            	{
            		popArray = ret.popData();
            		CO2Array = ret.doubleData("CO2");
            		popArray2 = ConvertToDouble.convert(popArray);
            		
            		empty1 = checker.checkInt(popArray);
            		empty2 = checker.checkDouble(CO2Array);		
            	}
            	else if(methodChoice == "Population Total vs % Forest Area For Total Land Area")
            	{
            		popArray = ret.popData();
            		forestArray = ret.doubleData("forest");
            		popArray2 = ConvertToDouble.convert(popArray);
            		
            		empty1 = checker.checkInt(popArray);
            		empty2 = checker.checkDouble(forestArray);
            	}
            	else if(methodChoice == "Population Total vs Energy Use Per Capita")
            	{
            		popArray = ret.popData();
            		energyUseArray = ret.doubleData("energy");
            		popArray2 = ConvertToDouble.convert(popArray);
            		
            		empty1 = checker.checkInt(popArray);
            		empty2 = checker.checkDouble(energyUseArray);
            	}
            	else if(methodChoice == "Population Total vs GDP Per Capita")
            	{
            		popArray = ret.popData();
            		GDPArray = ret.doubleData("GDP");
            		popArray2 = ConvertToDouble.convert(popArray);
            		
            		empty1 = checker.checkInt(popArray);
            		empty2 = checker.checkDouble(GDPArray);

            	}
            	else if(methodChoice == "GDP Per Capita vs Energy Use Per Capita")
            	{
            		GDPArray = ret.doubleData("GDP");
            		energyUseArray = ret.doubleData("energy");
            		
            		empty1 = checker.checkDouble(GDPArray);
            		empty2 = checker.checkDouble(GDPArray);
            	}
            	else if(methodChoice == "GDP Per Capita vs CO2 Emissions Per Capita")
            	{
            		GDPArray = ret.doubleData("GDP");
            		CO2Array = ret.doubleData("CO2");
            		
            		empty1 = checker.checkDouble(GDPArray);
            		empty2 = checker.checkDouble(CO2Array);
            	}
            	else if(methodChoice == "GDP Per Capita vs % Forest Area For Total Land Area")
            	{
            		GDPArray = ret.doubleData("GDP");
            		forestArray = ret.doubleData("forest");
            		
            		empty1 = checker.checkDouble(GDPArray);
            		empty2 = checker.checkDouble(forestArray);
            	}
            	else if(methodChoice == "CO2 Emissions Per Capita vs % Forest Area For Total Land Area")
            	{
            		CO2Array = ret.doubleData("CO2");
            		forestArray = ret.doubleData("forest");
            		
            		empty2 = checker.checkDouble(CO2Array);
            		empty2 = checker.checkDouble(forestArray);
            	}
            	
            	//Makes a record of the arrays so they can be used later on        	
            	arrays dataArrays = new arrays(popArray2, CO2Array, forestArray, GDPArray, energyUseArray);
            	
            	//If either array contains all 0s then there is no data so we throw a pop-up error message
            	if(empty1 == true || empty2 == true)
        		{
        			Component frame = null;
        			JOptionPane.showMessageDialog((Component) frame, "Error, the country does not contain data for this time frame");
        			return;
        		}  
			
            	//Otherwise we render the viewers with the appropriate data
        		else{
        			west = new JPanel();
        			renderViewers(dataArrays, west);
        		}
            }
        });
        
        //Sets the labels for all the analysis methods
        JLabel viewsLabel = new JLabel("Available Views: ");

        Vector<String> viewsNames = new Vector<String>();
        viewsNames.add("None Selected");
        viewsNames.add("Pie Chart");
        viewsNames.add("Line Chart");
        viewsNames.add("Bar Chart");
        viewsNames.add("Scatter Chart");
        viewsNames.add("Report");
        final JComboBox<String> viewsList = new JComboBox<String>(viewsNames);
       
        //USER ACTION: User selects viewer to add, sets selction to viewerChoice
        viewsList.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	String s = (String) viewsList.getSelectedItem();
            	facade.setviewerChoice(s);
            }
        });
        
        JButton addView = new JButton("+");
        
        //USER ACTION: User selects add viewer button, add viewerChoice to viewerList
        addView.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	facade.setButton(true);
            	facade.performAction(true);
            }
        });
   
        JButton removeView = new JButton("-");
        
        //USER ACTION: User selects subtract viewer button, remove viewerChoice from viewerList
        removeView.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	facade.setButton(false);
            	facade.performAction(false);
            }
        });

        JLabel methodLabel = new JLabel("        Choose analysis method: ");

        Vector<String> methodsNames = new Vector<String>();
        methodsNames.add("Population Total vs CO2 Emissions Per Capita");
        methodsNames.add("Population Total vs % Forest Area For Total Land Area");
        methodsNames.add("Population Total vs Energy Use Per Capita");
        methodsNames.add("Population Total vs GDP Per Capita");
        methodsNames.add("GDP Per Capita vs Energy Use Per Capita");
        methodsNames.add("GDP Per Capita vs CO2 Emissions Per Capita");
        methodsNames.add("GDP Per Capita vs % Forest Area For Total Land Area");
        methodsNames.add("CO2 Emissions vs % Forest Area For Total Land Area");

        final JComboBox<String> methodsList = new JComboBox<String>(methodsNames);
        
        //USER ACTION: Analysis method selected, set analysisMethod to selection
		methodsList.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt) 
            {
            	String s = (String) methodsList.getSelectedItem();
            	if (s!=facade.getMethodsChoice()) 
            	{
            		facade.getViewerList().clear();
            	}
            	facade.setMethodsChoice(s);
            }
        });

		//Update UI with country and year selection
        JPanel north = new JPanel();

        north.add(chooseCountryLabel);
        north.add(countriesList);
        north.add(from);
        north.add(fromList);
        north.add(to);
        north.add(toList);
		
		//all buttons added to bottom of panel
        JPanel south = new JPanel();
        south.add(viewsLabel);
        south.add(viewsList);
        south.add(addView);
        south.add(removeView);
        south.add(methodLabel);
        south.add(methodsList);
        south.add(recalculate);

        JPanel east = new JPanel();
        
        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(east, BorderLayout.EAST);
        getContentPane().add(south, BorderLayout.SOUTH);
    }
    /**
     * Method to render the viewers base on analysis data and render them in JPanel west
     * @param dataArrays results from analysis
     * @param west panel for renders to be placed on 
     */
    private void renderViewers(arrays dataArrays, JPanel west) {
		int startYear = facade.getStartDate();			//x-axis start -> end
		int endYear = facade.getEndDate();
		int[] yearRange = new int[endYear-startYear + 1];
		int j = 0;
        west.setLayout(new GridLayout(2, 0));
        String analysisMethod = facade.getMethodsChoice();
        ArrayList<String> viewerList = facade.getViewerList();
        JFreeChart[] charts;
			
        //Generate year range array based on start and end year
		for (int i = startYear; i <= endYear; i++) {
			yearRange[j] = i;
			j++;			
		}
		
		RenderViewers ren = new RenderViewers(yearRange, null, null);
		ren.getSeries(analysisMethod);
		
		//How
        if (ren.seriesTitle1.equals("Population Total")) {
       		ren.setSeriesData1(dataArrays.getPopArr());
    	}
   			
   		else if (ren.seriesTitle1.contains("GDP Per Capita")) {
   			ren.setSeriesData1(dataArrays.getGDPArr());    		
   		}
    			
   		else if (ren.seriesTitle1.equals("CO2 Emissions Per Capita")) {
   			ren.setSeriesData1(dataArrays.getCO2Arr());
   		}
    		
   		if (ren.seriesTitle2.equals("CO2 Emissions Per Capita")) {    			
   			ren.setSeriesData2(dataArrays.getCO2Arr());
    	}
    			
    	else if (ren.seriesTitle2.contains("% Forest Area For Total Land Area")) {
    		ren.setSeriesData2(dataArrays.getForestArr());
    	}
    			
    	else if (ren.seriesTitle2.equals("Energy Use Per Capita")) {
    		ren.setSeriesData2(dataArrays.getEnergyArr());
    	}
    		
    	else if (ren.seriesTitle2.equals("GDP Per Capita")) {
    		ren.setSeriesData2(dataArrays.getGDPArr());
    	}
   		
    	ren.getSeries(analysisMethod);
    	charts = ren.createCharts(viewerList);
        	
		for (int i = 0; i < charts.length; i++) {
			if (charts[i] != null) {
				ChartPanel chartPanel = new ChartPanel(charts[i]);
				chartPanel.setPreferredSize(new Dimension(400, 300));
			    chartPanel.setPreferredSize(new Dimension(400, 300));
			    chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			    chartPanel.setBackground(Color.white);
			    west.add(chartPanel);
			}
		}
			
		if (viewerList.contains("Report")) {
			JTextArea report = ren.createReport();
			JScrollPane outputScrollPane = new JScrollPane(report);
			west.add(outputScrollPane);
		}

		getContentPane().add(west, BorderLayout.WEST);
		getContentPane().getComponents()[3].revalidate();
		repaint();
    }
 

    public static void main(String[] args) 
    {
    	LoginProxy proxy = new LoginProxy();
    	proxy.createLogin();
    }

}