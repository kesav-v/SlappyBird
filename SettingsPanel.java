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
import java.util.Arrays;

public class SettingsPanel extends JPanel implements ChangeListener {

	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private JSlider volume;
	private FlappyPanel game;
	private JComboBox defaultValues;
	private TestMainMenu menu;

	public SettingsPanel(FlappyPanel game, TestMainMenu menu) {
		setLayout(null);
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
		defaultValues = new JComboBox(loadModes());
		defaultValues.setSize(200, 40);
		defaultValues.setLocation(480, 175);
		defaultValues.addItemListener(new ToggleDefault());
		defaultValues.setBackground(Color.BLUE);
		defaultValues.setForeground(Color.WHITE);
		defaultValues.setFont(new Font("Arial", Font.PLAIN, 16));
		defaultValues.setSelectedIndex(indexOf(loadModes(), "Default"));
		add(defaultValues);
		game.setValues(parseMode("Default"));
	}

	private int indexOf(String[] arr, String val) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(val)) return i;
		}
		return -1;
	}

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

	private Object[] parseMode(String modeName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("gameModes.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No modes found");
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
			System.out.println("ERROR: No such mode exists");
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
		double initVel = Double.parseDouble(pieces.get(0));
		boolean oscilPipe = pieces.get(3).equals("true");
		boolean changeOscil = pieces.get(4).equals("true");
		boolean ghostPipe = pieces.get(5).equals("true");
		int maxOscil = Integer.parseInt(pieces.get(6));
		double mOSpeed = Double.parseDouble(pieces.get(7));
		int numStartOscil = Integer.parseInt(pieces.get(8));
		int invin = Integer.parseInt(pieces.get(9));
		boolean retro = pieces.get(10).equals("true");
		boolean exploding = pieces.get(11).equals("true");
		return new Object[] {initVel, null, null, oscilPipe, changeOscil, ghostPipe, maxOscil, mOSpeed, numStartOscil, invin, retro, exploding};
	}

	private class ToggleDefault implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			String chosen = defaultValues.getSelectedItem().toString();
			if (chosen.equals("Create new mode...")) {
				menu.show("Mode Creator");
				return;
			}
			game.setValues(parseMode(chosen));
			/**String chosen = defaultValues.getSelectedItem().toString();
			if (chosen.equals("Default"))	game.setValues(FlappyPanel.GAME_MODE.DEFAULT);
			else if (chosen.equals("Ghost"))	game.setValues(FlappyPanel.GAME_MODE.GHOST);
			else if (chosen.equals("Original"))	game.setValues(FlappyPanel.GAME_MODE.ORIGINAL);
			else if (chosen.equals("Retro"))	game.setValues(FlappyPanel.GAME_MODE.RETRO);
			else if (chosen.equals("Real Retro"))	game.setValues(FlappyPanel.GAME_MODE.REAL_RETRO);*/
		}
	}

	public void stateChanged(ChangeEvent e) {
		game.getBackgroundMusic().setVolume(volume.getValue() / 100.0);
		game.getInvinMusic().setVolume(volume.getValue() / 100.0);
	}
}