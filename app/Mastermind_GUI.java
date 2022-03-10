package app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import enums.Chips;
import enums.Colors;
import enums.Interface;
import objects.Mastermind;
import utils.Toolbox;

/*
 * 
 * Description: Class emulating a game of Mastermind using the ensemble of subclasses in the project.
 *
 * Auteur: Zi heng Liu
 * Date: 03/03/2022
 * V 2.0
 */
public class Mastermind_GUI extends JFrame {
	/**
	 * NOTE: What is this???
	 */
	private static final long serialVersionUID = 2458586688596203765L;

	private Mastermind game;

	private JPanel contentPane;
	private final JButton btnSend = new JButton("CONFIRM");

	private JLabel[][] colorLabels;
	private JLabel[][] chipLabels;
	private JLabel[] cypherLabels;
	private JLabel[] hiddenLabels;

	private JComboBox<ImageIcon>[] playerChoices;

	private ImageIcon[] interfaceIcons;
	private ImageIcon[] colorIcons;
	private ImageIcon[] chipIcons;
	private final JButton btnCheat = new JButton("Cheat");
	private final JButton btnRestart = new JButton("Restart");

	/* NOTE LAUNCHER */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Mastermind_GUI frame = new Mastermind_GUI();
					frame.setVisible(true);
					frame.setTitle("Mastermind V2.0 - Zi heng Liu ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* NOTE CONSTRUCTOR */

	/*
	 * NOTE: Interface code for images is janky and messy, no time to optimize it..
	 * Methods don't accept enums as a parameter variable for some reason.
	 */
	public Mastermind_GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 2000, 900);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnSend.addActionListener(new BtnSendActionListener());
		btnSend.setBackground(new Color(128, 0, 0));
		btnSend.setForeground(new Color(255, 255, 255));
		btnSend.setFont(new Font("Bahnschrift", Font.BOLD, 24));
		btnSend.setSize(150, 75);
		btnSend.setLocation((this.getWidth() / 2) - (btnSend.getWidth() / 2), this.getHeight() - (btnSend.getHeight() * 2));
		contentPane.add(btnSend);

		btnCheat.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
		btnCheat.addActionListener(new BtnCheatActionListener());
		btnCheat.setForeground(Color.WHITE);
		btnCheat.setBackground(Color.DARK_GRAY);
		btnCheat.setBounds(1840, 800, 100, 25);
		contentPane.add(btnCheat);

		btnRestart.addActionListener(new BtnRestartActionListener());
		btnRestart.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
		btnRestart.setForeground(Color.WHITE);
		btnRestart.setBackground(Color.DARK_GRAY);
		btnRestart.setBounds(40, 800, 100, 25);
		contentPane.add(btnRestart);

		// Instanciating a Mastermind object.
		game = new Mastermind();

		// Instanciating all ImageIcons.
		interfaceIcons = new ImageIcon[Interface.values().length];
		colorIcons = new ImageIcon[Colors.values().length];
		chipIcons = new ImageIcon[Chips.values().length];

		// Loads all images.
		loadImageIcons();

		// Instanciating all JLabels.
		colorLabels = new JLabel[game.getMaxAttempts()][game.getPlayerCode().length];
		chipLabels = new JLabel[game.getMaxAttempts()][game.getPlayerCode().length];
		cypherLabels = new JLabel[game.getCypher().length];
		hiddenLabels = new JLabel[game.getPlayerCode().length];

		// Sets and places all JLabels to their default position and state.
		initiateLabels();

		// Instanciates player inputs method with JComboBoxes.
		// NOTE: Still have some warning errors.
		playerChoices = new JComboBox[game.getPlayerCode().length];

