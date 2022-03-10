package objects;

import enums.Colors;

/*
 * Description: Class simulating a game of Mastermind by utilizing and managing Codes class objects.
 *
 * Auteur: Zi heng Liu
 * Date: 23/02/2022
 * V 2.0 
 */
public class Mastermind {
	private final int MAX_ATTEMPTS = 10;

	private int attempt;

	private Code[] player;
	private Code[] chips;
	private Code cypher;

	/* NOTE CONSTRUCTORS */

	/*
	 * Player is granted an array of Chips and Codes in order to simulate the amount
	 * of tries given. A tracker is generated to follow the objects index and the
	 * cypher is given a dedicated Codes object with randomized values.
	 */
	public Mastermind() {
		this.cypher = new Code();
		resetGame();
	}

	/* NOTE GETTERS */

	/*
	 * Returns the max number of tries granted to the player before the match can be
	 * declared lost.
	 */
	public int getMaxAttempts() {
		return this.MAX_ATTEMPTS;
	}

	/*
	 * Returns player's code of attempt number as an array of integers.
	 */
	public int[] getPlayerCode() {
		return this.player[getAttempt()].getCode();
	}

	/*
	 * Returns an array of chips with values that are properly sorted and
	 * determined.
	 */
	public int[] getChips() {
		return this.chips[getAttempt()].getCode();
	}

	/*
	 * Returns the player's current attempt.
	 */
	public int getAttempt() {
		return this.attempt;
	}

	/*
	 * Returns the cypher as an array of integers.
	 */
	public int[] getCypher() {
		return this.cypher.getCode();
	}

	/* NOTE SETTERS */

	public void setPlayerCode(int[] code) {
		this.player[getAttempt()].setCode(code);
	}

	/*
	 * Sets input combination for the player's current attempt.
	 */
	public void setPlayerCode(int position, int value) {
		this.player[getAttempt()].setValueTo(position, value);
	}

	public void setChips() {
		this.chips[getAttempt()].countValidChips(this.player[getAttempt()], this.cypher);
		this.chips[getAttempt()].sortChips();
	}

	/*
	 * Set up for new attempts as the game tracks player progression through a
	 * match. Next batch of Code
	 */
	public void nextAttempt() {
		setAttempt(getAttempt() + 1);
		this.player[getAttempt()] = new Code();
		this.chips[getAttempt()] = new Code();
	}

	/*
	 * Tracks the progression of the player's guesses.
	 */
	private void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	/*
	 * Flushes out objects from memory and instanciates new ones, effectively
	 * restarting the game. Cypher Code object is kept, but values are randomized
	 * once again.
	 */
	public void resetGame() {
		setAttempt(0);
		this.player = new Code[MAX_ATTEMPTS];
		this.chips = new Code[MAX_ATTEMPTS];
		this.player[getAttempt()] = new Code();
		this.chips[getAttempt()] = new Code();
		randomize(this.cypher);
	}

	/* NOTE METHODS */

	/*
	 * Sets the Code object to random integer values with it's limits based on the
	 * constraints set by the enum Colors class.
	 */
	private void randomize(Code code) {
		for (int i = 0; i < code.getCode().length; i++)
			code.setValueTo(i, (int) Math.floor(Math.random() * Colors.values().length));
	}

	/* NOTE Game State Methods */

	/*
	 * Returns true if the player won the game.
	 */
	public boolean isVictory() {
		for (int i = 0; i < this.player[getAttempt()].getCode().length; i++)
			if (!this.player[getAttempt()].compareTo(cypher, i, i))
				return false;

		return true;
	}

	/*
	 * Returns true if attempt is equal or more to max number of tries.
	 */
	public boolean isGameOver() {
		return getAttempt() >= MAX_ATTEMPTS - 1;
	}
}
