package statsVisualiser.gui;

public class arrayChecker 
{
	private int[] arr1;
	private double[] arr2;
	private int len;

	/**
	 * 
	 * @param array this array can be population for a given number of years
	 * @return true if the entire array contains values of 0.This means that there is no information in the array
	 * @return false if there is at least one valid data point in the array 
	 */
	public boolean checkInt(int[] array)
	{
		arr1 = array;
		len = arr1.length;
		
		//Loop through  each element in the array and return false if there is at lease one element that is not 0
		for(int i = 0; i < len; i++)
		{
			if(arr1[i] != 0)
				return false;
		}
		
		//If all elements are 0 then we return true
		return true;	
	}
	/**
	 * 
	 * @param array this array can be either CO2 per capita, forest area %, energy use per capita, or GDP per capita
	 * @return true if the entire array contains values of 0.This means that there is no information in the array
	 * @return false if there is at least one valid data point in the array 
	 */
	public boolean checkDouble(double[] array)
	{
		arr2 = array;
		len = arr2.length;
		
		//Loop through  each element in the array and return false if there is at lease one element that is not 0
		for(int i = 0; i < len; i++)
		{
			if(arr2[i] != 0)
				return false;	
		}
		
		//If all elements are 0 then we return true
		return true;
	}
}
