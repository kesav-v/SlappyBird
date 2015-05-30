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

public class TestMainMenu extends JPanel implements ActionListener {

	private JButton play;
	private JButton howToPlay;
	private CardLayout cards;
	private FlappyPanel gamePanel;
	private MainMenu menu;
	private InstructionPanel instructions;
	private JButton stats;
	private FlappyStats statPanel;
	private JButton backToMenu;
	private JButton instructionsToMenu;
	private JButton statsToMenu;
	private JButton settingsToMenu;
	private JButton goToSettings;
	private JButton gameToMenu;
	private JButton backToSettings;
	private Font universal;
	private SettingsPanel settings;
	private ModeCreator creator;

	public TestMainMenu() {
		universal = new Font("Comic Sans", Font.BOLD, 48);
		cards = new CardLayout();
		setLayout(cards);
		gamePanel = new FlappyPanel(this);
		creator = new ModeCreator();
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
		add(menu, "Main Menu");
		add(gamePanel, "Game");
		add(instructions, "Instructions");
		add(statPanel, "Stats");
		add(settings, "Settings");
		add(creator, "Mode Creator");
		backToSettings = new JButton("CLOSE");
		creator.add(backToSettings);
		backToSettings.setLocation(50, 900);
		backToSettings.setSize(400, 40);
		backToSettings.setBackground(Color.RED);
		backToSettings.setForeground(Color.WHITE);
		backToSettings.addActionListener(new ShowSettings());
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

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.getContentPane().add(new TestMainMenu());
		frame.setVisible(true);
		frame.setSize(2160, 1440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	public void gameOver()	{
		gameToMenu.setVisible(gamePanel.gameIsOver());
	}

	public void gameStart()	{
		gameToMenu.setVisible(gamePanel.gameIsOver());
	}

	private class ShowSettings implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			show("Settings");
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

	public void show(String s) {
		cards.show(this, s);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
	}

	public void actionPerformed(ActionEvent e) {
		show("Game");
	}
}
