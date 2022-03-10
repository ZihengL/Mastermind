package enums;

/*
 * 
 * Description: Class containing every possible value in a Code array and it's corresponding relative image path.
 * This enum class can be alternatively used to extract information to keep a modular codebase. 
 *
 * Auteur: Zi heng Liu
 * Date: 24/02/2022
 * V 1.0
 */

public enum Colors {
	White("/res/Color_0.png"),
	Black("/res/Color_1.png"),
	Red("/res/Color_2.png"),
	Green("/res/Color_3.png"),
	Blue("/res/Color_4.png"),
	Yellow("/res/Color_5.png");

	private final String path;

	private Colors(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
