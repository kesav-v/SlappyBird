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
		AudioList songs = new AudioList(names, true);
		songs.play();
	}
}