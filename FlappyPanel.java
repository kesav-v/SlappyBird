import javax.swing.JPanel;
import java.awt.Graphics;
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
 * @version 1.8
 * @lastedited May 24, 2015
*/

public class FlappyPanel extends JPanel implements ActionListener, KeyListener {
	
	private FlappyPipe[] pipes;
	private Timer movePipes;
	private boolean first;
	private Bird bird;
	private int invincibleTimes;
	private boolean firstPress;
	private ArrayList<String> previousScores;
	private boolean justDied;
	private ImageIcon theBird;
	private ImageIcon bird1, bird2;
	private boolean imgLoaded;
	private Timer invincibility;
	private Color invincibleColor;
	private AudioList songs;
	private ArrayList<String> songNames;
	private AudioClip clip;
	private ImageIcon thePipe;
	private int headBangs;
	private int count;
	private ImageIcon apple;
	private boolean ghostPipes, oscilPipes, changeOscil;
	private double initVelocity;
	private int maxOscillation;

	public FlappyPanel() {
		apple = new ImageIcon(getClass().getResource("AppleImg.png"));
		clip = new AudioClip(new File("SoundEffects/MarioInvincible.mp3"));
		songs = new AudioList(AudioList.INITIAL_SHUFFLE, AudioList.CURRENT_FOLDER, new File("AllMusic"));
		songs.play();
		previousScores = new ArrayList<String>();
		movePipes = new Timer(20, this);
		invincibility = new Timer(25, new Handler());
		first = true;
		firstPress = true;
		headBangs = count = 0;
		ghostPipes = false;
<<<<<<< HEAD
		oscilPipes = true;
		changeOscil = true;
=======
		oscilPipes = false;
		changeOscil = false;
		initVelocity = 3;
		maxOscillation = 600;
>>>>>>> origin/master
		bird = new Bird(this);
		addKeyListener(this);
		justDied = true;
		imgLoaded = true;
		invincibleColor = Color.BLACK;
		theBird = new ImageIcon(getClass().getResource("FlappyBirdOnline.png"));
		bird1 = new ImageIcon(getClass().getResource("Bird1.png"));
		bird2 = new ImageIcon(getClass().getResource("Bird2.png"));
		thePipe = new ImageIcon(getClass().getResource("PipeCut.png"));
	}

