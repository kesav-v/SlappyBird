import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

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
		int width = getWidth();
		int height = getHeight();
		for (int i = 0; i < 255; i++) {
			g.setColor(new Color(i, i, i));
			g.fillRect(i * width / 255, i * height / 255, width - (2 * i * width / 255), height - (2 * i * height / 255));
		}
		for (int i = 0; i < 255; i++) {
			g.setColor(new Color(i, i, i));
			g.fillRect(i * width / 255 + 4, i * height / 255 + 4, width - (2 * i * width / 255), height - (2 * i * height / 255));
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Georgia", Font.BOLD, 108));
		g.drawString("Flappy Bird", 780, 140);
	}
}
