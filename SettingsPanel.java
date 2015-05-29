import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsPanel extends JPanel implements ChangeListener {

	private int vol;
	private JSlider volume;

	public SettingsPanel() {
		vol = 100;
	}

	public void stateChanged(ChangeEvent e) {

	}
}