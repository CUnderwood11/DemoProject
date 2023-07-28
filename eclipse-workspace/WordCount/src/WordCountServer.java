import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * A server application that listens on a specified port and provides word occurrences in response to client requests.
 */
public class WordCountServer {

    private WordCount1 wordCounter;

    /**
     * Constructs the WordCountServer object and initializes the WordCount1 instance.
     */
    public WordCountServer() {
        wordCounter = new WordCount1();
    }

    /**
     * Starts the WordCountServer on the specified port and handles client requests.
     *
     * @param port The port number to listen on.
     */
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                    // Read the word sent by the client
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    String word = (String) ois.readObject();

                    // Build the word map from the file and get sorted list of word occurrences
                    Map<String, Integer> wordMap = wordCounter.buildWordMapFromFile("/Users/colinunderwood/Documents/Raven.txt");
                    List<Map.Entry<String, Integer>> sortedList = wordCounter.sortByValueInDecreasingOrder(wordMap);

                    // Prepare the response to send back to the client
                    StringBuilder sb = new StringBuilder();
                    sb.append("List of top 20 most repeated words:\n");
                    int count = 0;
                    for (Map.Entry<String, Integer> entry : sortedList) {
                        if (entry.getValue() > 1 && entry.getKey().contains(word)) {
                            sb.append(entry.getKey()).append(" ~ ").append(entry.getValue()).append("\n");
                            count++;
                            if (count == 20) {
                                break;
                            }
                        }
                    }

                    // Send the response back to the client
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(sb.toString());

                    ois.close();
                    oos.close();
                    System.out.println("Response sent to client.");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of the server application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        WordCountServer server = new WordCountServer();
        server.start(8888);
    }
}
