import java.util.Scanner;

// this is a client of a class made by Ofek Gila
// May 22, 2015

public class PlayFavorites {
	public static void main(String[] args) {
		String[] names = new String[18];
		names[0] = "Andalouse.mp3";
		names[1] = "Breathing.mp3";
		names[2] = "Bills.mp3";
		names[3] = "Bella.mp3";
		names[4] = "Cool.mp3";
		names[5] = "Conmigo.mp3";
		names[6] = "INeedYourLove.mp3";
		names[7] = "WrittenInTheStars.mp3";
		names[8] = "SeeYouAgain.mp3";
		names[9] = "LaVieDuBonCote.mp3";
		names[10] = "JmeTire.mp3";
		names[11] = "PasTouche.mp3";
		names[12] = "TheMan.mp3";
		names[13] = "TenFeetTall.mp3";
		names[14] = "HeyBrother.mp3";
		names[15] = "Papaoutai.mp3";
		names[16] = "GetLow.mp3";
		names[17] = "InTheEnd.mp3";
		AudioList songs = new AudioList(names, AudioList.INITIAL_SHUFFLE);
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
				case 1: songs.stop(); break;
				case 2: songs.nextSong(); break;
				case 3: songs.previousSong(); break;
				case 4:
					System.out.print("Enter the name of the song you would like to play -> ");
					songs.playSong(scan.nextLine());
					break;
				case 5:
					System.out.print("Enter the new playback speed -> ");
					songs.speedUp(scan.nextDouble());
					scan.nextLine();
					break;
			}
		}
	}
}