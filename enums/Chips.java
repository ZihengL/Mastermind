package enums;

/*
 * Description: Class containing image paths for every possible value a confirmation chip can have. 
 * This enum can be alternatively used to extract information for the codebase to maintain modularity.
 *
 * Auteur: Zi heng Liu
 * Date: 25/02/2022
 * V 1.0 
 */
public enum Chips {
	Empty("/res/Chips_0.png", "None"),
	Yellow("/res/Chips_1.png", "Color"),
	Red("/res/Chips_2.png", "Both");

	private final String path;
	private final String match;

	private Chips(String path, String match) {
		this.path = path;
		this.match = match;
	}

	public String getPath() {
		return this.path;
	}

	public String getMatch() {
		return this.match;
	}
}
