package statsVisualiser.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * @author      Group 19 
 * CS 2212 Final Project
 * This following class is the implementation of the facade design pattern.
 * This is the central hub with most of the methods concerning user choice
 * that was done in the main UI.
 */

public class facade {
	/**
	 * The viewer that the user selected from the main UI
	 */
	static String viewerChoice;
	/**
	 * The value used to  determine whether or not
	 * the user wants to add or remove the selected
	 * viewer
	 */
	static boolean result = true;
	/**
	 * The list containing all the viewers requested
	 */
	static ArrayList<String> viewerList = new ArrayList<String>();
	/**
	 * The country that the user selected from the main UI
	 */
	static String countryChoice;
	/**
	 * The start date that the user selected from the main UI
	 */
	static int startChoice;
	/**
	 * The end date that the user selected from the main UI
	 */
	static int endChoice;
	/**
	 * The analysis type that the user selected, by default this is
	 * the population total vs. CO2 emissions to test validity of other
	 * selections.
	 */
	static String methodsChoice = "Population Total vs CO2 Emissions Per Capita";
	/**
	 * The list of the allinfo.txt file which contains information about 
	 * abbreviations and other important info.
	 */
	static ArrayList<String> resultList = new ArrayList<String>();
	/**
	 * The start date of a country to compare with the user selected date
	 */
	static String startComp;
	/**
	 * The end date of a country to compare with the user selected date
	 */
	static String endComp; 
	
	/**
	 * Return the user country choice (string) done in main UI
	 *
	 * @param  N/A         
	 * @return countryChoice
	 */
	
	public static String getCountryChoice() {
    	return countryChoice;
    }
    
	/**
	 * Set the variable countryChoice to the one the user chose.
	 *
	 * This method will obtain what the user selected in the main
	 * UI and convert that country into the abbreviation. The 
	 * abbreviations are stored in a separate text file. This text
	 * file also contains information about the start and end date
	 * of the country which will be stored in each respective 
	 * variable for later use.
	 *
	 * @param  A string "s" which consists of the country the user chose
	 * from the main UI menu.    
	 * @return N/A
	 */
	
