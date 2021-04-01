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
	private int arrayLength;
	private int[] populations;
	private int populationForYear = 0;
	private double GDPForYear = 0;
	private double CO2CapitaForYear = 0;
	private double ForestAreaLand = 0;
	private double EnergyUseCapita = 0;
	private String urlString;
	
	public API_Reader(String newCountry, int newStartDate, int newEndDate)
	{
		country = newCountry;
		startDate = newStartDate;
		endDate = newEndDate;	
	}
	
	public JsonArray retrievePopData()
	{
		try
		{
			urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/SP.POP.TOTL?date=%s:%s&format=json", country, startDate, endDate);
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				System.out.println(sizeOfResults);
				for(int i = 0; i < sizeOfResults; i++)
				{
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						populationForYear = 0;
					else
						populationForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsInt();

					System.out.println("Population for : " + year + " is " + populationForYear);
				}
				
				return jsonArray;
			}	
			
		}
		catch(IOException e)
		{
			
		}
		
		return null;
	}
	
	public JsonArray retrieveGDP_Capita()
	{
		try
		{
			urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/NY.GDP.MKTP.CD?date=%s:%s&format=json", country, startDate, endDate);
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				
				for(int i = 0; i < sizeOfResults; i++)
				{
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						GDPForYear = 0;
					else
						GDPForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
					
					System.out.println("GDP for : " + year + " is " + GDPForYear);
				}

				return jsonArray;	
			}
					
		}
		catch(IOException e)
		{
		}
		
		return null;
	}
	
	public JsonArray retrieveCO2()
	{
		try
		{
			urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/EN.ATM.CO2E.PC?date=%s:%s&format=json", country, startDate, endDate);
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				
				for(int i = 0; i < sizeOfResults; i++)
				{
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						CO2CapitaForYear = 0;
					else
						CO2CapitaForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
					
					System.out.println("CO2 for : " + year + " is " + CO2CapitaForYear);
				}
				
				return jsonArray;
			}
			
		}
		catch(IOException e)
		{
			
		}
		
		return null;
	}
	
	public JsonArray retrieveForestArea()
	{
		try
		{
			urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/AG.LND.FRST.ZS?date=%s:%s&format=json", country, startDate, endDate);
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				
				for(int i = 0; i < sizeOfResults; i++)
				{
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						ForestAreaLand = 0;
					else
						ForestAreaLand = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
					
					System.out.println("Forest Area % Land for : " + year + " is " + ForestAreaLand);
				}
				
				return jsonArray;
				
			}
		}
		catch(IOException e)
		{
			
		}
		return null;
	}
	
	public JsonArray retrieveEnergyUseCapita()
	{
		try
		{
			urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/EG.USE.PCAP.KG.OE?date=%s:%s&format=json", country, startDate, endDate);
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			
			if(responsecode == 200)
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year;
				
				for(int i = 0; i < sizeOfResults; i++)
				{
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						EnergyUseCapita = 0;
					else
						EnergyUseCapita = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
					
					System.out.println("Energy Use Per Capita for : " + year + " is " + EnergyUseCapita);
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

