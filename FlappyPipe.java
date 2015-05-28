import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * This class represents a pipe in the Flappy Bird game.
 * @author Kesav Viswanadha
 * @contributor Ofek Gila
 * @version 1.8
 * @lastedited May 24, 2015
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
	private boolean redding;
	private boolean blueing;
	private boolean greening;
	private boolean reset;
	private int score;
	private int topOscilDist, botOscilDist;
	private boolean changeOscil = true;
	private final int numPipe;
	private int invinTime;
	private boolean visible;
	private Timer flash;
	private boolean flashing;

	public FlappyPipe(JComponent comp, double velocity, double x, int numPipe) {
		this.numPipe = numPipe;
		WIDTH = comp.getWidth();
		HEIGHT = comp.getHeight();
		count = 1;
		visible = true;
		isInvincible = true;
		reset();
		this.x = x;
		y = Math.random() * (comp.getHeight() - 800) + 400;
		this.velocity = velocity;
		oscillator = new Timer(20, this);
		oscillator.start();
		flashing = (Math.random() > 0.9);
		flash = new Timer(500, new Flash());
		if (flashing && isInvincible) flash.start();
	}

	public void reset()	{
		setColor();
		if (isInvincible)	{
			isInvincible = false;
			invinTime = (int)(Math.random() * 2);
		}
		else if (count / 2 % 4 == numPipe && count % 2 == invinTime)	isInvincible = true;
		if (isInvincible && color.equals(Color.YELLOW)) color = Color.RED;
		if (color.equals(Color.RED)) {
			redding = false;
			greening = true;
			blueing = false;
		}
		else if (color.equals(Color.GREEN)) {
			redding = false;
			greening = false;
			blueing = true;
		}
		else {
			redding = true;
			greening = false;
			blueing = false;
		}
		visible = true;
		if (Math.random() > 0.5) oscillation = 0;
		oscillation = Math.random() * 5 + 1;
		if (isInvincible) oscillation = 10;
		newOscilDist();
		flashing = (Math.random() > 0.9);
		flash = new Timer(250, new Flash());
		if (flashing && !isInvincible) flash.start();
	}

	public void newOscilDist()	{
		if (changeOscil)	{
			topOscilDist = 450 - (int)(Math.random() * 100);
			botOscilDist = 450 - (int)(Math.random() * 100);
		}
		else topOscilDist = botOscilDist = 400;
	}

	private class Flash implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			visible = !visible;
		}
	}

	public void move() {
		x -= velocity;
		count++;
		if (x <= -50) {
			flash.stop();
			if (!isInvincible)
				score++;
			reset = true;
			x = WIDTH;
			reset();
		}
		else reset = false;
		if (isInvincible)
			fadeColor();
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setOscillation(double osc) {
		oscillation = osc;
	}

	public int getScore() {
		return score;
	}

	public void fadeColor()	{
		if (redding) {
			if (color.getRed() >= 255) {
				redding = false;
				greening = true;
				blueing = false;
			}
			else color = new Color(color.getRed() + 5, 0, color.getBlue() - 5);
		}
		else if (greening) {
			if (color.getGreen() >= 255) {
				greening = false;
				blueing = true;
				redding = false;
			}
			else color = new Color(color.getRed() - 5, color.getGreen() + 5, 0);
		}
		else if (blueing) {
			if (color.getBlue() >= 255) {
				blueing = false;
				redding = true;
				greening = false;
			}
			else color = new Color(0, color.getGreen() - 5, color.getBlue() + 5);
		}
	}

	public void setColor() {
		switch ((int)(Math.random() * 4)) {
			case 0: color = Color.RED; break;
			case 1: color = Color.BLUE; break;
			case 2: color = Color.YELLOW; break;
			case 3: color = Color.GREEN; break;
		}
	}

	public void actionPerformed(ActionEvent e) {
		y -= oscillation;
		if ((y < botOscilDist && oscillation > 0) || (y > HEIGHT - topOscilDist && oscillation < 0)) {
			if (isInvincible) {
				if (oscillation > 0) oscillation = -(Math.random() * 10 + 5);
				else oscillation = (Math.random() * 10 + 5);
				newOscilDist();
			} else oscillation *= -1;
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean bool) {
		visible = bool;
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

	public void setVelocity(double vel)	{
		velocity = vel;
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

	public boolean isReset()	{
		return reset;
	}
}