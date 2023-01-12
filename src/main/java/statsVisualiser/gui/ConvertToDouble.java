package statsVisualiser.gui;

public class ConvertToDouble
{
	/**
	 * This function is used to convert the value of the population from an array of integers to an array of doubles
	 * @param array population array consisting of integers
	 * @return opulation array consisting of doubles
	 */
	public static double[] convert(int[] array) 
	{
		//Creates a new array that is the same length as the old one
		double[] doubleConversion = new double[array.length];
		
		//Loop through  each element in the array and save the value as a double
		for(int i = 0; i < array.length; i++)
		{
			doubleConversion[i] = array[i];
		}
		return doubleConversion;
	}
}
