import java.util.Scanner;

public class AudioClipTester	{
	public static void main(String... pumpkins)	{
		// demo(pumpkins[0]);
		AudioClip clip = new AudioClip("PidginCoby.wav", 1000, 5000, true);
		clip.play();
		new Scanner(System.in).nextLine();
		clip.setRate(Math.PI);
		new Scanner(System.in).nextLine();
		clip.setRate(0.5);
		new Scanner(System.in).nextLine();
		clip.setLoop(false);
		new Scanner(System.in).nextLine();
		System.out.println(clip.isRunning());
		new Scanner(System.in).nextLine();
	}

	public static void demo(String soundLocation)	{	// demos using sound
		Scanner keyboard = new Scanner(System.in);
		AudioClip testclip = new AudioClip(soundLocation);
		System.out.println();
		System.out.println("new AudioClip(\"" + soundLocation + "\")");
		
		System.out.println();
		System.out.println("length " + testclip.length);
		System.out.println("lengthSeconds() " + testclip.lengthSeconds());
		System.out.println();

		System.out.print("play() "); keyboard.nextLine(); System.out.println();
		testclip.play();

		System.out.println("isRunning() " + testclip.isRunning());
		System.out.println("getPosition() " + testclip.getPosition());
		System.out.println();

		System.out.print("pause() "); keyboard.nextLine(); System.out.println();
		testclip.pause();

		System.out.println("isRunning() " + testclip.isRunning());
		System.out.println("getPosition() " + testclip.getPosition());
		System.out.println();

		System.out.print("play() "); keyboard.nextLine(); System.out.println();
		testclip.play();

		System.out.println("isRunning() " + testclip.isRunning());
		System.out.println("getPosition() " + testclip.getPosition());
		System.out.println();

		System.out.print("stop() "); keyboard.nextLine(); System.out.println();
		testclip.stop();

		System.out.println("isRunning() " + testclip.isRunning());
		System.out.println("getPosition() " + testclip.getPosition());
		System.out.println();

		System.out.print("play(double position) "); testclip.play(keyboard.nextLong()); System.out.println();
		keyboard.nextLine();

		System.out.println("getVolume() " + testclip.getVolume());
		System.out.println("getRate() " + testclip.getRate());
		System.out.println();

		System.out.print("setVolume(double volume) "); testclip.setVolume(keyboard.nextDouble()); System.out.println();
		System.out.print("setRate(double rate) "); testclip.setRate(keyboard.nextDouble()); System.out.println();
		keyboard.nextLine();

		System.out.print("isRunning() "); keyboard.nextLine();
		System.out.println(testclip.isRunning());
	}
}