package statsVisualiser.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class API_Reader 
{
	private String country;
	private int startDate;
	private int endDate;
	private int populationForYear = 0;
	private String urlString;
	private String inline = "";
	private double yearValue = 0;
	
	/**
	 * Sets the country, start date and end date needed to retrieve data from te world bank repository
	 * @param newCountry
	 * @param newStartDate
	 * @param newEndDate
	 */
	public API_Reader(String newCountry, int newStartDate, int newEndDate)
	{
		country = newCountry;
		startDate = newStartDate;
		endDate = newEndDate;	
	}
	/**
	 * Creates the url to get population
	 * @return array with the population
	 */
	public JsonArray setPopLink()
	{
		urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/SP.POP.TOTL?date=%s:%s&format=json", country, startDate, endDate);
		JsonArray intArray = integerArrays();
		return intArray;
	}
	
	/**
	 * Creates the url to get GDP per capita
	 * @return array with gdp per capita
	 */
	public JsonArray setGDPLink()
	{
		urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/NY.GDP.MKTP.CD?date=%s:%s&format=json", country, startDate, endDate);
		JsonArray doubleArray = doubleArrays();
		return doubleArray;
	}
	
	/**
	 * Creates the url to get CO2 emission per capita
	 * @return array with CO2 per capita
	 */
	public JsonArray setCO2Link()
	{
		urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/EN.ATM.CO2E.PC?date=%s:%s&format=json", country, startDate, endDate);
		JsonArray doubleArray = doubleArrays();
		return doubleArray;
	}
	
	/**
	 * Creates the url to get forest area %
	 * @return array with forest area % compared to total land area
	 */
	public JsonArray setForestLink()
	{
		urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/AG.LND.FRST.ZS?date=%s:%s&format=json", country, startDate, endDate);
		JsonArray doubleArray = doubleArrays();
		return doubleArray;
	}
	
	/**
	 * Creates the url to get energy use per capita
	 * @return energy use per capita
	 */
	public JsonArray setEnergyUseLink()
	{
		urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/EG.USE.PCAP.KG.OE?date=%s:%s&format=json", country, startDate, endDate);
		JsonArray doubleArray = doubleArrays();
		return doubleArray;
	}
	
	/**
	 * Opens the url and gets the Json array contiaing values of the population for each year
	 * @return the json array containing the population values
	 */
	public JsonArray integerArrays()
	{
		try
		{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				
				for(int i = 0; i < sizeOfResults; i++)
				{
					try
					{
						year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
						if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
							populationForYear = 0;
						else
							populationForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsInt();
					}
					catch(NumberFormatException e)
					{
						year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
						if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
							populationForYear = 0;
						else
							populationForYear = 0;
					}	
				}			
				return jsonArray;
			}
		}
		catch(IOException e)
		{
			
		}
		
		return null;
	}
	/**
	 * 
	 * @return the json array containing either, GDP ,forest %, co2, or energy use depending on which methiod called it
	 */
	public JsonArray doubleArrays()
	{
		try
		{
			//Open the corresponding url 
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				
				for(int i = 0; i < sizeOfResults; i++)
				{
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						yearValue = 0;
					else
						yearValue = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
				}

				return jsonArray;
			}
			
		}
		catch(IOException e)
		{
			
		}
		return null;
	}
}

