import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PrimeServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Listening on port 5000...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String input = reader.readLine();
                String[] numbers = input.split(" ");
                StringBuilder response = new StringBuilder();

                for (String num : numbers) {
                    try {
                        int number = Integer.parseInt(num.trim());
                        String result = isPrime(number) ? "YES" : "NO";
                        response.append(number).append(": ").append(result).append("\n");
                    } catch (NumberFormatException e) {
                        response.append("Invalid number: ").append(num).append("\n");
                    }
                }

                writer.println(response.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean isPrime(int number) {
            if (number <= 1)
                return false;
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
