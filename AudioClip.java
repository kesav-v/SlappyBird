import java.io.File;
import java.io.IOException;

import java.util.Scanner;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaException;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.SwingUtilities;
import java.util.concurrent.CountDownLatch;
import javafx.embed.swing.JFXPanel;

import javafx.application.Platform;

/**
 * This class makes Clips easier and more intuitive to use
 *
 * @author Ofek Gila
 * @since  May 19th, 2015
 * @lastedited May 22nd, 2015
 * @version 2.8
 */
public class AudioClip extends Application implements Runnable	{

	/**
	 * double length The length of the audioclip when initialized
	 */
	public double length;

	private String soundLocation;
	private Media clip;
	private MediaPlayer player;
	private boolean loop;
	private boolean running;
	private double startat, stopat;
	private double rate, volume;
	private int numCycles, totalCycles, cycleOn;

	final CountDownLatch latch = new CountDownLatch(1);
	

	public AudioClip(AudioClip ac)	{
		load(ac);
	}
	public AudioClip(String soundLocation, double start, double stop)	{
		this(soundLocation, start, stop, false);
	}
	public AudioClip(String soundLocation, double start, double stop, boolean loop)	{
		load(soundLocation, start, stop, loop);
		length = length();
	}

	public AudioClip(String soundLocation, boolean loop)	{
		this(soundLocation, 0, -1, loop);
	}

	public AudioClip(String soundLocation)	{
		this(soundLocation, false);
	}
	public String getLocation()	{
		return soundLocation;
	}

	public String getName()	{
		return soundLocation.substring(soundLocation.lastIndexOf("\\")+1, soundLocation.lastIndexOf("."));
	}

	public String getExtension()	{
		try	{
			return soundLocation.substring(soundLocation.lastIndexOf(".")).substring(1);
		}
		catch (StringIndexOutOfBoundsException e)	{
			System.err.println("Cannot find extension in " + soundLocation);
			return "";
		}
	}

	public String toString()	{
		return getName();
	}

	public void reload()	{
		dispose();
		load();
	}

	public void dispose()	{
		player.dispose();
	}

	public void load()	{
		player = new MediaPlayer(clip);
		player.setOnEndOfMedia(this);

		setStart(startat);
		setStop(stopat);
		setRate(rate);
		setVolume(volume);
	}

	public void replay()	{
		reload();
		play();
	}

	public void reInit(String soundLocation)	{
		reInit(soundLocation, 0, -1, false);
	}
	public void reInit(String soundLocation, double start, double end)	{
		reInit(soundLocation, start, end, false);
	}
	public void reInit(String soundLocation, double start, double end, boolean loop)	{
		dispose();
		load(soundLocation, start, end, loop);
		length = length();
	}

	public void load(String soundLocation)	{
		load(soundLocation, 0, -1, false);
	}

	public void load(AudioClip ac)	{
		noException();
		clip = ac.getClip();
		player = new MediaPlayer(clip);
		player.setOnEndOfMedia(this);

		play(); stop(); while (Double.isNaN(length()));
		length = ac.length();
		setStart(ac.getStart());
		setStop(ac.getStop());
		setVolume(ac.getVolume());
		setRate(ac.getRate());
		startat = getStart(); stopat = getStop();
		setLoop(ac.getLoop());
		numCycles = ac.getCycleCount();
		totalCycles = 0;
		cycleOn = 0;
		running = false;
	}

	public void load(String soundLocation, double start, double stop, boolean loop)	{
		noException();
		this.soundLocation = soundLocation;
		URL resource = getClass().getResource(soundLocation);
		try	{
			clip = new Media(resource.toString());
		}	catch (NullPointerException e)	{
			System.err.println("Cannot find file " + soundLocation);
			System.exit(1);
		}	catch (MediaException e)	{
			System.err.println("Unsupported file format: " + soundLocation);
			System.exit(1);
		}

		player = new MediaPlayer(clip);
		player.setOnEndOfMedia(this);
		
		play();
		stop();

		setStart(start);
		if (stop >= 0)
			setStop(stop);

		startat = getStart(); stopat = getStop();
		volume = rate = 1;
		totalCycles = 0;
		cycleOn = 0;
		numCycles = 1;
		running = false;
		this.loop = loop;
		while (Double.isNaN(length()));
	}

	public void loadNPlay()	{
		load();
		play();
	}

	public double length()	{
		return (getStop() - getStart()) / getRate();
	}

	public Media getClip()	{
		return clip;
	}

	public MediaPlayer getPlayer()	{
		return player;
	}

	public void play()	{
		if (isRunning())
			stop();
		player.play();
		running = true;
	}

	public void play(boolean fromstart)	{
		if (isRunning())
			if (fromstart)
				stop();
			else return;
		player.play();
		running = true;
	}

	public void play(double position)	{
		double start = getStart();
		setStart(position);
		play();
		setStart(start);
	}

	public void stop()	{
		player.stop();
		running = false;
		cycleOn = 0;
	}

	public void pause()	{
		player.pause();
		running = false;
	}

	public void setStart(double time)	{
		player.setStartTime(new Duration(time));
		startat = getStart();
	}

	public double getStart()	{
		return player.getStartTime().toMillis();
	}

	public void setStop(double time)	{
		player.setStopTime(new Duration(time));
		stopat = getStop();
	}

	public double getStop()	{
		return player.getStopTime().toMillis();
	}

	public void setVolume(double volume)	{
		player.setVolume(volume);
		this.volume = getVolume();
	}

	public double getVolume()	{
		return player.getVolume();
	}

	public double getRate()	{
		return player.getRate();
	}

	public void setRate(double rate)	{
		player.setRate(rate);
		this.rate = getRate();
	}

	public void setPosition(double position)	{
		if (isPlaying())
			player.seek(new Duration(position));
		else	{
			play();
			while(!isPlaying());
			player.seek(new Duration(position));
			pause();
		}
	}

	public double getPosition()	{
		return player.getCurrentTime().toMillis();
	}

	public double lengthSeconds()	{
		return length / 1E3;
	}

	public void setLoop(boolean loop)	{
		this.loop = loop;
	}

	public boolean getLoop()	{
		return loop;
	}

	public void setCycleCount(int count)	{
		numCycles = count;
	}

	public int getCycleCount()	{
		return numCycles;
	}

	public int getCycleOn()	{
		return cycleOn;
	}

	public int getTotalCycles()	{
		return totalCycles;
	}

	public boolean isRunning()	{
		return running;
	}
	public boolean isPlaying()	{
		return player.getStatus().equals(Status.PLAYING);
	}
	
	@Override	// runs when song ends
	public void run()	{
		player.stop();
		running = false;
		totalCycles++;
		if (loop)
			replay();
		else {
			cycleOn++;
			if (cycleOn < numCycles)
				replay();
			else {
				cycleOn = 0;
				reload();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {}
	public void noException()	{
	SwingUtilities.invokeLater(new Runnable() {
	   	public void run() {
	   	    new JFXPanel(); // initializes JavaFX environment
	   	    latch.countDown();
	   	}
	});
	try {
		latch.await();
	} catch (Exception e) {System.err.println("ahh");}
  }
}