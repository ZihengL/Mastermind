package objects;

import enums.Colors;

/*
 * 
 * Description: Class simulating the color and chip codes in a game of Mastermind with the usage of arrays.
 *
 * Auteur: Zi heng Liu
 * Date: 23/02/2022
 * V 2.0
 */
public class Code {
	private final int CODE_LENGTH = 4;

	private int[] code;

	/* NOTE CONSTRUCTORS */

	public Code() {
		this.code = new int[CODE_LENGTH];
	}

	/* NOTE GETTERS */

	/*
	 * Returns Code as an Integer array.
	 */
	public int[] getCode() {
		return this.code;
	}

	/*
	 * Returns the integer value of the array at the specified index.
	 */
	public int getValueOf(int index) {
		return this.code[index];
	}

	/* NOTE SETTERS */

	public void setCode(int[] code) {
		this.code = code;
	}

	/*
	 * Sets the array value at index to the specified parameter value.
	 */
	public void setValueTo(int index, int value) {
		this.code[index] = value;
	}

	/* NOTE METHODS */

	/*
	 * Returns true if the value of the code at index and the cypher at index are
	 * identical.
	 */
	public boolean compareTo(Code cypher, int cypherIndex, int playerIndex) {
		return this.code[playerIndex] == cypher.getValueOf(cypherIndex);
	}

	/* NOTE Chips Reserved Methods */

	/*
	 * Compares and counts the amount of matching elements in the array. A color
	 * match as well as a position match will each increment the indexed variable by
	 * 1.
	 */
	public void countValidChips(Code player, Code cypher) {
		boolean[] confirmedIndex = new boolean[CODE_LENGTH];

		for (int i = 0; i < player.getCode().length; i++)
			for (int j = 0; j < player.getCode().length && this.code[i] == 0; j++)
				if (!confirmedIndex[j] && player.compareTo(cypher, j, i)) {
					setValueTo(i, player.compareTo(cypher, j, j) ? 2 : 1);
					confirmedIndex[j] = true;
				}
	}

	/*
	 * Sorts the code in descending order with bubble sorting method.
	 */
	public void sortChips() {
		int temp = 0;

		for (int i = 0; i < this.code.length; i++)
			for (int j = 0; j < this.code.length; j++)
				if (i + 1 < CODE_LENGTH && this.code[i] < this.code[i + 1]) {
					temp = this.code[i];
					this.code[i] = this.code[i + 1];
					code[i + 1] = temp;
				}
	}

	/* NOTE toString Methods */

	/*
	 * Converts each integer into it's String equivalent before returning it. By
	 * default, the code uses integers as the basis for it's logic and only converts
	 * it to readable Strings as needed by the user.
	 */
	@Override
	public String toString() {
		String message = "";

		for (int i = 0; i < this.code.length; i++)
			message += Colors.values()[this.code[i]] + " ";

		return message;
	}
}
