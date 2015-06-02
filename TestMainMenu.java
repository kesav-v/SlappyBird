import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.ImageIcon;

/**
 * This class represents the main menu of the game. All the parts of the game come together
 * under this panel. This is the main class of the game.
 * @author Kesav Viswanadha
 * @version 2.1
 * @lastedited June 1, 2015
*/

public class TestMainMenu extends JPanel implements ActionListener {

	private JButton play; // the "PLAY" button displayed in the main menu
	private JButton howToPlay; // the "HOW TO PLAY" button displayed in the main menu
	private CardLayout cards; // the card layout that this panel is going to use
	private FlappyPanel gamePanel; // the main panel where the game is played
	private MainMenu menu; // this is the panel that represents the main menu
	private InstructionPanel instructions; // this panel displays instructions on how to play the game
	private JButton stats; // the "YOUR STATISTICS" button displayed in the main menu
	private FlappyStats statPanel; // the panel which displays the statistics
	private JButton instructionsToMenu; // the button which takes the user back to the menu from the instructions
	private JButton statsToMenu; // the button which takes the user back to the menu from the stats page
	private JButton settingsToMenu; // the button which takes the user back to the menu from the settings panel
	private JButton goToSettings; // the "SETTINGS" button displayed in the main menu
	private JButton gameToMenu; // the button which takes the user back to the main menu from the game
	private JButton backToSettings; // the button which takes the user back to settings from the mode creator
	private Font universal; // the universal font used for all the JButtons
	private SettingsPanel settings; // the settings panel
	private ModeCreator creator; // the panel where a user can create a new mode of gameplay

	/**
	 * Constructs a TestMainMenu object.
	 * Adds all necessary components to the panel and sets up everything.
	*/

	public TestMainMenu() {
		universal = new Font("Comic Sans", Font.BOLD, 48);
		cards = new CardLayout();
		setLayout(cards);
		gamePanel = new FlappyPanel(this);
		play = new JButton("PLAY");
		play.setFont(universal);
		play.setBackground(Color.GREEN);
		howToPlay = new JButton("HOW TO PLAY");
		howToPlay.setFont(universal);
		howToPlay.setBackground(Color.BLUE);
		howToPlay.setForeground(Color.WHITE);
		stats = new JButton("YOUR STATISTICS");
		stats.setFont(universal);
		stats.setBackground(Color.ORANGE);
		stats.setForeground(Color.WHITE);
		instructionsToMenu = new JButton("BACK TO MAIN MENU");
		instructionsToMenu.setFont(universal);
		statsToMenu = new JButton("BACK TO MAIN MENU");
		statsToMenu.setFont(universal);
		settingsToMenu = new JButton("BACK TO MAIN MENU");
		settingsToMenu.setFont(universal);
		goToSettings = new JButton("SETTINGS");
		goToSettings.setFont(universal);
		goToSettings.setBackground(Color.GRAY);
		gameToMenu = new JButton("BACK TO MAIN MENU");
		gameToMenu.setFont(universal);
		gameToMenu.setBackground(Color.RED);
		gameToMenu.setForeground(Color.WHITE);
		menu = new MainMenu();
		gamePanel.add(gameToMenu);
		gameToMenu.setSize(1000, 100);
		gameToMenu.setLocation(580, 900);
		gameToMenu.setVisible(false);
		gameToMenu.addActionListener(new BackToMenu());
		instructions = new InstructionPanel();
		instructions.add(instructionsToMenu);
		instructionsToMenu.addActionListener(new BackToMenu());
		instructionsToMenu.setSize(1000, 200);
		instructionsToMenu.setLocation(580, 1020);
		statPanel = new FlappyStats();
		statPanel.add(statsToMenu);
		statsToMenu.addActionListener(new BackToMenu());
		statsToMenu.setSize(1000, 200);
		statsToMenu.setLocation(580, 1020);
		settings = new SettingsPanel(gamePanel, this);
		settings.add(settingsToMenu);
		settingsToMenu.setSize(1000, 200);
		settingsToMenu.setLocation(580, 1020);
		settingsToMenu.addActionListener(new BackToMenu());
		creator = new ModeCreator(settings);
		add(menu, "Main Menu");
		add(gamePanel, "Game");
		add(instructions, "Instructions");
		add(statPanel, "Stats");
		add(settings, "Settings");
		add(creator, "Mode Creator");
		backToSettings = new JButton("CLOSE");
		creator.add(backToSettings);
		backToSettings.setLocation(50, 950);
		backToSettings.setSize(2060, 40);
		backToSettings.setBackground(Color.RED);
		backToSettings.setForeground(Color.WHITE);
		backToSettings.addActionListener(new ResetCreator());
		menu.add(play);
		play.setSize(1000, 200);
		play.setLocation(580, 520);
		play.addActionListener(this);
		menu.add(howToPlay);
		howToPlay.setSize(1000, 200);
		howToPlay.setLocation(580, 820);
		howToPlay.addActionListener(new ShowInstructions());
		menu.add(stats);
		stats.setSize(1000, 200);
		stats.setLocation(580, 1120);
		stats.addActionListener(new ShowStats());
		menu.add(goToSettings);
		goToSettings.setSize(1000, 200);
		goToSettings.setLocation(580, 220);
		goToSettings.addActionListener(new ShowSettings());
		cards.show(this, "Main Menu");
	}

	public SettingsPanel getSettingsPanel() {
		return settings;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.getContentPane().add(new TestMainMenu());
		frame.setVisible(true);
		frame.setSize(2160, 1440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	/**
	 * If the game is over, displays the "BACK TO MAIN MENU" button.
	*/

	public void gameOver()	{
		gameToMenu.setVisible(gamePanel.gameIsOver());
	}

	/**
	 * Below are several nested classes for handling the buttons throughout the program.
	 * They do a variety of simple functions which mostly consist of flipping through cards in the layout.
	*/

	private class ShowSettings implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			show("Settings");
		}
	}

	private class ResetCreator implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			show("Settings");
			creator.clear();
			settings.getComboBox().setSelectedItem("Default");
		}
	}

	private class ShowStats implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			show("Stats");
		}
	}

	private class BackToMenu implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gamePanel.resetGame();
			show("Main Menu");
		}
	}

	private class ShowInstructions implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			show("Instructions");
		}
	}

	public void actionPerformed(ActionEvent e) {
		show("Game");
	}

	/**
	 * Displays a JPanel with the given name.
	 * @param s The name of the panel to be displayed.
	*/

	public void show(String s) {
		cards.show(this, s);
	}

	@Override
	/**
	 * Paints all the components onto the main menu.
	 * @param g The Graphics object used to do the drawing.
	*/

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
	}
}
