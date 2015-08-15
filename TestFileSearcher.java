public class TestFileSearcher {
	public static void main(String[] args) {
		java.util.ArrayList<java.io.File> list = FileSearcher.findFiles(FileSearcher.SUBFOLDERS_AND_CURRENT, ".mp3");
		for (java.io.File f : list) {
			System.out.println(f.getName());
		}
	}
}