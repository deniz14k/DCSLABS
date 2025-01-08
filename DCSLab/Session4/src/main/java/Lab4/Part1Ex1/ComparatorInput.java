package main.java.Lab4.Part1Ex1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ComparatorInput {
	public static void main(String[] args) throws InterruptedException, IOException {

		// File setup
		File file = new File("C:/Users/deniz/Downloads/Lab4/Lab4/Part1Ex1/comparator.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());

		// Generate sine and cosine signals for P0 and P1
		for (int i = 0; i < 100; i++) {
			double angle = Math.toRadians(i * 3.6); // Scale i to [0, 360 degrees]
			float f1 = (float) Math.sin(angle); // Sine value for P0
			float f2 = (float) Math.cos(angle); // Cosine value for P1

			// Write the values in the required format to the file
			fw.write("P0:" + f1 + "F" + "," + "P1:" + f2 + "F\n");
		}

		fw.close();
		System.out.println("Done! Sine and cosine signals written to comparator.txt.");
	}
}
