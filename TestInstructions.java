import javax.swing.JFrame;

public class TestInstructions {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Testing Instructions");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1000, 1000);
		frame.setContentPane(new InstructionPanel());
	}
}