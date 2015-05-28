import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Toolkit;

public class InstructionPanel extends JPanel {

	private JTextArea words;
	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public InstructionPanel() {
		setLayout(null);
		words = new JTextArea();
		add(words);
		words.setSize(SCREEN_WIDTH - 200, SCREEN_HEIGHT - 200);
		words.setLocation(100, 100);
		words.setFont(new Font("Arial", Font.BOLD, 48));
		words.setText("Welcome to Flappy Bird! Here's how to play:\n\nup arrow key or space bar - jump\n\nWhen you die, hit 'r' to reset\n\nCollect the apples to become invincible, but avoid all the other pipes!");
		words.setEditable(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
	}
}