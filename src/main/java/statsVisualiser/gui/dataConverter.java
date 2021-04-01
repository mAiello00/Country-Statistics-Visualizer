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
	private double forestArea = 0;
	private double energyUse = 0;
	private double GDP = 0;
	private String thisCountry;
	private int thisStartDate;
	private int thisEndDate;
	
	public dataConverter(String country, int startDate, int endDate)
	{
		thisCountry = country;
		thisStartDate = startDate;
		thisEndDate = endDate;
	}
	
	public int[] getPopArray()
	{	
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray popJson = reader.retrievePopData();
		int size = popJson.size();
		int sizeOfResults = popJson.get(1).getAsJsonArray().size();
		int year;
		int[] popArray = new int[sizeOfResults];
		
		for(int i = 0; i < sizeOfResults; i++)
		{
			if (popJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
				pop = 0;
			else
				pop = popJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsInt();
				
			popArray[i] = pop;
			System.out.println(popArray[i]);
		}
		
		return popArray;
	}
	
	public double[] getCO2Array()
	{
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray CO2Json = reader.retrieveCO2();
		int sizeCO2 = CO2Json.size();
		int sizeOfResultsCO2 = CO2Json.get(1).getAsJsonArray().size();
		int year;
		double[] CO2Array = new double[sizeOfResultsCO2];
		
		for(int i = 0; i < sizeOfResultsCO2; i++)
		{
			if (CO2Json.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
				CO2 = 0;
			else
				CO2 = CO2Json.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
				
			CO2Array[i] = CO2;
			System.out.println(CO2Array[i]);
		}
		
		return CO2Array;
	}
	
	public double[] getForestArea()
	{
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray forestJson = reader.retrieveForestArea();
		int sizeforest = forestJson.size();
		int sizeOfResultsforest = forestJson.get(1).getAsJsonArray().size();
		int year;
		double[] forestArray = new double[sizeOfResultsforest];
		
		for(int i = 0; i < sizeOfResultsforest; i++)
		{
			if (forestJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
				forestArea = 0;
			else
				forestArea = forestJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
				
			forestArray[i] = forestArea;
			System.out.println(forestArray[i]);
		}
		
		return forestArray;
	}
	
	public double[] getEnergyUse()
	{
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray energyJson = reader.retrieveEnergyUseCapita();
		int sizeEnergy = energyJson.size();
		int sizeOfResultsEnergy = energyJson.get(1).getAsJsonArray().size();
		int year;
		double[] energyArray = new double[sizeOfResultsEnergy];
		
		for(int i = 0; i < sizeOfResultsEnergy; i++)
		{
			if (energyJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
				energyUse = 0;
			else
				energyUse = energyJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
				
			energyArray[i] = energyUse;
			System.out.println(energyArray[i]);
		}
		
		return energyArray;
	}
	
	public double[] getGDP()
	{
		API_Reader reader = new API_Reader(thisCountry, thisStartDate, thisEndDate);
		JsonArray GDPJson = reader.retrieveGDP_Capita();
		int sizeGDP = GDPJson.size();
		int sizeOfResultsGDP = GDPJson.get(1).getAsJsonArray().size();
		int year;
		double[] GDPArray = new double[sizeOfResultsGDP];
		
		for(int i = 0; i < sizeOfResultsGDP; i++)
		{
			if (GDPJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
				GDP = 0;
			else
				GDP = GDPJson.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
				
			GDPArray[i] = GDP;
			System.out.println(GDPArray[i]);
		}
		
		return GDPArray;
	}
}
