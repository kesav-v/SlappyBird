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

public class ModeCreator extends JPanel {

	private JCheckBox[] options;
	private JTextField[] values;
	private JButton saveChanges;
	private JTextArea errorMessage;
	private JTextArea[] descriptions;

	public ModeCreator() {
		setLayout(null);
		setBackground(Color.WHITE);
		options = new JCheckBox[5];
		options[0] = new JCheckBox("Pipes move up and down?");
		options[1] = new JCheckBox("Oscillation distance not constant?");
		options[2] = new JCheckBox("Ghost pipes (pipes that disappear)?");
		options[3] = new JCheckBox("Retro mode?");
		options[4] = new JCheckBox("Bird explodes?");
		descriptions = new JTextArea[6];
		descriptions[0] = new JTextArea("Name of your new mode:");
		descriptions[1] = new JTextArea("Initial horizontal velocity of sidescroll:");
		descriptions[2] = new JTextArea("Maximum pipe oscillation:");
		descriptions[3] = new JTextArea("Maximum oscillation speed:");
		descriptions[4] = new JTextArea("Number of rounds until oscillating pipes show up:");
		descriptions[5] = new JTextArea("Approximate percentage of pipes that are invincibility items:");
		values = new JTextField[6];
		for (int i = 0; i < options.length; i++) {
			add(options[i]);
			options[i].setSize(400, 40);
			options[i].setLocation(50, 50 * i);
			options[i].setBackground(Color.WHITE);
		}
		for (int i = 0; i < values.length; i++) {
			values[i] = new JTextField();
			add(descriptions[i]);
			descriptions[i].setSize(400, 40);
			descriptions[i].setLocation(50, 100 * i + 250);
			descriptions[i].setBackground(Color.WHITE);
			descriptions[i].setEditable(false);
			add(values[i]);
			values[i].setSize(400, 40);
			values[i].setLocation(50, 100 * i + 300);
		}
		saveChanges = new JButton("SAVE CHANGES");
		add(saveChanges);
		saveChanges.setSize(400, 40);
		saveChanges.setLocation(50, 850);
		saveChanges.addActionListener(new ChangeSaver());
		errorMessage = new JTextArea("Error: Changes could not be saved.\nPlease remember that the last 5 options must all be numbers.");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBackground(Color.WHITE);
		add(errorMessage);
		errorMessage.setSize(400, 80);
		errorMessage.setLocation(50, 600);
		errorMessage.setVisible(false);
		errorMessage.setEditable(false);
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
			try {
				writeMode = new PrintWriter(new File("gameModes.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: gameModes.txt could not be opened. Stack trace:\n");
				e.printStackTrace();
				return;
			}
			double initVel = 0;
			double mOSpeed = 0;
			int maxOscil = 0;
			int numStartOscil = 0;
			int invin = 0;
			try {
				initVel = Double.parseDouble(values[1].getText());
				maxOscil = Integer.parseInt(values[2].getText());
				mOSpeed = Double.parseDouble(values[3].getText());
				numStartOscil = Integer.parseInt(values[4].getText());
				invin = Integer.parseInt(values[5].getText());
			} catch (IllegalArgumentException e) {
				errorMessage.setVisible(true);
				return;
			}
			errorMessage.setVisible(false);
			writeMode.print(removeSpaces(values[0].getText()) + " ");
			writeMode.print(initVel + " ");
			writeMode.print("null null ");
			writeMode.print(options[0].isSelected() + " ");
			writeMode.print(options[1].isSelected() + " ");
			writeMode.print(options[2].isSelected() + " ");
			writeMode.print(mOSpeed + " ");
			writeMode.print(maxOscil + " ");
			writeMode.print(numStartOscil + " ");
			writeMode.print(invin + " ");
			writeMode.print(options[3].isSelected() + " ");
			writeMode.println(options[4].isSelected());
			writeMode.print(s);
			writeMode.close();
			scan.close();
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

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ModeCreator());
		frame.setVisible(true);
		frame.setSize(2160, 1440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
}