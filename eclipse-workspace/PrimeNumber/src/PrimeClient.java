import java.io.*;
import java.net.*;

public class PrimeClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 5000);
            System.out.println("Connected to the server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Prompt the user to enter a number
            System.out.print("Enter a number to check if it is prime: ");
            String input = reader.readLine();
            int number = Integer.parseInt(input);

            // Send the number to the server
            writer.println(number);

            // Read the server's response
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String response = serverReader.readLine();

            // Display the result
            System.out.println("Is " + number + " a prime number? " + response);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
