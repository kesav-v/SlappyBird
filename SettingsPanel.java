import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Toolkit;

public class SettingsPanel extends JPanel implements ChangeListener {

	private JSlider volume;
	FlappyPanel game;
	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public SettingsPanel(FlappyPanel game) {
		this.game = game;
		volume = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
	}

	public void stateChanged(ChangeEvent e) {
		
	}
}