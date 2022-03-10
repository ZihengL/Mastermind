package utils;

/*
 * Description: Static class with miscellaneous general purpose methods. 
 *
 * Auteur: Zi heng Liu
 * Date: 02/03/2022
 * V 1.0 
 */
public class Toolbox {

	/*
	 * Determines if parametered String object array exists within the comparison
	 * String object array.
	 */
	public static boolean confirmInstanceOf(String object, String comparison) {
		return comparison.contains(object);
	}

	/*
	 * Finds the largest value within the array of data provided in parameter.
	 */
	public static int findLargestValue(int... values) {
		int largest = 0;

		for (int value : values)
			largest = value > largest ? value : largest;

		return largest;
	}
}
