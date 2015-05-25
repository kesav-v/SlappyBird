import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Makes an automatically-playing playlist of AudioClip files
 * Supports wav, mp3, mp4, flv, aif, and more filetypes
 * @author Kesav Viswanadha and Ofek Gila
 * @version 2.3
 * @since  May 22nd, 2015
 * @lastedited May 24th, 2015
 */

public class AudioList implements ActionListener {

	public static final int NO_SHUFFLE = 0, CONSTANT_SHUFFLE = 1, INITIAL_SHUFFLE = 2;
	public static final int CURRENT_FOLDER = 0, SUBFOLDERS_AND_CURRENT = 1, SEARCH_EVERYTHING = 2;
	public static final String[] acceptedTypes = {"mp3", "wav", "mp4", "m4a", "m4v", "m3u8", "fxm", "flv", "aif", "aiff"};
	public static final File currentFile = new File(System.getProperty("user.dir"));

	private String[] locations;
	private ArrayList<Integer> playlist;
	private AudioClip clip;
	private Timer loadNextSong;
	private int songOn = 0;
	private int shuffle;
	private int folderSearch;
	private double rate = 1;

	public AudioList(Object[] locs, int shuffle) {
		this.shuffle = shuffle;
		locations = new String[locs.length];
		for (int i = 0; i < locs.length; i++)
			locations[i] = locs[i].toString();

		playlist = new ArrayList<Integer>();
		addNext();

		clip = new AudioClip(new File(locations[playlist.get(songOn)]).getAbsoluteFile());
		loadNextSong = new Timer((int)clip.length(), this);
		loadNextSong.setRepeats(false);
	}

	public AudioList(ArrayList<String> locs, int shuffle) {
		this(locs.toArray(), shuffle);
	}

	public AudioList()	{
		this(NO_SHUFFLE);
	}

	public AudioList(int shuffle, int folderSearch)	{
		this(shuffle, folderSearch, currentFile);
	}

	public AudioList(int shuffle)	{
		this(shuffle, acceptedTypes);
	}

	public AudioList(int shuffle, String... extensions)	{
		this(shuffle, 1, extensions);
	}

	public AudioList(int shuffle, int folderSearch, File startDirectory)	{
		this(shuffle, folderSearch, startDirectory, acceptedTypes);
	}

	public AudioList(int shuffle, int folderSearch, String... extensions)	{
		this(shuffle, folderSearch, currentFile, extensions);
	}

	public AudioList(int shuffle, int folderSearch, File startDirectory, String... extensions)	{
		this.folderSearch = folderSearch;
		if (folderSearch == SEARCH_EVERYTHING)
			while (startDirectory.getParentFile().getParentFile().getParentFile() != null)
				startDirectory = startDirectory.getParentFile();
		ArrayList<String> locs = getFilesInFolder(startDirectory, new ArrayList<String>(), extensions);
		this.shuffle = shuffle;

		locations = new String[locs.size()];

		for (int i = 0; i < locs.size(); i++)
			locations[i] = locs.get(i);

		playlist = new ArrayList<Integer>();
		addNext();

		clip = new AudioClip(new File(locations[playlist.get(songOn)]).getAbsoluteFile());
		loadNextSong = new Timer((int)clip.length(), this);
		loadNextSong.setRepeats(false);
	}

	public ArrayList<String> getFilesInFolder(final File folder, ArrayList<String> locations, String... extensions) {
		try {
			for (final File fileEntry : folder.listFiles())
				if (fileEntry.isDirectory() && (folderSearch == SUBFOLDERS_AND_CURRENT || folderSearch == SEARCH_EVERYTHING))
					getFilesInFolder(fileEntry, locations, extensions);
				else for (String extension : extensions)
					if (getExtension(fileEntry.getPath()).equals(extension))
						locations.add(fileEntry.getPath());
			return locations;
		}	catch(NullPointerException e)	{return locations; }
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
		clip.setRate(rate);
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
		int ran;
		if (playlist.size() == 0)
			switch (shuffle)	{
				case NO_SHUFFLE: 
					playlist.add(0); break;
				case CONSTANT_SHUFFLE: case INITIAL_SHUFFLE:
					playlist.add((int)(Math.random() * locations.length)); break;	
			}
		else switch(shuffle) {
			case NO_SHUFFLE: 
				playlist.add((playlist.get(playlist.size() - 1) + 1) % locations.length); break;
			case CONSTANT_SHUFFLE:
				do ran = (int)(Math.random() * locations.length);
				while (ran == playlist.get(playlist.size() - 1));
				playlist.add(ran);
				break;
			case INITIAL_SHUFFLE:
				if (playlist.size() >= locations.length)
					playlist.add(playlist.get(playlist.size() - locations.length));
				else	{
					do ran = (int)(Math.random() * locations.length);
					while (inPlaylist(ran));
					playlist.add(ran);
					break;
				}
		}
	}

	public boolean inPlaylist(int location)	{
		for (Integer song : playlist)
			if (song.intValue() == location)
				return true;
		return false;
	}

	public void nextSong()	{
		songOn++;
		addNext();
		clip.stop();
		clip.dispose();
		clip.reInit(new File(locations[playlist.get(songOn)]).getAbsoluteFile());
		clip.setRate(rate);
		clip.play();
		loadNextSong.setInitialDelay((int)clip.length());
		loadNextSong.restart();
	}

	public void setRate(double rate) {
		this.rate = rate;
		clip.setRate(rate);
		loadNextSong.setInitialDelay((int)((loadNextSong.getDelay() - clip.getPosition()) / rate));
		loadNextSong.restart();
	}

	public void previousSong()	{
		songOn--;
		if (songOn < 0) songOn = playlist.size() - 1;
		songOn--;
		nextSong();
	}

	public void playSong(String songName)	{
		playlist = new ArrayList<Integer>();
		songOn = 0;
		clip.stop();
		int songloc;
		for (songloc = 0; songloc < locations.length; songloc++)
			if (getName(locations[songloc]).equals(songName))
				break;
		if (songloc == locations.length)
			System.err.println("Cannot find song " + songName);
		else {
			playlist.add(songloc);
			clip.stop();
			clip = new AudioClip(new File(locations[playlist.get(songOn)]).getAbsoluteFile());
			play();
		}
	}

	public boolean isPlaying() {
		return clip.isRunning();
	}

	public void actionPerformed(ActionEvent e) {
		nextSong();
	}

	public AudioClip getAudioClip()	{
		return clip;
	}

	public String getSongName()	{
		return clip.getName();
	}
}