import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class represents the panel where the user can toggle/change certain settings with the game.
 * @author Kesav Viswanadha
 * @contributor Ofek Gila
 * @version 2.1
 * @lastedited June 1, 2015
*/

public class SettingsPanel extends JPanel implements ChangeListener {

	private JSlider volume; // the slider that changes the volume of the background music
	private FlappyPanel game; // a reference to the panel object which is the game
	private JComboBox<String> defaultValues; // the list of choices between different game modes
	private TestMainMenu menu; // a reference to the main menu object of the game
	private boolean first; // to handle the special case where itemStateChanged is called for the first time

	/**
	 * Constructs a SettingsPanel object.
	 * Adds all components and such.
	 * @param game A reference to the panel where the game is played.
	 * @param menu A reference to the main menu of the game.
	*/

	public SettingsPanel(FlappyPanel game, TestMainMenu menu) {
		setLayout(null);
		first = true;
		setBackground(Color.BLUE);
		this.game = game;
		this.menu = menu;
		volume = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		add(volume);
		volume.setSize(800, 50);
		volume.setLocation(680, 200);
		volume.setMajorTickSpacing(25);
		volume.setMinorTickSpacing(5);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		volume.setBackground(Color.BLUE);
		volume.setForeground(Color.WHITE);
		volume.addChangeListener(this);
		defaultValues = new JComboBox<String>(loadModes());
		defaultValues.setSize(200, 40);
		defaultValues.setLocation(480, 175);
		defaultValues.addItemListener(new ToggleDefault());
		defaultValues.setBackground(Color.BLUE);
		defaultValues.setForeground(Color.WHITE);
		defaultValues.setFont(new Font("Arial", Font.PLAIN, 16));
		defaultValues.setSelectedItem("DefaultEASY");
		add(defaultValues);
		game.setValues(parseMode("Default"));
	}

	public JComboBox<String> getComboBox() {
		return defaultValues;
	}

	/**
	 * This method gets all the names of the modes from the text file gameModes.txt.
	 * @return An array of mode names.
	*/

	private String[] loadModes() {
		Scanner scan = null;
		File gameModes = new File("gameModes.txt");
		if (!gameModes.exists())	{
			System.err.println("Cannot find file: gameModes.txt");
			System.exit(2);
		}
		try {
			scan = new Scanner(gameModes);
		} catch (FileNotFoundException e) {
			return new String[] {"(No modes found)"};
		}
		int cnt = 0;
		while (scan.hasNext()) {
			scan.nextLine();
			cnt++;
		}
		String[] modes = new String[cnt + 1];
		try {
			scan = new Scanner(new File("gameModes.txt"));
		} catch (FileNotFoundException e) {
			return new String[] {"(No modes found)"};
		}
		cnt = 0;
		while (scan.hasNext()) {
			modes[cnt] = scan.next();
			cnt++;
			scan.nextLine();
		}
		modes[cnt] = "Create new mode...";
		return modes;
	}

	/**
	 * This method takes a single line out of the text file gameModes.txt,
	 * and parses the string to convert into a game mode, which is an array of Objects.
	 * It does this by reading in the individual tokens and assigning values based on what
	 * each token says.
	 * @param modeName The name of the mode to be parsed
	 * @return An array of objects representing all the properties of this game mode.
	*/

	private Object[] parseMode(String modeName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("gameModes.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Cannot find file gameModes.txt");
			System.exit(2);
		}
		String theMode = null;
		while (scan.hasNext()) {
			String name = scan.next();
			if (name.equals(modeName)) {
				theMode = scan.nextLine();
				break;
			}
		}
		if (theMode == null) {
			System.out.println("ERROR: Cannot find mode " + modeName + " in gameModes.txt");
			System.exit(1);
		}
		Scanner getParts = new Scanner(theMode);
		ArrayList<String> pieces = new ArrayList<String>();
		while (getParts.hasNext()) {
			pieces.add(getParts.next());
		}
		if (pieces.size() != 12) {
			System.out.println("ERROR: This mode is incomplete");
			System.exit(1);
		}
		double initVel             = Double.parseDouble(pieces.get(0));
		double chanceofoscillating = Double.parseDouble(pieces.get(3));
		boolean changeOscil        = Boolean.parseBoolean(pieces.get(4));
		boolean ghostPipe          = Boolean.parseBoolean(pieces.get(5));
		int maxOscil               = Integer.parseInt(pieces.get(6));
		double mOSpeed             = Double.parseDouble(pieces.get(7));
		int numStartOscil          = Integer.parseInt(pieces.get(8));
		int invin                  = Integer.parseInt(pieces.get(9));
		boolean retro              = Boolean.parseBoolean(pieces.get(10));
		boolean exploding          = Boolean.parseBoolean(pieces.get(11));
		return new Object[] {initVel, null, null, chanceofoscillating, changeOscil, ghostPipe, maxOscil, mOSpeed, numStartOscil, invin, retro, exploding};
	}

	/**
	 * This nested class handles the combo box and changes the mode of game play as necessary.
	*/

	private class ToggleDefault implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (first) { // to make sure there are no NullPointerExceptions
				first = false;
				return;
			}
			String chosen = defaultValues.getSelectedItem().toString();
			if (chosen.equals("Create new mode...")) {
				menu.show("Mode Creator");
				return;
			}
			game.setValues(parseMode(chosen));
		}
	}

	/**
	 * Changes the volume of the music to whatever the user puts it at.
	 * @param e The ChangeEvent fired by the JSlider.
	*/

	public void stateChanged(ChangeEvent e) {
		game.getBackgroundMusic().setVolume(volume.getValue() / 100.0);
		game.getInvinMusic().setVolume(volume.getValue() / 100.0);
	}
}