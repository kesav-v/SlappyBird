import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MusicPanel extends JPanel implements MouseListener {

	private AudioList songs;
	private JButton playPause;
	private JButton stop;
	private JButton previous;
	private JButton next;

	public MusicPanel(String[] args) {
		setLayout(null);
		System.out.println(getLayout());
		playPause = new JButton("Play");
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
		addMouseListener(this);
		//this.repaint();
		System.out.println("PAINTED");
	}

	private class HandlePlayPause implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/**if (songs.isPlaying()) {
				songs.pause();
				playPause.setText("Play");
			}
			else {
				songs.play();
				playPause.setText("Pause");
			}
			repaint();*/
		}
	}

	private class HandleStop implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//songs.stop();
		}
	}

	private class HandlePrevious implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//songs.previousSong();
		}
	}

	private class HandleNext implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//songs.nextSong();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("STARTING");
		super.paintComponent(g);
		setBackground(Color.BLACK);
		System.out.println("HERE");
		this.songs = new AudioList(AudioList.INITIAL_SHUFFLE, "mp3");
		songs.play();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {repaint();}
}