import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;

public class InstructionPanel extends JPanel {

	private JTextArea words;

	public InstructionPanel() {
		setLayout(null);
		setBackground(Color.BLUE);
		words = new JTextArea();
		add(words);
		words.setSize(800, 800);
		words.setLocation(100, 100);
		words.setFont(new Font("Arial", Font.BOLD, 14));
		words.setText("Welcome to Flappy Bird! Here's how to play:\nup arrow key - jump\nWhen you die, hit 'r' to reset\nGo through the colorful pipes to become invincible, but avoid all the other pipes!");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int w = 0; w < 256; w++) {
			g.setColor(new Color(w, w, w));
			g.fillRect(0, 1000 - 4 * w, 4 * w, 1000);
		}
	}
}