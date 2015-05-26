import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class represents a bird in my Flappy Bird game.
 * @author Kesav Viswanadha
 * @version 1.8
 * @lastedited May 24, 2015
*/

public class Bird implements ActionListener {

	private int y;
	private int velocity;
	private Timer fall;
	private JComponent comp;
	private boolean invincible;

	public Bird(JComponent comp) {
		y = 720;
		velocity = 0;
		this.comp = comp;
		fall = new Timer(18, this);
		invincible = false;
	}

	public void actionPerformed(ActionEvent e) {
		fall();
	}

	public void fall() {
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