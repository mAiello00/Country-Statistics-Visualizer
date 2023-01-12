package statsVisualiser.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class dataConverter
{
	private int pop = 0;
	private double CO2 = 0;
	private String thisCountry;
	private int thisStartDate;
	private int thisEndDate;
	
	/**
	 * Sets the country, start date, and end date so we can collect information from the database
	 * @param country name of the country as a string
	 * @param startDate date we start at as an integer
	 * @param endDate date we end at as an integer
	 */
	public dataConverter(String country, int startDate, int endDate)
	{
		thisCountry = country;
		thisStartDate = startDate;
		thisEndDate = endDate;
	}
	
	/**
	 * Gets the JSon array from the API_Reader class and converts it into an array of integers
	 * @return popArray is the array containing integer values for the population of the country over the given ime range
	 */
	public int[] popData()
	{
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray popJson = reader.setPopLink();
		int sizeOfResults = popJson.get(1).getAsJsonArray().size();
		int[] popArray = new int[sizeOfResults];
		
		//Loop through the JSon array we got and create an integer array with the values
		for(int i = 0; i < sizeOfResults; i++)
		{
			try
			{
				//If the value of the array is null then we set the position in the integer array to be 0
				if (popJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
					pop = 0;
				else//Otherwise we set the value in the JSon array to the integer array
					pop = popJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsInt();
					
				popArray[i] = pop;
			}
			catch(Exception e)
			{
				if (popJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
					pop = 0;
				else
					pop = 0;
					
				popArray[i] = pop;
			}
		}
		
		return popArray;
	}
	
	/**
	 * This method gets the type of analysis as a string and determines what type of informaton to retrieve from the repository. Once it gets a Json array, the data is converted
	 * into an array of doubles and returned.
	 * @param s the string containing the type of analysis that we are performing. Determines what we retrieve from the data repository
	 * @return doubleArray the array containing all the data for the country over the given time frame.
	 */
	public double[] doubleData(String s)
	{
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray doubleJson;
		//Checks the input string to determine what type of analysis we are conducting and generates  Json array accordingly by calling methods in API_Reader
		if(s == "CO2")
			doubleJson = reader.setCO2Link();
		else if(s == "forest")
			doubleJson = reader.setForestLink();
		else if(s == "GDP")
			doubleJson = reader.setGDPLink();
		else
			doubleJson = reader.setEnergyUseLink();
		
		int sJsonResults = doubleJson.get(1).getAsJsonArray().size();
		double[] doubleArray = new double[sJsonResults];
		
		//Loop through the JSon array we got and create an double array with the values
		for(int i = 0; i < sJsonResults; i++)
		{
			//If the value of the array is null then we set the position in the double array to be 0
			if (doubleJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
				CO2 = 0;
			else//Otherwise we set the value in the JSon array to the double array
				CO2 = doubleJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
				
			doubleArray[i] = CO2;
		}
		
		return doubleArray;
	}
}
