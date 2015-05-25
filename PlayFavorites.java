import java.util.Scanner;

// this is a client of a class made by Ofek Gila
// May 24, 2015

public class PlayFavorites {
	public static void main(String[] args) {
		AudioList songs = new AudioList(AudioList.INITIAL_SHUFFLE);
		songs.play();
		Scanner scan = new Scanner(System.in);
		int n = -1;
		while (n != 1) {
			if (songs.isPlaying()) System.out.print("Enter 0 to pause, 1 to stop, 2 to go to next song, 3 to go to previous song, 4 to jump to a song, or 5 to change playback speed -> ");
			else System.out.print("Enter 0 to play, 1 to stop, 2 to go to next song, 3 to go to previous song, 4 to jump to a song, or 5 to change playback speed -> ");
			n = scan.nextInt();
			scan.nextLine();
			switch (n) {
				case 0:
					if (songs.isPlaying()) songs.pause();
					else songs.play();
					break;
				case 1: songs.stop(); System.exit(0); break;
				case 2: songs.nextSong(); break;
				case 3: songs.previousSong(); break;
				case 4:
					System.out.print("Enter the name of the song you would like to play -> ");
					songs.playSong(scan.nextLine());
					break;
				case 5:
					System.out.print("Enter the new playback speed -> ");
					songs.setRate(scan.nextDouble());
					scan.nextLine();
					break;
			}
		}
	}
}