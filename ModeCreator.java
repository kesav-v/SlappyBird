import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import javax.swing.JTextArea;
import java.awt.Font;

public class ModeCreator extends JPanel {

	private JCheckBox[] options;
	private JTextField[] values;
	private JButton saveChanges;
	private JTextArea errorMessage;
	private JTextArea[] descriptions;
	private SettingsPanel settings;
	private Font universal;

	public ModeCreator(SettingsPanel settings) {
		universal = new Font("Arial", Font.PLAIN, 36);
		this.settings = settings;
		setLayout(null);
		setBackground(Color.WHITE);
		options = new JCheckBox[4];
		options[0] = new JCheckBox("Oscillation distance not constant?");
		options[1] = new JCheckBox("Ghost pipes (pipes that disappear)?");
		options[2] = new JCheckBox("Retro mode?");
		options[3] = new JCheckBox("Bird explodes?");
		descriptions = new JTextArea[7];
		descriptions[0] = new JTextArea("Name of your new mode:");
		descriptions[1] = new JTextArea("Initial horizontal velocity of sidescroll:");
		descriptions[2] = new JTextArea("Chance that pipes will oscillate when they are able to:");
		descriptions[3] = new JTextArea("Maximum pipe oscillation:");
		descriptions[4] = new JTextArea("Maximum oscillation speed:");
		descriptions[5] = new JTextArea("Number of rounds until oscillating pipes show up:");
		descriptions[6] = new JTextArea("Approximate percentage of pipes that are invincibility items:");
		values = new JTextField[7];
		for (int i = 0; i < options.length; i++) {
			add(options[i]);
			options[i].setSize(2060, 40);
			options[i].setLocation(50, 50 * i);
			options[i].setBackground(Color.WHITE);
			options[i].setFont(universal);
		}
		for (int i = 0; i < values.length; i++) {
			values[i] = new JTextField();
			add(descriptions[i]);
			descriptions[i].setSize(2060, 40);
			descriptions[i].setLocation(50, 100 * i + 200);
			descriptions[i].setBackground(Color.WHITE);
			descriptions[i].setEditable(false);
			descriptions[i].setFont(universal);
			add(values[i]);
			values[i].setSize(2060, 40);
			values[i].setLocation(50, 100 * i + 250);
			values[i].setFont(new Font("Arial", Font.PLAIN, 20));
		}
		saveChanges = new JButton("SAVE CHANGES");
		add(saveChanges);
		saveChanges.setSize(2060, 40);
		saveChanges.setLocation(50, 900);
		saveChanges.addActionListener(new ChangeSaver());
		errorMessage = new JTextArea("Error: Changes could not be saved.\nPlease remember that the last 5 options must all be numbers.");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBackground(Color.WHITE);
		add(errorMessage);
		errorMessage.setSize(2060, 100);
		errorMessage.setLocation(50, 1000);
		errorMessage.setVisible(false);
		errorMessage.setEditable(false);
		errorMessage.setFont(universal);
	}

	private class ChangeSaver implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String s = "";
			Scanner scan = null;
			try {
				scan = new Scanner(new File("gameModes.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: gameModes.txt could not be opened. Stack trace:\n");
				e.printStackTrace();
				return;
			}
			while (scan.hasNext()) {
				s += scan.nextLine() + "\n";
			}
			PrintWriter writeMode = null;
			double initVel = 0;
			double chanceofoscillating = 0;
			double mOSpeed = 0;
			int maxOscil = 0;
			int numStartOscil = 0;
			int invin = 0;
			try {
				initVel = Double.parseDouble(values[1].getText());
				chanceofoscillating = Double.parseDouble(values[2].getText());
				maxOscil = Integer.parseInt(values[3].getText());
				mOSpeed = Double.parseDouble(values[4].getText());
				numStartOscil = Integer.parseInt(values[5].getText());
				invin = Integer.parseInt(values[6].getText());
			} catch (IllegalArgumentException e) {
				errorMessage.setVisible(true);
				System.out.println("Illegal argument exception");
				return;
			}
			try {
				writeMode = new PrintWriter(new File("gameModes.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: gameModes.txt could not be opened. Stack trace:\n");
				e.printStackTrace();
				return;
			}
			errorMessage.setVisible(false);
			writeMode.print(removeSpaces(values[0].getText()) + " ");
			writeMode.print(initVel + " ");
			writeMode.print("null null ");
			writeMode.print(chanceofoscillating + " ");
			writeMode.print(options[0].isSelected() + " ");
			writeMode.print(options[1].isSelected() + " ");
			writeMode.print(maxOscil + " ");
			writeMode.print(mOSpeed + " ");
			writeMode.print(numStartOscil + " ");
			writeMode.print(invin + " ");
			writeMode.print(options[2].isSelected() + " ");
			writeMode.println(options[3].isSelected());
			writeMode.print(s);
			writeMode.close();
			scan.close();
			settings.getComboBox().addItem(removeSpaces(values[0].getText()));
			settings.getComboBox().setSelectedItem(removeSpaces(values[0].getText()));
			clear();
		}
	}

	public void clear() {
		for (JTextField jtf : values) {
			jtf.setText("");
		}
		for (JCheckBox jcb : options) {
			jcb.setSelected(false);
		}
	}

	private String removeSpaces(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				s = s.substring(0, i) + s.substring(i + 1);
				i--;
			}
		}
		return s;
	}
}