import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * A class that interacts with a MySQL database to store words and their occurrences.
 */
public class WordCount1 {

    private static final String DB_URL = "jdbc:mysql://localhost/wordoccurrences";
    private static final String USER = "root";
    private static final String PASSWORD = "123456Yes";

    private Connection connection;

    /**
     * Constructs a WordCount1 object and establishes a connection to the database.
     */
    public WordCount1() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a word to the database.
     *
     * @param word The word to be added.
     */
    public void addWord(String word) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO word (words) VALUES (?)");
            ps.setString(1, word);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all words from the database and calculates their frequency.
     *
     * @return A map containing the word as the key and its frequency as the value.
     */
    public Map<String, Integer> calculateWordFrequency() {
        Map<String, Integer> frequency = new LinkedHashMap<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM word");
            while (rs.next()) {
                String word = rs.getString(1);
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frequency;
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // New method to build word map from file
    /**
     * Builds a word map from a text file.
     *
     * @param filePath The path to the text file.
     * @return A map containing words as keys and their occurrences as values.
     */
    public Map<String, Integer> buildWordMapFromFile(String filePath) {
        Map<String, Integer> wordMap = new LinkedHashMap<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wordMap;
    }
    
    public String getDbUrl() {
    	return DB_URL;
    }
    
    public String getUser() {
    	return USER;
    }
    
    public String getPassword() {
    	return PASSWORD;
    }

    // New method to sort word map by frequency in decreasing order
    /**
     * Sorts a word map by frequency in decreasing order.
     *
     * @param wordMap The map to be sorted.
     * @return A list of map entries sorted by frequency in decreasing order.
     */
    public List<Map.Entry<String, Integer>> sortByValueInDecreasingOrder(Map<String, Integer> wordMap) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return entryList;
    }
}