		// Sets all JComboBoxes.
		setComboBoxes();

	}

	/* NOTE INTERACTIVE UI ELEMENTS */

	/*
	 * Sends the currently set input in the JComboBox values as the current attempt.
	 */
	private class BtnSendActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateColorLabels();
			sendAttempt();
			game.setChips();
			updateChipLabels();

			if (game.isVictory()) {
				btnSend.setEnabled(false);
				if (!cypherLabels[0].isVisible()) {
					setCypherVisibility(true);
					JOptionPane.showMessageDialog(rootPane, "Wow, such amazing!\nYou completed this match in " + (game.getAttempt() + 1) + " tries.");
				} else
					JOptionPane.showMessageDialog(rootPane, "C-H-E-A-T-E-R");
			} else if (game.isGameOver()) {
				btnSend.setEnabled(false);
				JOptionPane.showMessageDialog(rootPane, "You lost.\n*Sad trumpet noises*");
			} else {
				game.nextAttempt();
				setDefaultComboBoxes();
			}
		}

		/*
		 * Takes the selected value at each JComboBox and sets it as the current attempt
		 * on colorLabels.
		 */
		private void updateColorLabels() {
			for (int i = 0; i < game.getPlayerCode().length; i++) {
				colorLabels[game.getAttempt()][i].setIcon(colorIcons[playerChoices[i].getSelectedIndex()]);
				setToolTip(colorLabels[game.getAttempt()][i]);
			}
		}

		/*
		 * Gets defined and sorted values of confirmation chips from the game and
		 * interprets them with red(2), yellow(1) or black(0) icons.
		 */
		private void updateChipLabels() {
			int[] result = game.getChips();

			for (int i = 0; i < game.getChips().length; i++) {
				chipLabels[game.getAttempt()][i].setIcon(chipIcons[result[i]]);
				chipLabels[game.getAttempt()][i].setToolTipText(Chips.values()[game.getChips()[i]].getMatch());
			}
		}

		/*
		 * Sends the current attempt from information taken from the updated icons.
		 */
		private void sendAttempt() {
			for (int i = 0; i < game.getPlayerCode().length; i++) {
				if (findIconValue(colorLabels[game.getAttempt()][i].getToolTipText()) != -1)
					game.setPlayerCode(i, findIconValue(colorLabels[game.getAttempt()][i].getToolTipText()));
				else {
					JOptionPane.showMessageDialog(rootPane, "Error, unknown value at index " + i + " of attempt number " + game.getAttempt() + ".");
					break;
				}
			}
		}
	}

	/*
	 * Sets visibility of the cypher and hidden labels to false or true depending on
	 * their current state.
	 */
	private class BtnCheatActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setCypherVisibility(cypherLabels[0].isVisible() ? false : true);
		}
	}

	/*
	 * Restarts the game and sets all labels back to their default state.
	 */
	private class BtnRestartActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			resetLabels(game.getAttempt() + 1);
			game.resetGame();
			setCypherVisibility(false);
			setDefaultComboBoxes();
			btnSend.setEnabled(true);
		}
	}

	/* NOTE METHODS */

	/*
	 * Instanciates and loads all images.
	 */
	private void loadImageIcons() {
		for (int i = 0; i < Toolbox.findLargestValue(interfaceIcons.length, colorIcons.length, chipIcons.length); i++) {
			if (i < interfaceIcons.length)
				interfaceIcons[i] = new ImageIcon(this.getClass().getResource(Interface.values()[i].getPath()));
			if (i < colorIcons.length)
				colorIcons[i] = new ImageIcon(this.getClass().getResource(Colors.values()[i].getPath()));
			if (i < chipIcons.length)
				chipIcons[i] = new ImageIcon(this.getClass().getResource(Chips.values()[i].getPath()));
		}
	}

	/*
	 * Sets the JLabel size based on the dimensions of it's assigned image.
	 */
	private void setLabelSize(JLabel label) {
		label.setSize(label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
	}

	/*
	 * Instanciates and sets all labels to their default format.
	 */
	// NOTE: Code is messy and janky, no time to fix...
	private void initiateLabels() {

		for (int i = 0; i < game.getMaxAttempts(); i++)
			for (int j = 0; j < Toolbox.findLargestValue(colorLabels[i].length, chipLabels[i].length, cypherLabels.length,
					hiddenLabels.length); j++) {
				if (j < colorLabels[i].length) {
					colorLabels[i][j] = new JLabel(interfaceIcons[Interface.Empty.ordinal()]);
					setLabelSize(colorLabels[i][j]);
					colorLabels[i][j].setLocation((colorLabels[i][j].getWidth() + 100) * i + 50, (colorLabels[i][j].getHeight() + 50) * j + 50);
					colorLabels[i][j].setToolTipText(Interface.Empty.toString());
					contentPane.add(colorLabels[i][j]);
				}
				if (j < chipLabels[i].length) {
					chipLabels[i][j] = new JLabel(chipIcons[Chips.Empty.ordinal()]);
					setLabelSize(chipLabels[i][j]);
					chipLabels[i][j].setLocation((colorLabels[i][j].getWidth() + 100) * i + 150, 215 + (j * 35));
					chipLabels[i][j].setToolTipText(Chips.Empty.toString());
					contentPane.add(chipLabels[i][j]);
				}
				if (i == game.getMaxAttempts() - 1) {
					if (j < cypherLabels.length) {
						cypherLabels[j] = new JLabel(colorIcons[game.getCypher()[j]]);
						setLabelSize(cypherLabels[j]);
						cypherLabels[j].setLocation(getWidth() - cypherLabels[j].getWidth() - 75, (cypherLabels[j].getHeight() + 50) * j + 50);
						cypherLabels[j].setVisible(false);
						setToolTip(cypherLabels[j]);
						contentPane.add(cypherLabels[j]);
					}
					if (j < hiddenLabels.length) {
						hiddenLabels[j] = new JLabel(interfaceIcons[Interface.Hidden.ordinal()]);
						setLabelSize(hiddenLabels[j]);
						hiddenLabels[j].setLocation(getWidth() - hiddenLabels[j].getWidth() - 75, (hiddenLabels[j].getHeight() + 50) * j + 50);
						hiddenLabels[j].setToolTipText(Interface.Hidden.toString());
						contentPane.add(hiddenLabels[j]);
					}
				}
			}
	}

	/*
	 * Sets all labels back to their default state.
	 */
	private void resetLabels(int attempts) {
		for (int i = 0; i < attempts; i++)
			for (int j = 0; j < Toolbox.findLargestValue(colorLabels[i].length, chipLabels[i].length, cypherLabels.length); j++) {
				if (j < colorLabels[i].length) {
					colorLabels[i][j].setIcon(interfaceIcons[Interface.Empty.ordinal()]);
					colorLabels[i][j].setToolTipText(Interface.Empty.toString());
				}
				if (j < chipLabels[i].length) {
					chipLabels[i][j].setIcon(chipIcons[Chips.Empty.ordinal()]);
					chipLabels[i][j].setToolTipText(Chips.Empty.toString());
				}
				if (i == game.getMaxAttempts() - 1)
					if (j < cypherLabels.length) {
						cypherLabels[j].setIcon(colorIcons[game.getCypher()[j]]);
						cypherLabels[j].setToolTipText(Colors.values()[game.getCypher()[j]].toString());
					}
			}
	}

	/*
	 * Sets the selected index of every JComboBox back to it's first index.
	 */
	private void setDefaultComboBoxes() {
		for (int i = 0; i < playerChoices.length; i++)
			playerChoices[i].setSelectedIndex(0);
	}

	/*
	 * Instanciates and sets all JComboBoxes.
	 */
	private void setComboBoxes() {
		for (int i = 0; i < playerChoices.length; i++) {
			playerChoices[i] = new JComboBox<ImageIcon>();
			playerChoices[i].setSize(100, 100);
			playerChoices[i].setLocation((getWidth() / 2 - (playerChoices[i].getWidth() / 2) - 180) + (i * 120),
					getHeight() - 300);
			playerChoices[i].setBackground(Color.DARK_GRAY);
			contentPane.add(playerChoices[i]);
			for (int j = 0; j < Colors.values().length; j++) {
				playerChoices[i].addItem(new ImageIcon(this.getClass().getResource(Colors.values()[j].getPath())));
			}
		}
	}

	/*
	 * Sets the color JLabels ToolTipText to it's corresponding text.
	 */
	private void setToolTip(JLabel label) {
		for (int i = 0; i < colorIcons.length; i++)
			if (label.getIcon().equals(colorIcons[i]))
				label.setToolTipText(Colors.values()[i].toString());
	}

	/*
	 * Finds the integer equivalent of the parameter color icon. If it isn't found,
	 * -1 is returned.
	 */
	private int findIconValue(String color) {
		for (int i = 0; i < Colors.values().length; i++)
			if (color.equals(Colors.values()[i].toString()))
				return i;

		return -1;
	}

	/*
	 * Sets the visibility of the cypher code based on parameter value.
	 */
	private void setCypherVisibility(boolean visible) {
		for (JLabel label : cypherLabels)
			label.setVisible(visible ? true : false);

		for (JLabel label : hiddenLabels)
			label.setVisible(visible ? false : true);
	}
}
