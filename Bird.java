import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class represents a bird in my Flappy Bird game.
 * @author Kesav Viswanadha
 * @version 2.1
 * @lastedited May 29, 2015
*/

public class Bird implements ActionListener {

	private int y; // the birds height
	private int velocity; // the birds current velocity
	private Timer fall; // the timer that makes the bird fall
	private JComponent comp; // a reference to a JComponent to which this bird will be added
	private boolean invincible; // is the bird invincible?
	private boolean exploding; // is the bird in the process of exploding?
	private int radius; // the radius of the explosion
	private int dR; // how quickly the radius is changing

	/**
	 * Constructs a Bird object/
	 * @param comp A reference to the JComponent to which this bird will be added.
	*/

	public Bird(JComponent comp) {
		y = 720;
		velocity = 0;
		this.comp = comp;
		fall = new Timer(18, this);
		invincible = false;
		exploding = false;
		radius = 0;
		dR = 0;
	}

	/**
	 * ActionEvents fired by the Timer cause the bird to fall.
	 * @param e The ActionEvent fired by the timer
	*/

	public void actionPerformed(ActionEvent e) {
		fall();
	}

	public void setExploding(boolean bool) {
		exploding = bool;
	}

	public int getRadius() {
		return radius;
	}

	public boolean isExploding() {
		return exploding;
	}

	/**
	 * Moves the bird up or down a certain amount each time it is called.
	*/

	public void fall() {
		if (exploding) {
			radius += dR;
			dR++;
			fall.setDelay(20);
			comp.repaint();
			if (radius >= 200) {
				radius = 0;
				fall.stop();
			}
			return;
		}
		y -= velocity;
		velocity -= 1;
		comp.repaint();
		if (y + 50 > comp.getHeight()) stopFalling();
	}

	public int getY() {
		return y;
	}

	public void setVelocity(int v) {
		velocity = v;
	}

	public void stopFalling() {
		if (fall.isRunning()) fall.stop();
	}

	public void startFalling() {
		if (!fall.isRunning()) fall.start();
	}

	public boolean isFalling() {
		return fall.isRunning();
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean bool) {
		invincible = bool;
	}
}