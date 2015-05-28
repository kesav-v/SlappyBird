import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import javax.swing.JSlider;

public class MusicPanel extends JPanel implements ChangeListener, ActionListener {

	private AudioList songs;
	private JButton playPause;
	private JButton stop;
	private JButton previous;
	private JButton next;
	private JSlider position;
	private Timer changePos;

	public MusicPanel(String[] args) {
		setLayout(null);
		System.out.println(getLayout());
		playPause = new JButton("Pause");
		add(playPause);
		playPause.setSize(200, 50);
		playPause.setLocation(400, 475);
		playPause.addActionListener(new HandlePlayPause());
		stop = new JButton("Stop");
		add(stop);
		stop.setSize(200, 50);
		stop.setLocation(400, 375);
		stop.addActionListener(new HandleStop());
		previous = new JButton("Previous");
		add(previous);
		previous.setSize(200, 50);
		previous.setLocation(400, 575);
		previous.addActionListener(new HandlePrevious());
		next = new JButton("Next");
		add(next);
		next.setSize(200, 50);
		next.setLocation(400, 675);
		next.addActionListener(new HandleNext());
		System.out.println("Initialized buttons");
		//this.repaint();
		System.out.println("PAINTED");
		this.songs = new AudioList(AudioList.INITIAL_SHUFFLE, AudioList.CURRENT_FOLDER, new File("C:/Users/Kesav Viswanadha/OneDrive/Documents/YoutubeVideos"));
		position = new JSlider(JSlider.HORIZONTAL, 0, (int)(songs.getAudioClip().lengthSeconds() * 1000), 0);
		add(position);
		position.setSize(800, 20);
		position.setLocation(100, 275);
		position.addChangeListener(this);
		changePos = new Timer(20, this);
		changePos.start();
		songs.play();
	}

	private class HandlePlayPause implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (songs.isPlaying()) {
				songs.pause();
				playPause.setText("Play");
			}
			else {
				songs.play();
				playPause.setText("Pause");
			}
			repaint();
		}
	}

	private class HandleStop implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			songs.stop();
		}
	}

	private class HandlePrevious implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			songs.previousSong();
		}
	}

	private class HandleNext implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			songs.nextSong();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("STARTING");
		super.paintComponent(g);
		setBackground(Color.BLACK);
		System.out.println("HERE");
	}

	public void stateChanged(ChangeEvent e) {
		songs.getAudioClip().setPosition(position.getValue());
	}

	public void actionPerformed(ActionEvent e) {
		position.setValue((int)songs.getAudioClip().getPosition());
	}
}