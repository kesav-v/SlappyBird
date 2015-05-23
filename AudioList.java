import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// client class of AudioClip, a class developped by Ofek Gila
// May 22, 2015

public class AudioList implements ActionListener {

	private ArrayList<String> locations;
	private AudioClip clip;
	private Timer loadNextSong;

	public AudioList(String[] locs, boolean shuffle) {
		locations = new ArrayList<String>();
		for (String s : locs) {
			locations.add(s);
		}
		if (shuffle) shuffle();
		clip = new AudioClip(locations.get(0));
		loadNextSong = new Timer((int)clip.length, this);
		loadNextSong.setRepeats(false);
	}

	public AudioList(ArrayList<String> locs, boolean shuffle) {
		locations = new ArrayList<String>();
		for (String s : locs) {
			locations.add(s);
		}
		if (shuffle) shuffle();
		clip = new AudioClip(locations.get(0));
		loadNextSong = new Timer((int)clip.length, this);
	}

	public void play() {
		clip.play();
		loadNextSong.start();
	}

	public void shuffle() {
		for (int k = locations.size() - 1; k > 0; k--) {
			int r = (int)(Math.random() * k);
			String temp = locations.get(r);
			locations.set(r, locations.get(k));
			locations.set(k, temp);
		}
	}

	public void actionPerformed(ActionEvent e) {
		locations.add(locations.remove(0));
		clip.dispose();
		clip.reInit(locations.get(0));
		clip.play();
		loadNextSong.setInitialDelay((int)clip.length);
		loadNextSong.restart();
	}
}