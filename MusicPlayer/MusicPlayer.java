import javax.swing.JFrame;
import javax.swing.JPanel;

public class MusicPlayer {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Music Player");
		frame.setVisible(true);
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mp = new MusicPanel(args);
		frame.setContentPane(mp);
	}	
}