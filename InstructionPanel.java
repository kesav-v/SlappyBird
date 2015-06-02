import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Toolkit;

/**
 * This class is a panel that displays some information
 * about the game to the user.
 * @author Kesav Viswanadha
 * @version 2.1
 * @lastedited May 28, 2015
*/

public class InstructionPanel extends JPanel {

	private JTextArea words; // the words the describe the game

	public InstructionPanel() {
		setLayout(null); // so that buttons can be added from TestMainMenu
		words = new JTextArea();
		add(words);
		words.setSize(2160, 900);
		words.setLocation(100, 100);
		words.setFont(new Font("Arial", Font.BOLD, 48));
		words.setText("Welcome to Flappy Bird! Here's how to play:\n\nup arrow key or space bar - jump\n\nWhen you die, hit 'r' to reset\n\nCollect the apples to become invincible, but avoid all the other pipes!");
		words.setEditable(false);
	}

	@Override

	/**
	 * Paints the message onto the panel.
	 * @param g The Graphics object used to paint the components.
	*/
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
	}
}