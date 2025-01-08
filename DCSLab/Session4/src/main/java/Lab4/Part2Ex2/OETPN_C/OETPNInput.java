package main.java.Lab4.Part2Ex2.OETPN_C;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

//For OETPN-c 
public class OETPNInput {
	public static void main(String[] args) throws InterruptedException, IOException {
		File file = new File("C:/Users/deniz/Downloads/Lab4/Lab4/Part2Ex1_2/OETPNInput.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());
		Float command = 0.55F;

		for (float i = 0; i < 100; i++) {
			if (i > 50)
				command = 0.35f;

			fw.write("r:" + command + "F\n");
		}
		fw.close();
		System.out.println("Done!");
	}
}
