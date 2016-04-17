import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * This is the bulk of the code - the game itself is displayed through this panel.
 * @author Kesav Viswanadha
 * @contributor Ofek Gila
 * @version 2.1
 * @lastedited June 2, 2015
*/

public class FlappyPanel extends JPanel implements ActionListener, KeyListener {
							
	private TestMainMenu mainMenu; // a reference to the main menu of this game
	private FlappyPipe[] pipes; // an array of the pipes that will be shown on screen
	private Timer movePipes; // This timer moves the pipes horizontally
	private boolean first; // is it the first time paintComponent is called?
	private Bird bird; // The bird that moves through the pipes in the game
	private int invincibleTimes; // the number of times the invincibility timer has been called
	private boolean firstPress; // is it the first key press?
	private ArrayList<String> previousScores; // all the user's previous scores in the game
	private boolean justDied; // did the bird just hit a pipe?
	private ImageIcon bird1, bird2; // two pictures of birds to create a flapping animation
	private ImageIcon bird1ud, bird2ud; // two upside-down pictures of birds to create a flapping animation
	private Timer invincibility; // This timer fires events while the bird is invincible
	private AudioList songs; // the playlist of background music during the game
	private AudioClip clip; // the invincibility background music
	private ImageIcon thePipe; // an image of the pipe
	private int count; // the number of times the pipes have moved
	private ImageIcon apple; // the image of the invincibility apple
	private boolean ghostPipes, changeOscil; // are there ghost pipes? does the oscillation distance change?
	private double chanceofoscillating; // the chance that a pipe oscillates
	private double initVelocity; // the initial sidescrolling speed of the pipes
	private int maxOscillation; // the maximum distance that the pipe moves up and down
	private double maxOscilSpeed; // the maximum speed with which the pipe oscillates
	private int numStartOscil; // the number of screenfuls of pipes until oscillation begins
	private int roundsTillInvin; // how far apart the invincibility apples are spaced
	private boolean upsideDown; // does the gravity reverse in this level?
	private boolean gameIsOver; // is the game finished? has the bird died?
	private boolean retro; // toggles retro mode on/off
	private boolean explode; // does the bird explode when it dies?
	private Color birdColor; // the color of the bird, which changes in retro mode
	private ImageIcon explosion; // the image of the bird's explosion
	private int numJumps; // the number of times the user jumped

	/**
	 * Constucts a new FlappyPanel object.
	  *@param mainMenu A reference to the main menu of this game.
	*/

	public FlappyPanel(TestMainMenu mainMenu) {
		this.mainMenu = mainMenu;
		setLayout(null);
		explosion = new ImageIcon(getClass().getResource("explosion.png"));
		apple = new ImageIcon(getClass().getResource("AppleImg.png"));
		clip = new AudioClip(new File("SoundEffects/MarioInvincible.mp3"));
		songs = new AudioList(AudioList.INITIAL_SHUFFLE, AudioList.CURRENT_FOLDER, new File("AllMusic"));
		songs.play();
		previousScores = new ArrayList<String>();
		movePipes = new Timer(20, this);
		invincibility = new Timer(25, new Handler());
		first = true;
		retro = false;
		explode = true;
		firstPress = true;
		count = 0;
		bird = new Bird(this);
		addKeyListener(this);
		justDied = true;
		birdColor = Color.white;
		bird1 = new ImageIcon(getClass().getResource("Bird1.png"));
		bird2 = new ImageIcon(getClass().getResource("Bird2.png"));
		bird1ud = new ImageIcon(getClass().getResource("Bird1Rotated.png"));
		bird2ud = new ImageIcon(getClass().getResource("Bird2Rotated.png"));
		thePipe = new ImageIcon(getClass().getResource("PipeCut.png"));
	}

	/**
	 * If the game is over and the users hits 'r', this method resets the game.
	*/

	public void resetGame()	{
		numJumps = 0;
		bird = new Bird(this);
		pipes = new FlappyPipe[4];
		for (int i = 0; i < pipes.length; i++) {
			pipes[i] = new FlappyPipe(this, getValues(i));
		}
		birdColor = Color.white;
		firstPress = true;
		justDied = true;
		previousScores.clear();
		gameIsOver = false;
		mainMenu.gameOver();
	}

