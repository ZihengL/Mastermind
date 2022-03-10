package enums;

/*
 * Description: Contains path for miscellaneous interface elements of the UI class.
 *
 * Auteur: Zi heng Liu
 * Date: 03/03/2022
 * V 1.0 
 */
public enum Interface {
	Empty("/res/Color_Empty.png"),
	Hidden("/res/Color_Hidden.png");

	private final String path;

	private Interface(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
