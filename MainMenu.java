import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * This class paints the main menu of the game.
 * @author Kesav Viswanadha
 * @version 2.1
 * @lastedited May 28, 2015
*/

public class MainMenu extends JPanel {

	/**
	 * Constructs a MainMenu object.
	*/

	public MainMenu() {
		setLayout(null);
	}

	/**
	 * Paints the entire panel black.
	 * @param g The Graphics object used for drawing.
	*/

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
	}
}