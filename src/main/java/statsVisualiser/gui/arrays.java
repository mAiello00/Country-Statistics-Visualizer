package statsVisualiser.gui;

public class arrays 
{
	private double[] pop;
	private double[] CO2;
	private double[] forest;
	private double[] GDP;
	private double[] energy;
	
	public arrays(double[] popArr, double[] CO2Arr, double[] forestArr, double[] GDPArr, double[] energyArr)
	{
		/**
		 * Gets the arrays for each type of data and saves them
		 */
		pop = popArr;
		CO2 = CO2Arr;
		forest = forestArr;
		GDP = GDPArr;
		energy = energyArr;
	}
	
	/**
	 * Function that gets the array containing population for the country during the specified time
	 * @return returns the population array
	 */
	public double[] getPopArr()
	{
		return pop;
	}
	
	/**
	 * Function that gets the array containing forest area % for the country during the specified time
	 * @return returns the forest % array
	 */
	public double[] getForestArr()
	{
		return forest;
	}
	
	/**
	 * Function that gets the array containing GDP per capita for the country during the specified time
	 * @return returns the GDP array
	 */
	public double[] getGDPArr()
	{
		return GDP;
	}
	
	/**
	 * Function that gets the array containing CO2 emissions per capita for the country during the specified time
	 * @return returns the CO2 per capita array
	 */
	public double[] getCO2Arr()
	{
		return CO2;
	}
	
	/**
	 * Function that gets the array containing energy use per capita for the country during the specified time
	 * @return returns the energy use per capita array
	 */
	public double[] getEnergyArr()
	{
		return energy;
	}
}
