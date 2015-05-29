import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Toolkit;

public class SettingsPanel extends JPanel implements ChangeListener {

	private int vol;
	private JSlider volume;
	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public SettingsPanel() {
		vol = 100;
	}

	public void stateChanged(ChangeEvent e) {

	}
}