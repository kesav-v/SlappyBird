import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;

public class SettingsPanel extends JPanel implements ChangeListener, ItemListener {

	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private JSlider volume;
	private FlappyPanel game;
	private JCheckBox retroMode;

	public SettingsPanel(FlappyPanel game) {
		setLayout(null);
		setBackground(Color.BLUE);
		this.game = game;
		volume = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		add(volume);
		volume.setSize(800, 50);
		volume.setLocation(680, 200);
		volume.setMajorTickSpacing(25);
		volume.setMinorTickSpacing(5);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		volume.setBackground(Color.BLUE);
		volume.setForeground(Color.WHITE);
		volume.addChangeListener(this);
		retroMode = new JCheckBox("Retro mode?");
		add(retroMode);
		retroMode.setSize(200, 40);
		retroMode.setLocation(680, 275);
		retroMode.addItemListener(this);
		retroMode.setBackground(Color.BLUE);
		retroMode.setForeground(Color.WHITE);
		retroMode.setFont(new Font("Arial", Font.PLAIN, 16));
	}



	public boolean getRetro() {
		return retroMode.isSelected();
	}

	public void stateChanged(ChangeEvent e) {
		game.getBackgroundMusic().setVolume(volume.getValue() / 100.0);
		game.getInvinMusic().setVolume(volume.getValue() / 100.0);
	}

	public void itemStateChanged(ItemEvent e) {
		game.setRetro(retroMode.isSelected());
	}
}