	public Object[] getValues(int numPipe)	{
		return new Object[]	{initVelocity, numPipe * (getWidth() / 4) + getWidth() / 4, numPipe, oscilPipes, changeOscil, ghostPipes, maxOscillation};
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			invincibleTimes++;
			if (!clip.isRunning()) {
				clip.play();
				songs.pause();
			}
			invincibleColor = new Color(255 - invincibleTimes, 0, 0);
			if (invincibleTimes == 255) {
				bird.setInvincible(false);
				invincibility.stop();
				invincibleTimes = 0;
				clip.stop();
				songs.play();
			}
		}
	}

	public void paintComponent(Graphics g) {
		requestFocus();
		if (first) {
			first = false;
			pipes = new FlappyPipe[4];
			for (int i = 0; i < pipes.length; i++) {
				pipes[i] = new FlappyPipe(this, getValues(i));
				if (!pipes[i].isInvincible()) pipes[i].setOscillation(0);
				else pipes[i].setOscillation(10);
			}
		}
		super.paintComponent(g);
		setBackground(Color.BLACK);
		for (FlappyPipe fp : pipes) {
			g.setColor(fp.getColor());
			if (fp.isInvincible()) {
				if (fp.isVisible()) g.drawImage(apple.getImage(), fp.getX(), fp.getY(), 50, 50, this);
			}
			else if (fp.isVisible()) {
				g.drawImage(thePipe.getImage(), fp.getX(), 0, 50, fp.getY(), this);
				g.drawImage(thePipe.getImage(), fp.getX(), fp.getY() + 200, 50, getHeight() - (fp.getY() + 200), this);
			}
		}
		if (bird.isInvincible())
			if (count % 4 < 2)
				g.drawImage(bird1.getImage(), 50, bird.getY(), 50, 50, this);
			else g.drawImage(bird2.getImage(), 50, bird.getY(), 50, 50, this);
		else
			if (count % 20 < 10)
				 g.drawImage(bird1.getImage(), 50, bird.getY(), 50, 50, this);
			else g.drawImage(bird2.getImage(), 50, bird.getY(), 50, 50, this);
		if (dying() || dead()) {
			movePipes.stop();
			for (FlappyPipe fp : pipes)
				fp.setVisible(true);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 96));
			g.drawString("YOU LOSE!", 800, 700);
			if (justDied) {
				saveScore();
				justDied = false;
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 96));
		g.drawString("SCORE: " + sumScores(), 800, 800);
		if (firstPress) {
			g.setColor(new Color(0, 100, 0));
			g.drawString("GET READY!", 780, 700);
		}
	}

	private int sumScores() {
		int sum = 0;
		for (FlappyPipe fp : pipes) {
			sum += fp.getScore();
		}
		return sum;
	}

	public void actionPerformed(ActionEvent e) {
		count++;
		for (FlappyPipe fp : pipes) {
			fp.move();
		}
		if (sumScores() != 0) {
			incPipeVelocity(bird.isInvincible() ? 2:1);
		}
		repaint();


		// songs.setRate(1 + sumScores() / 20f);
		// System.out.println((1 + sumScores() / 20f) + " " + (1 + (pipes[0].getVelocity()-2)/20));
	}

	public void incPipeVelocity(double ratio)	{
		for (FlappyPipe fp : pipes)	{
			if (ratio > 0)
				fp.incVelocity(velocityInc(fp.getVelocity()) * ratio);
			else fp.incVelocity(velocityDec(fp.getVelocity()-2) * ratio);
		}
	}

	public double velocityInc(double num)	{
		return 1 / (100 * Math.sqrt(num));
	}

	public double velocityDec(double num)	{
		return Math.sqrt(num) / 100;
	}

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
		for (i = 0; i < previousScores.size() && Integer.parseInt(previousScores.get(i).substring(0, previousScores.get(i).indexOf(" "))) > sumScores(); i++) {}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		previousScores.add(i, sumScores() + " on " + sdf.format(date));
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

	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) && !dying() && !dead()) {
			if (firstPress) {
				movePipes.start();
				bird.startFalling();
				firstPress = false;
			}
			//clip.play(9000000);
			bird.setVelocity(13);
			//flap.loadNPlay();
			repaint();
		}
		if (e.getKeyChar() == 'r' && dead()) {
			bird = new Bird(this);
			for (int i = 0; i < pipes.length; i++) {
				pipes[i] = new FlappyPipe(this, getValues(i));
				if (!pipes[i].isInvincible()) pipes[i].setOscillation(0);
				else pipes[i].setOscillation(10);
			}
			firstPress = true;
			justDied = true;
			previousScores.clear();
			repaint();
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public boolean dead() {
		if (bird.getY() + 50 > getHeight() || (bird.getY() < 0)) return true;
		for (FlappyPipe fp : pipes) {
			if (!fp.isInvincible() && fp.getX() >= 0 && fp.getX() <= 100 &&
				(fp.getY() <= bird.getY() - 150 || fp.getY() >= bird.getY())) {
				if (bird.isInvincible())	{
					incPipeVelocity(-1);
					return false;
				}
				else return true;
			} else if (fp.isInvincible() && fp.getX() >= 0 && fp.getX() <= 100 && bird.getY() + 50 >= fp.getY() && bird.getY() <= fp.getY() + 50) {
				bird.setInvincible(true);
				fp.setVisible(false);
				invincibility.start();
				invincibleColor = Color.RED;
				return false;
			}
		}
		return false;
	}

	public boolean dying() {
		return bird.isFalling() && !movePipes.isRunning();
	}
}