    public static void setCountryChoice(String s) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("allinfo.txt"))){
     	     String line;
     	     while ((line = br.readLine()) != null) {
     	     	 resultList.add(line);
     	     }
     	     String[] resultArr = new String[resultList.size()]; // convert to string so you can split
     	     resultArr = resultList.toArray(resultArr);
     	   
     	    for (String str : resultArr) // add the elements of the split string back into the array list
     		    for (String c : str.split(" "))
     		        resultList.add(c);
        	for (int i = 0; i<resultList.size(); i++) {
        		if (resultList.get(i).contains(s)) {
        			countryChoice = resultList.get(i).split(",")[1]; // read all important info from the selected country
        			startComp = resultList.get(i).split(",")[2];
        			endComp = resultList.get(i).split(",")[3];
        		}
        	}
        }
    }
    
	/**
	 * Return the user chosen method (String)
	 * from the scroll down menu. This is the
	 * analysis that the user wants.
	 *
	 * @param  N/A         
	 * @return methodsChoice
	 */
    
    public static String getMethodsChoice() {
    	return methodsChoice;
    }
    
	/**
	 * Return the list of viewers (type ArrayList) 
	 * that the user has chosen to add.
	 *
	 * @param  N/A         
	 * @return viewerList
	 */
    
    public static ArrayList<String> getViewerList() {
    	return viewerList;
    }
    
	/**
	 * Set the analysis method based on user selection.
	 *
	 * @param  a string "s" which is the user selected
	 * analysis from the main UI  .      
	 * @return N/A
	 */
    
    public static void setMethodsChoice(String s) {
    	methodsChoice = s;
    }
	
	/**
	 * Return the user chosen method (String)
	 * from the scroll down menu. This is the
	 * viewer that the user wants either 
	 * added or removed from the list
	 *
	 * @param  N/A         
	 * @return viewerChoice
	 */
    
    public static String getviewerChoice() {
    	return viewerChoice;
    }
    
	/**
	 * This method will obtain what viewer the user selected
	 * and set the viewerChoice variable to that value. Additional
	 * error checking to make sure the viewers are compatible with 
	 * the analysis type.
	 *
	 * @param  A string s that is the user selected viewer    
	 * from the drop down menu in the main UI    
	 * @return N/A
	 */
    
    public static void setviewerChoice(String s) {
    	if (s.contains("Pie Chart") || s.contains("None Selected")) { // only viewers not compatible with analysis
    		Component frame = null;
			JOptionPane.showMessageDialog((Component) frame, "Error, viewer not compatible with analysis");
    	}
    	viewerChoice = s;
    }
    
	/**
	 * Return the user chosen integer start date that
	 * they selected from the drop down menu in the main
	 * UI.
	 *
	 * @param  N/A         
	 * @return startChoice
	 */
    
    public static int getStartDate() {
    	return startChoice;
    }
    
	/**
	 * This method will obtain what start date that the user selected
	 * and make sure to save the user start choice if it is valid.
	 * This validation includes a check to see if the dates are compatible
	 * with the end date as well as making sure to convert "Now" into the
	 * current year
	 *
	 * @param  An int x that is the user selected start date from
	 * the scroll down menu in the main UI
	 * @return N/A
	 */
    
    public static void setStartDate(int x) {
    	int start = Integer.parseInt(getStartComp());
    	int end = 0;
    	startChoice = x; // make sure the end is not "now", if it is convert to current year
    	if (getEndComp().contains("Now"))
    		end = 2021;
    	else
    		end = Integer.parseInt(getEndComp());
    	if (x < start || x > end) {
    		Component frame = null;
			JOptionPane.showMessageDialog((Component) frame, "Error, start date not valid");
			startChoice = 0;
    	}

    }
    
	/**
	 * Return the user chosen integer end date that
	 * they selected from the drop down menu in the main
	 * UI.
	 *
	 * @param  N/A         
	 * @return endChoice
	 */
    
    public static int getEndDate() {
    	return endChoice;
    }
    
	/**
	 * Return the associated start date based on the 
	 * country and the text file with that given
	 * information.
	 *
	 * @param  N/A         
	 * @return startComp
	 */
    
    public static String getStartComp() {
    	return startComp;
    }
    
	/**
	 * Return the associated end date based on the 
	 * country and the text file with that given
	 * information.
	 *
	 * @param  N/A         
	 * @return endComp
	 */
    
    public static String getEndComp() {
    	return endComp;
    }
    
	/**
	 * This method will obtain what end date that the user selected
	 * and make sure to save the user end choice if it is valid.
	 * This validation includes a check to see if the dates are compatible
	 * with the start date as well as making sure to convert "Now" into the
	 * current year
	 *
	 * @param  An int x that is the user selected end date from
	 * the scroll down menu in the main UI
	 * @return N/A
	 */
    
    public static void setEndDate(int x) {
    	int start = Integer.parseInt(getStartComp());
    	int end = 0;
    	endChoice = x;
    	if (getEndComp().contains("Now"))
    		end = 2021;
    	else
    		end = Integer.parseInt(getEndComp());
    	if (x > end || x < start || getStartDate()>getEndDate()) {
    		Component frame = null;
			JOptionPane.showMessageDialog((Component) frame, "Error, end date not valid");
			endChoice = 0;
    	}
    }
    
	/**
	 * Return a boolean result that confirms what
	 * button the user pressed, they either want to
	 * add the selected viewer to the list or subtract
	 * (result is true for add and false for remove)
	 *
	 * @param  N/A         
	 * @return result
	 */
    
    public static boolean getButton() {
    	return result;
    }
    
	/**
	 * Receive whether the user wants to add or remove
	 * the selected viewer and saves that selection.
	 *
	 * @param  A boolean value choice that will be true
	 * if the user wants to add the viewer and false if 
	 * they want to remove the value from the list.      
	 * @return N/A
	 */
    
    public static void setButton(boolean choice) {
    	result = choice;
    }
    
	/**
	 * Receives a value from the user that confirms whether
	 * the user wants to add or remove their selected viewers.
	 * Either way, this method will check if it valid or it will
	 * inform the user of an error with a pop up box. If no error
	 * occurs it will add or remove the viewer as suggested. 
	 *
	 * @param  A boolean value status that is used to 
	 * determine whether to add or remove that viewer  
	 * @return N/A
	 */
    
    public static void performAction(boolean status) {
    	boolean alreadyExist = viewerList.contains(viewerChoice);
    	if (result == true) { // this means the user selected the add viewer button
    		Object frame = null;
			if (viewerChoice != null && alreadyExist == false) 
    			viewerList.add(viewerChoice); // confirm after error checking and do what the user requested
    		else if (alreadyExist == true)
    			JOptionPane.showMessageDialog((Component) frame, "Error, viewer already added to list");
    		else 
    			JOptionPane.showMessageDialog((Component) frame, "Error, please select a viewer.");
    			
    	}
    	else if (result == false){ // user selected the remove viewer button
    		Object frame = null;
    		if (alreadyExist == false) 
    			JOptionPane.showMessageDialog((Component) frame, "Error, viewer cannot be deleted because it is not in the list.");
    		else
    			viewerList.remove(viewerChoice);
    	}	
    }
    
}
