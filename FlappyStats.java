import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;

public class FlappyStats extends JPanel {
	
	ArrayList<Integer> scores;

	public FlappyStats() {
		scores = new ArrayList<Integer>();
		readScores();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Flappy Bird Stats");
		frame.setVisible(true);
		FlappyStats fs = new FlappyStats();
		frame.getContentPane().add(fs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
	}

	public void readScores() {
		Scanner in = null;
		try {
			in = new Scanner(new File("scores.txt"));
		} catch (IOException e) {
			System.out.println("ERROR: scores.txt does not exist");
			System.exit(1);
		}
		while (in.hasNext() && scores.size() < 20) {
			String s = in.nextLine();
			scores.add(Integer.parseInt(s.substring(0, s.indexOf(" "))));
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		g.drawString("Average score (top 20 games): " + average(), getWidth() / 2 - 300, getHeight() / 2 - 100);
		g.drawString("High score (top 20 games): " + max(), getWidth() / 2 - 300, getHeight() / 2 - 200);
		g.drawString("Rating (top 20 games): " + (average() - standardDev()), getWidth() / 2 - 300, getHeight() / 2);
		g.drawString("Standard Deviation (top 20 games): " + standardDev(), getWidth() / 2 - 300, getHeight() / 2 + 100);
	}

	public double average() {
		double sum = 0;
		for (int i : scores) {
			sum += i;
		}
		return sum / scores.size();
	}

	public int max() {
		int max = scores.get(0);
		for (int i : scores) {
			if (i > max) max = i;
		}
		return max;
	}

	public double standardDev() {
		double avg = average();
		double sum = 0;
		for (int i : scores) {
			sum += (avg - i) * (avg - i);
		}
		return Math.sqrt(sum / scores.size());
	}

	public double median() {
		mergeSort(scores, 0, scores.size() - 1);
		if (scores.size() % 2 == 1) return scores.get(scores.size() / 2);
		return 0.5 * (scores.get(scores.size() / 2) + scores.get(scores.size() / 2 - 1));
	}

	/**
	 * This method sorts an array recursively.
	 * It splits up the array into multiple arrays with
	 * 2 elements or less, and then sorts each individual array.
	 * Then, it merges the arrays back together in a style similar to insertion sort.
	 * @param a The ArrayList of integers to be sorted.
	 * @param from The starting point of the array (so a sub-array).
	 * @param to The ending point of the sub-array.
	 * @return The number of steps taken to complete the sort.
	*/

	public static int mergeSort (ArrayList<Integer> a, int from, int to)
	{
		int steps = 0;
		if (to - from < 2) {
			if (to > from && a.get(from) > a.get(to)) {
				int temp = a.get(to);
				a.set(to, a.get(from));
				a.set(from, temp);
				steps += 8;
			}
			else if (a.size() == 2) steps++;
		}
		else
		{
			int middle = (from + to) / 2;
			steps++;
			
			steps += mergeSort(a, from, middle);
			steps += mergeSort(a, middle + 1, to);
			steps += merge(a, from, middle, to);
		}
		return steps;
	}

	/**
	 * This helper method merges two sub-arrays together by inserting the numbers
	 * in their right locations and creating a longer sub-array, with twice the elements.
	 * @param a The ArrayList of integers to be sorted.
	 * @param from The starting point of the array (so a sub-array).
	 * @param to The ending point of the sub-array.
	 * @return The number of steps taken to merge the two sub-arrays.
	*/

	public static int merge (ArrayList<Integer> a, int from, int middle, int to)
	{
		int steps = 0;
		int i = from, j = middle + 1, k = from;
		steps += 3;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int it = 0; it < a.size(); it++) {
			temp.add(0);
			steps += 3;
		}

		while (i <= middle && j <= to) {
			if (a.get(i) < a.get(j)) {
				temp.set(k, a.get(i));
				i++;
				steps += 7;
			}
			else {
				temp.set(k, a.get(j));
				j++;
				steps += 5;
			}
			k++;
			steps++;
		}
		while (i <= middle) {
			temp.set(k, a.get(i));
			i++;
			k++;
			steps += 5;
		}
		while (j <= to) {
			temp.set(k, a.get(j));
			j++;
			k++;
			steps += 5;
		}
		for (k = from; k <= to; k++) {
			a.set(k, temp.get(k));
			steps += 5;
		}
		return steps;
	}
}