	/**
	 * This method returns the properties of a given pipe.
	 * @param numPipe The index of the pipe for which this method gets the values.
	 * @return The properties of the pipe.
	*/

	public Object[] getValues(int numPipe)	{
		return new Object[]	{initVelocity, numPipe * (getWidth() / 4) + getWidth() / 4, numPipe, chanceofoscillating, changeOscil, ghostPipes, maxOscillation, maxOscilSpeed, numStartOscil, roundsTillInvin, upsideDown};
	}

	public AudioList getSongList() {
		return songs;
	}

	/**
	 * This method sets the properties of this game as specified.
	 * @param values The properties of the game.
	*/

	public void setValues(Object[] values)	{
		initVelocity = (double)values[0];
		chanceofoscillating = (double)values[3];
		changeOscil = (boolean)values[4];
		ghostPipes = (boolean)values[5];
		maxOscillation = (int)values[6];
		maxOscilSpeed = (double)values[7];
		numStartOscil = (int)values[8];
		roundsTillInvin = (int)values[9];
		retro = (boolean)values[10];
		explode = (boolean)values[11];
		upsideDown = (boolean)values[12];
	}

	/**
	 * This nested class handles what happens if an invincibility item is collected.
	*/

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			invincibleTimes++;
			if (!clip.isRunning()) {
				clip.play();
				songs.pause();
			}
			if (invincibleTimes == 255) {
				bird.setInvincible(false);
				invincibility.stop();
				invincibleTimes = 0;
				clip.stop();
				songs.play();
			}
		}
	}

	public AudioClip getBackgroundMusic() {
		return songs.getAudioClip();
	}

	public AudioClip getInvinMusic() {
		return clip;
	}

	/**
	 * This method paints all the things seen on screen.
	 * @param g The Graphics object used for the drawing.
	*/

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		requestFocus();
		if (first) {
			first = false;
			pipes = new FlappyPipe[4];
			for (int i = 0; i < pipes.length; i++) {
				pipes[i] = new FlappyPipe(this, getValues(i));
			}
		}
		super.paintComponent(g);
		setBackground(Color.BLACK);
		for (FlappyPipe fp : pipes) {
			if (ghostPipes && fp.getX() <= 100 + 150 && !gameIsOver && !fp.isInvincible())
				if (fp.getX() <= 100)	continue;
				else g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (fp.getX() - 100f) / 150f));	
			g.setColor(fp.getColor());
			if (fp.isVisible())
				if (fp.isInvincible())
					if (retro)	{
						g.drawOval(fp.getX()-25, fp.getY()-25, 50, 50);
						g.drawOval(fp.getX()-15, fp.getY()-15, 30, 30);
					}
					else g.drawImage(apple.getImage(), fp.getX(), fp.getY(), 50, 50, this);
				else 
					if (retro)	{
						g.drawRect(fp.getX(), 0, 50, fp.getY());
						g.drawRect(fp.getX(), fp.getY() + 200, 50, getHeight() - (fp.getY() + 200));
					}
					else if (!bird.isInvincible()) {
						g.drawImage(thePipe.getImage(), fp.getX(), 0, 50, fp.getY(), this);
						g.drawImage(thePipe.getImage(), fp.getX(), fp.getY() + 200, 50, getHeight() - (fp.getY() + 200), this);
					}
					else {
						g.setColor(Color.white);
						g.drawRoundRect(fp.getX(), 0, 50, fp.getY(), 50, 50);
						g.drawRoundRect(fp.getX(), fp.getY() + 200, 50, getHeight() - (fp.getY() + 200), 50, 50);
					}
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		g.setColor(birdColor);
		if (retro)	{
			if (bird.isExploding())	{
				g.setColor(Color.red);
				for (int radius = bird.getRadius(); radius > 0; radius -= 20)
					g.drawOval(75 - radius / 2, (int)bird.getY() + 25 - radius / 2, radius, radius);
			}
			else {
				g.drawOval(50, (int)bird.getY(), 50, 50);
				if (bird.isInvincible())	{
					int antiradius = (int)(invincibleTimes * 50f / 255);
					g.drawOval(50 + antiradius/2, (int)bird.getY() + antiradius/2, 50 - antiradius, 50 - antiradius);
				}
			}
			
		}
		else if (bird.isExploding()) {
			g.drawImage(explosion.getImage(), 75 - bird.getRadius() / 2, (int)bird.getY() + 25 - bird.getRadius() / 2, bird.getRadius(), bird.getRadius(), this);
		}
		else
			if (bird.isInvincible())
				if (count % 4 < 2) {
					if (bird.isFallingUp()) g.drawImage(bird1ud.getImage(), 50, (int)bird.getY(), 50, 50, this);
					else g.drawImage(bird1.getImage(), 50, (int)bird.getY(), 50, 50, this);
				}
				else {
					if (bird.isFallingUp()) g.drawImage(bird2ud.getImage(), 50, (int)bird.getY(), 50, 50, this);
					else g.drawImage(bird2.getImage(), 50, (int)bird.getY(), 50, 50, this);
				}
			else
				if (count % 20 < 10) {
					 if (bird.isFallingUp()) g.drawImage(bird1ud.getImage(), 50, (int)bird.getY(), 50, 50, this);
					 else g.drawImage(bird1.getImage(), 50, (int)bird.getY(), 50, 50, this);
				}
				else {
					if (bird.isFallingUp()) g.drawImage(bird2ud.getImage(), 50, (int)bird.getY(), 50, 50, this);
					else g.drawImage(bird2.getImage(), 50, (int)bird.getY(), 50, 50, this);
				}
		if (dying() || dead()) {
			movePipes.stop();
			for (FlappyPipe fp : pipes)
				fp.setVisible(true);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 96));
			g.drawString("GAME OVER!", 800, 700);
			if (justDied) {
				saveScore();
				justDied = false;
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 96));
		g.drawString("SCORE: " + sumScores(), 800, 800);
		if (bird.isFallingUp()) g.setColor(Color.RED);
		g.drawString("JUMPS: " + numJumps, 800, 900);
		if (firstPress) {
			g.setColor(new Color(0, 100, 0));
			g.drawString("GET READY!", 780, 700);
		}
	}

	/**
	 * This method calculates the sum of the scores of all the pipes, which
	 * ends up being the user's total score.
	 * @return The total score the user has so far.
	*/

	private int sumScores() {
		int sum = 0;
		for (FlappyPipe fp : pipes) {
			sum += fp.getScore();
		}
		return sum;
	}

	/**
	 * This method is called with a Timer to create the animation of sidescrolling pipes.
	 * @param e The ActionEvent fired by the Timer.
	*/

	public void actionPerformed(ActionEvent e) {
		count++;
		for (FlappyPipe fp : pipes) {
			fp.move();
		}
		if (sumScores() != 0) {
			incPipeVelocity(bird.isInvincible() ? 2:1);
		}
		repaint();
	}

	/**
	 * Increases/decreases the speed of each pipe in the game by a certain amount.
	 * @param ratio The factor by which the speed increases.
	*/

	public void incPipeVelocity(double ratio)	{
		for (FlappyPipe fp : pipes)	{
			if (ratio > 0)
				fp.incVelocity(velocityInc(fp.getVelocity()) * ratio);
			else fp.incVelocity(velocityDec(fp.getVelocity()-2) * ratio);
		}
	}

	/**
	 * Returns the velocity increase, which is a reciprocal square root function of the current velocity.
	 * @param The current velocity
	*/

	public double velocityInc(double num)	{
		return 1 / (100 * Math.sqrt(num));
	}

	/**
	 * Returns the velocity decrease, which is a square root function of the current velocity.
	 * @param The current velocity
	*/

	public double velocityDec(double num)	{
		return Math.sqrt(num) / 100;
	}

	/**
	 * When the player dies, this method saves his/her score into the scores.txt file.
	*/

	public void saveScore() {
		Scanner getScores = null;
		try {
			getScores = new Scanner(new File("scores.txt"));
		} catch (IOException e) {
			System.out.println("ERROR: scores.txt does not exist");
		}
		if (getScores != null) {
			while (getScores.hasNext()) {
				previousScores.add(getScores.nextLine());
			}
		}
		int i;
		try {
			for (i = 0; i < previousScores.size() && Integer.parseInt(previousScores.get(i).substring(0, previousScores.get(i).indexOf(" "))) > sumScores(); i++) {}
		}	catch (Exception e)	{System.err.println("Fix your score.txt files!!!"); return; }
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		previousScores.add(i, sumScores() + " on " + sdf.format(date) + " in level " + mainMenu.getSettingsPanel().getComboBox().getSelectedItem());
		PrintWriter writeScores = null;
		try {
			writeScores = new PrintWriter(new File("scores.txt"));
		} catch (IOException e) {
			System.out.println("ERROR: Could not open scores.txt for writing");
			return;
		}
		for (String s : previousScores) {
			writeScores.println(s);
		}
		writeScores.close();
	}

	/**
	 * This method handles the keyboard controls of the game.
	 * @param e The KeyEvent fired by the KeyListener.
	*/

	public void keyPressed(KeyEvent e) {
		// if the user jumps
		if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) && !dying() && !gameIsOver) {
			numJumps++;
			if (numJumps % 20 == 0 && numJumps != 0 && upsideDown) bird.reverseGravity();
			if (numJumps % 20 == 10 && numJumps != 10 && upsideDown) bird.reverseGravity();
			if (firstPress) {
				movePipes.start();
				bird.startFalling();
				firstPress = false;
			}
			if (bird.isFallingUp()) {
				bird.setVelocity(-13);
			}
			else {
				bird.setVelocity(13);
			}
			repaint();
		}
		if (e.getKeyChar() == KeyEvent.VK_ENTER && gameIsOver) { // 'r' resets the game
			resetGame();
			repaint();
		}
	}

	public boolean gameIsOver() {
		return gameIsOver;
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	/**
	 * This method figures out if a bird is dead. It is dead if one of the following conditions is satisfied:
	 * - the bird hit the ceiling.
	 * - the bird hit the ground.
	 * - the bird hit one of the pipes.
	 * @return Is the bird dead or not?
	*/

	public boolean dead() {
		if ((int)bird.getY() + 50 > getHeight() || ((int)bird.getY() < 0)) {
			gameIsOver = true;
			if (explode) {
				bird.setExploding(true);
			}
			mainMenu.gameOver();
			return true;
		}
		for (FlappyPipe fp : pipes) {
			if (fp.getX() <= 50)
				birdColor = fp.getColor();
			if (!fp.isInvincible() && fp.getX() >= 0 && fp.getX() <= 100 &&
				(fp.getY() <= (int)bird.getY() - 150 || fp.getY() >= (int)bird.getY())) {
				if (bird.isInvincible())	{
					incPipeVelocity(-1);
					return false;
				}
				else {
					gameIsOver = true;
					if (explode) {
						bird.setExploding(true);
					}
					mainMenu.gameOver();
					return true;
				}
			} else if (fp.isInvincible() && fp.getX() >= 0 && fp.getX() <= 100 && (int)bird.getY() + 50 >= fp.getY() && (int)bird.getY() <= fp.getY() + 50) {
				bird.setInvincible(true);
				fp.setVisible(false);
				invincibility.start();
				return false;
			}
		}
		return false;
	}

	/**
	 * Is the bird in the process of falling/exploding?
	*/

	public boolean dying() {
		return bird.isFalling() && !movePipes.isRunning();
	}

	// Setters and such
	
	public void setGhostPipes(boolean val)	{
		ghostPipes = val;
	}
	public void setChanceOfOscillating(double val)	{
		chanceofoscillating = val;
	}
	public void setChangeOscillation(boolean val)	{
		changeOscil = val;
	}
	public void setInitialVelocity(double val)	{
		initVelocity = val;
	}
	public void setMaxOscillation(int val)	{
		maxOscillation = val;
	}
	public void setMaxOscillationSpeed(double val)	{
		maxOscilSpeed = val;
	}
	public void setRoundStartOscillation(int val)	{
		numStartOscil = val;
	}
	public void setRoundsTillInvinsibilityPerPipe(int val)	{
		roundsTillInvin = val;
	}
	public void setRetro(boolean val)	{
		retro = val;
	}
}
