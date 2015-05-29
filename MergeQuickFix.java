import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;

public class MergeQuickFix	{
	public static void main(String... pumpkins)	{
		for (File file : FileSearcher.findFiles(FileSearcher.CURRENT_FOLDER, "java"))	{
			if (file.getName().contains("MergeQuickFix"))	continue;
			boolean changed = false;
			ArrayList<String> fileContents = new ArrayList<String>();
			Scanner input = null;
			try {
				input = new Scanner(file);
			}	catch (IOException e)	{
				continue;
			}
			while (input.hasNext())	{
				String nextLine = input.nextLine();
				if (!nextLine.contains("=====") && !nextLine.contains(">>>>>") && !nextLine.contains("<<<<<"))
					fileContents.add(nextLine);
				else changed = true;
			}
			input.close();
			if (!changed) continue;

			PrintWriter writer = null;
			try {
				writer = new PrintWriter(file);
			} catch (IOException e) {
				continue;
			}
			for (String line : fileContents)
				writer.println(line);
			writer.close();
			System.out.println("Checked: " + file);
		}
	}
}