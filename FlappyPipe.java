import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class FlappyPipe implements ActionListener {

	private int x;
	private int y; // (x, y) represents the bottom left corner of the pipe
	private int velocity;
	private static int WIDTH;
	private Color color;
	private Timer oscillator;
	private int oscillation;
	private static int HEIGHT;

	public FlappyPipe(JComponent comp, int velocity, int x) {
		switch ((int)(Math.random() * 4)) {
			case 0: color = Color.RED; break;
			case 1: color = Color.GREEN; break;
			case 2: color = Color.BLUE; break;
			case 3: color = Color.YELLOW; break;
		}
		if (Math.random() < 0.1) color = Color.GRAY;
		WIDTH = comp.getWidth();
		HEIGHT = comp.getHeight();
		this.x = x;
		y = (int)(Math.random() * (comp.getHeight() - 800)) + 400;
		this.velocity = velocity;
		oscillation = (int)(Math.random() * 5) + 1;
		oscillator = new Timer(20, this);
		oscillator.start();
		if (Math.random() > 0.5) oscillation = 0;
	}

	public void move() {
		x -= velocity;
		if (x <= -50) {
			x = WIDTH;
			switch ((int)(Math.random() * 4)) {
				case 0: color = Color.RED; break;
				case 1: color = Color.GREEN; break;
				case 2: color = Color.BLUE; break;
				case 3: color = Color.YELLOW; break;
			}
			if (Math.random() < 0.1) color = Color.GRAY;
			oscillation = (int)(Math.random() * 5) + 1;
			if (Math.random() > 0.5) oscillation = 0;
		}
	}

	public void actionPerformed(ActionEvent e) {
		y -= oscillation;
		if (y < 400 || y > HEIGHT - 400) {
			oscillation *= -1;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void incVelocity(int amt) {
		velocity += amt;
	}

	public int getVelocity() {
		return velocity;
	}

	public Color getColor() {
		return color;
	}
}