import javax.swing.JFrame;

public class FlappyBird extends JFrame {
	
	public FlappyBird() {
		super("Flappy Bird");
	}

	public static void main(String[] args) {
		FlappyBird fw = new FlappyBird();
		fw.setSize(2160, 1440);
		fw.setVisible(true);
		fw.getContentPane().add(new FlappyPanel());
		fw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fw.setResizable(false);
	}
}