package lab.scd.net.socket;

import java.io.*;
import java.net.*;

public class CalculatorServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1900); // Ascultă pe portul 1900
            System.out.println("Serverul calculator asteapta conexiuni...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Acceptă conexiunea cu un client
                System.out.println("Client conectat.");

                // Creează fluxurile de intrare și ieșire
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

                // Citește datele trimise de client
                String input = in.readLine();
                System.out.println("Serverul a primit: " + input);

                // Procesează datele (separă numerele și operatorul)
                String[] parts = input.split(" ");
                double num1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double num2 = Double.parseDouble(parts[2]);

                // Calculează rezultatul
                double result = 0;
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            out.println("Eroare: Împărțire la zero!");
                            clientSocket.close();
                            continue;
                        }
                        break;
                    default:
                        out.println("Eroare: Operator necunoscut!");
                        clientSocket.close();
                        continue;
                }

                // Trimite rezultatul înapoi la client
                out.println("Rezultatul este: " + result);

                // Închide conexiunea cu clientul
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) serverSocket.close();
        }
    }
}