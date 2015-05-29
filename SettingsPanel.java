import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Toolkit;
import java.awt.Color;

public class SettingsPanel extends JPanel implements ChangeListener {

	private JSlider volume;
	FlappyPanel game;
	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public SettingsPanel(FlappyPanel game) {
		setLayout(null);
		setBackground(Color.BLUE);
		this.game = game;
		volume = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		add(volume);
		volume.setSize(800, 20);
		volume.setLocation(680, 200);
		volume.addChangeListener(this);
	}

	public void stateChanged(ChangeEvent e) {
		game.getBackgroundMusic().setVolume(volume.getValue() / 100.0);
		game.getInvinMusic().setVolume(volume.getValue() / 100.0);
	}
}