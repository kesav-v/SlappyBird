import javax.swing.JFrame;

/**
 * This is the main class that runs the Flappy Bird game.
 * @author Kesav Viswanadha
 * @version 1.1
 * @lastedited May 23, 2015
*/

public class FlappyBird extends JFrame {
	
	public FlappyBird() {
		super("Flappy Bird");
	}

	public static void main(String[] args) {
		FlappyBird fw = new FlappyBird();
		fw.setSize(2160, 1440);
		fw.setVisible(true);
		fw.getContentPane().add(new FlappyPanel());
		fw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fw.setResizable(false);
	}
}