import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * This class represents a pipe in the Flappy Bird game.
 * @author Kesav Viswanadha and Ofek Gila
 * @version 1.1
 * @lastedited May 23, 2015
*/

public class FlappyPipe implements ActionListener {

	private double x;
	private double y; // (x, y) represents the bottom left corner of the pipe
	private double velocity;
	private final int WIDTH;
	private Color color;
	private Timer oscillator;
	private double oscillation;
	private final int HEIGHT;
	private boolean isInvincible;
	private int count;

	public FlappyPipe(JComponent comp, double velocity, double x) {
		setColor();
		if (Math.random() < 0.1) isInvincible = true;
		else isInvincible = false;
		WIDTH = comp.getWidth();
		HEIGHT = comp.getHeight();
		this.x = x;
		y = Math.random() * (comp.getHeight() - 800) + 400;
		this.velocity = velocity;
		oscillation = Math.random() * 5 + 1;
		oscillator = new Timer(20, this);
		oscillator.start();
		if (Math.random() > 0.5) oscillation = 0;
		count = 0;
	}

	public void move() {
		x -= velocity;
		count++;
		if (x <= -50) {
			x = WIDTH;
			setColor();
			if (Math.random() < 0.1) isInvincible = true;
			else isInvincible = false;
			oscillation = Math.random() * 5 + 1;
			if (Math.random() > 0.5) oscillation = 0;
		}
		if (isInvincible && count % 20 == 0)
			setColor();
	}

	public void setOscillation(int osc) {
		oscillation = osc;
	}

	public void setColor()	{
		switch ((int)(Math.random() * 4)) {
			case 0: color = Color.RED; break;
			case 1: color = Color.GREEN; break;
			case 2: color = Color.BLUE; break;
			case 3: color = Color.YELLOW; break;
		}
	}

	public void actionPerformed(ActionEvent e) {
		y -= oscillation;
		if (y < 400 || y > HEIGHT - 400) {
			oscillation *= -1;
		}
	}

	public int getX() {
		return (int)(x + 0.5);
	}

	public int getY() {
		return (int)(y + 0.5);
	}

	public void incVelocity(double amt) {
		velocity += amt;
	}

	public double getVelocity() {
		return velocity;
	}

	public Color getColor() {
		return color;
	}

	public boolean isInvincible()	{
		return isInvincible;
	}
}