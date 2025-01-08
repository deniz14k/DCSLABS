package main.java.Lab4.Part2Ex2.OETPN_C;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

//For testing the PI controller alone
public class PIControllerInput {
	public static void main(String[] args) throws InterruptedException, IOException {
		File file = new File("C:/Users/deniz/Downloads/Lab4/Lab4/Part2Ex1_2/PIController.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());
		Float a, b, c, d, reference, currentStatus;
		a = 0.5f;
		b = 0.7f;
		currentStatus = 0.0f;
		reference = 0.55f;
		for (float i = 0; i < 100; i++) {
			if (i > 50)
				reference = 0.35f;
			currentStatus = a * currentStatus + b * reference;
			fw.write("P2:" + currentStatus + "F" + "," + "P4:" + reference + "F\n"); 
		}
		fw.close();
		System.out.println("Done!");
	}
}