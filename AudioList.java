import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Makes an automatically-playing playlist of AudioClip files
 * Supports wav, mp3, mp4, flv, aif, and more filetypes
 * @author Kesav Viswanadha and Ofek Gila
 * @version 2.0
 * @since  May 22nd, 2015
 * @lastedited May 23rd, 2015
 */

public class AudioList implements ActionListener {

	private String[] locations;
	private ArrayList<Integer> playlist;
	private AudioClip clip;
	private Timer loadNextSong;
	private int songOn = 0;
	private boolean shuffle;

	public AudioList(Object[] locs, boolean shuffle) {
		this.shuffle = shuffle;
		locations = new String[locs.length];
		for (int i = 0; i < locs.length; i++)
			locations[i] = locs[i].toString();

		playlist = new ArrayList<Integer>();
		addNext();

		clip = new AudioClip(locations[playlist.get(songOn)]);
		loadNextSong = new Timer((int)clip.length(), this);
		loadNextSong.setRepeats(false);
	}

	public AudioList(ArrayList<String> locs, boolean shuffle) {
		this(locs.toArray(), shuffle);
	}

	public AudioList(boolean shuffle)	{
		this(shuffle, "mp3", "wav", "mp4", "m4a", "m4v", "m3u8", "fxm", "flv", "aif", "aiff");
	}
	public AudioList(boolean shuffle, String... extensions)	{
		ArrayList<String> locs = getFilesInFolder(new File(getDirectory()), new ArrayList<String>(), extensions);
		this.shuffle = shuffle;

		locations = new String[locs.size()];

		for (int i = 0; i < locs.size(); i++)
			locations[i] = locs.get(i);

		playlist = new ArrayList<Integer>();
		addNext();

		clip = new AudioClip(locations[playlist.get(songOn)]);
		loadNextSong = new Timer((int)clip.length(), this);
		loadNextSong.setRepeats(false);
	}

	public ArrayList<String> getFilesInFolder(final File folder, ArrayList<String> locations, String... extensions) {
		for (final File fileEntry : folder.listFiles())
			if (fileEntry.isDirectory())
				getFilesInFolder(fileEntry, locations);
			else for (String extension : extensions)
				if (getExtension(fileEntry.getPath()).equals(extension))
					locations.add(fileEntry.getPath().substring(getDirectory().length()+1));
			return locations;
	}
	public String getDirectory() {
		return  System.getProperty("user.dir");
    }

    public String getExtension(String name)	{
		try	{
			return name.substring(name.lastIndexOf(".")).substring(1);
		}
		catch (StringIndexOutOfBoundsException e)	{
			return "";
		}
	}

	public String getName(String loc)	{
		return loc.substring(loc.lastIndexOf("\\")+1, loc.lastIndexOf("."));
	}

	public void play() {
		clip.play();
		loadNextSong.setInitialDelay((int)(clip.length() - clip.getPosition()));
		loadNextSong.restart();
	}

	public void pause()	{
		clip.pause();
		loadNextSong.setInitialDelay(Integer.MAX_VALUE);
		loadNextSong.restart();
	}

	public void stop()	{
		clip.stop();
		loadNextSong.setInitialDelay(Integer.MAX_VALUE);
		loadNextSong.restart();
	}

	public void addNext()	{
		if (playlist.size() == 0)
			playlist.add(shuffle ? ((int)(Math.random() * locations.length)):0);
		else if (shuffle) {
			int ran;
			do ran = (int)(Math.random() * locations.length);
			while (ran == playlist.get(playlist.size() - 1));
			playlist.add(ran);
		} else
			playlist.add((playlist.get(playlist.size() - 1) + 1) % locations.length);
	}

	public void nextSong()	{
		songOn++;
		addNext();
		clip.stop();
		clip.dispose();
		clip.reInit(locations[playlist.get(songOn)]);
		clip.play();
		loadNextSong.setInitialDelay((int)clip.length());
		loadNextSong.restart();
	}

	public void previousSong()	{
		songOn--;
		if (songOn < 0) songOn = playlist.size() - 1;
		songOn--;
		nextSong();
	}

	public void playSong(String songName)	{
		for (songOn = 0; songOn < playlist.size(); songOn++)
			if (getName(locations[playlist.get(songOn)]).equals(songName))
				break;
		if (songOn == playlist.size())
			System.err.println("Cannot find song " + songName);
		else {
			songOn--;
			nextSong();
		}
	}

	public void actionPerformed(ActionEvent e) {
		nextSong();
	}
}