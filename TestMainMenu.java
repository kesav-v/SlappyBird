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
	private Font universal;

	public TestMainMenu() {
		universal = new Font("Comic Sans", Font.BOLD, 48);
		cards = new CardLayout();
		setLayout(cards);
		System.out.println(getLayout());
		play = new JButton("PLAY");
		play.setFont(universal);
		howToPlay = new JButton("HOW TO PLAY");
		howToPlay.setFont(universal);
		stats = new JButton("YOUR STATISTICS");
		stats.setFont(universal);
		instructionsToMenu = new JButton("BACK TO MAIN MENU");
		instructionsToMenu.setFont(universal);
		statsToMenu = new JButton("BACK TO MAIN MENU");
		statsToMenu.setFont(universal);
		menu = new MainMenu();
		gamePanel = new FlappyPanel();
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
		add(menu, "Main Menu");
		add(gamePanel, "Game");
		add(instructions, "Instructions");
		add(statPanel, "Stats");
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

	private class ShowStats implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			show("Stats");
		}
	}

	private class BackToMenu implements ActionListener {
		public void actionPerformed(ActionEvent e) {
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