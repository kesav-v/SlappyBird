import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * This class represents a pipe in the Flappy Bird game.
 * @author Kesav Viswanadha
 * @contributor Ofek Gila
 * @version 2.1
 * @lastedited June 1, 2015
*/

public class FlappyPipe implements ActionListener {

	private double x;
	private double y; // (x, y) represents
	private double velocity; // how quickly the pipe sidescrolls
	private final int WIDTH; // the width of the pipe, which is 50 pixels
	private Color color; // the color of the pipe /(for retro mode)
	private Timer oscillator; // handles the oscillation animation
	private double oscillation; // how quickly the pipe oscillates vertically
	private final int HEIGHT; // the height of the pipe, randomly generated each time
	private boolean isInvincible; // is the pipe an invincibility item?
	private int resets; // the number of times the pipe has wrapped around the screen
	private boolean redding; // is the color of the pipe fading into red?
	private boolean blueing; // is the color of the pipe fading into blue?
	private boolean greening; // is the color of the pipe fading into green?
	private boolean reset; // has the pipe just reset?
	private int score; // the score that this pipe has given the user
	private int topOscilDist, botOscilDist; // the two points where the pipes change vertical direction
	private boolean changeOscil; // whether or not the oscillation distance is constant
	private double chanceofoscillating;
	private final int numPipe; // the number, or index, represented by this pipe
	private int invinTime; // a random value used to generate invincibility items
	private boolean isVisible; // can the pipe be seen?
	private boolean isGhost; // is this a ghost pipe, so it disappears?
	private int maxOscillation; // the maximum oscillation distance of the pipe
	private double maxOscilSpeed; // the maximum speed at which the pipe oscillates
	private int numStartOscil; // the number of screenfuls of pipes before the oscillating pipes show up
	private int roundsTillInvin; // how often a pipe shows up

	/**
	 * Constructs a FlappyPipe object.
	 * @param comp The JComponent to which this FlappyPipe was added.
	 * @param values The defining values that will set the properties of this pipe.
	*/

	public FlappyPipe(JComponent comp, Object... values) {
		this.velocity = (double)values[0];
		this.x = (int)values[1];
		this.numPipe = (int)values[2];
		this.chanceofoscillating = (double)values[3];
		if (chanceofoscillating > 1)	chanceofoscillating /= 100;
		this.changeOscil = (boolean)values[4];
		this.isGhost = (boolean)values[5];
		this.maxOscillation = (int)values[6];
		this.maxOscilSpeed = (double)values[7];
		this.numStartOscil = (int)values[8];
		this.roundsTillInvin = (int)values[9];
		WIDTH = comp.getWidth();
		HEIGHT = comp.getHeight();
		Object testbool = true;
		resets = 0;
		isVisible = true;
		isInvincible = true;
		oscillator = new Timer(20, this);
		if (chanceofoscillating > 0 || isInvincible)
			oscillator.start();
		y = Math.random() * (comp.getHeight() - 800) + 400;
		reset();
	}

	/**
	 * This is the method that is called everytime a new pipe is created.
	 * It does the following:
	 * 
	 * - Sets the color of the pipe (for retro mode)
	 * - Decides whether the pipe is invincible or not.
	 * - Finds the starting point for color fading (retro mode)
	 * - figures out the oscillation speed and top/bottom distance
	 * - increments the number of resets
	*/

	private void reset()	{
		setColor();
		if (oscillator.isRunning())	oscillator.stop();
		if (isInvincible)	{
			isInvincible = false;
			invinTime = (int)(Math.random() * 2);
		}
		else if (1 + roundsTillInvin * numPipe + invinTime == resets % (roundsTillInvin * 4))
			isInvincible = true;
		if (((chanceofoscillating > 0 && resets >= numStartOscil) || isInvincible))	oscillator.start();
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
		isVisible = true;
		if (Math.random() > chanceofoscillating) oscillation = 0;
		else oscillation = Math.random() * maxOscilSpeed + 1;
		if (isInvincible && chanceofoscillating != 0) oscillation = 10;
		newOscilDist();
		resets++;
	}

	/**
	 * This method calculates random oscillation top/bottom boundaries.
	*/

	private void newOscilDist()	{
		if (changeOscil)	{
			topOscilDist = maxOscillation / 2 - (int)(Math.random() * 50);
			botOscilDist = maxOscillation / 2 - (int)(Math.random() * 50);
		}
		else topOscilDist = botOscilDist = maxOscillation / 2;
	}

	/**
	 * This method moves the pipe a certain amount of pixels to the left.
	 * A Timer is used on this method to create the animation of sidescrolling.
	*/

	public void move() {
		x -= velocity;
		if (x <= -50) {
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

	/**
	 * This method slightly alters the color of the pipe.
	 * Red pipes fade into green pipes, green into blue, and blue back to red.
	 * A Timer is used on this method to create the animation of a pipe that gradually changes color.
	*/

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

	/**
	 * Randomly chooses a color for the pipe between
	 * red, blue, green, and yellow.
	*/

	public void setColor() {
		switch ((int)(Math.random() * 4)) {
			case 0: color = Color.RED; break;
			case 1: color = Color.BLUE; break;
			case 2: color = Color.YELLOW; break;
			case 3: color = Color.GREEN; break;
		}
	}

	/**
	 * This method moves the pipe up or down a certain amount.
	 * A Timer is used on this method to create the animation of oscillating pipes.
	*/

	public void actionPerformed(ActionEvent e) {
		y -= oscillation;
		if ((y < HEIGHT / 2 - botOscilDist && oscillation > 0) || (y > HEIGHT / 2 + topOscilDist - 100 && oscillation < 0)) {
			if (isInvincible) {
				if (oscillation > 0) oscillation = -(Math.random() * 10 + 5);
				else oscillation = (Math.random() * 10 + 5);
				newOscilDist();
			} else oscillation *= -1;
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean bool) {
		isVisible = bool;
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
