import javax.swing.SwingUtilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextAnalyzer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TextAnalyzerGUI analyzerGUI = new TextAnalyzerGUI();
                analyzerGUI.setVisible(true);
            }
        });
        String filePath = "/Users/colinunderwood/Documents/Raven.txt";
        List<Map.Entry<String, Integer>> wordFreq = analyzeFile(filePath);

        // Output the word frequencies
        for (int i = 0; i < 20 && i < wordFreq.size(); i++) {
            Map.Entry<String, Integer> entry = wordFreq.get(i);
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static List<Map.Entry<String, Integer>> analyzeFile(String filePath) {
        Map<String, Integer> wordFreq = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s.;,?:!()\"]+");

                for (String word : words) {
                    word = word.replaceAll("[^\\w]", "").toLowerCase();

                    if (!word.isEmpty()) {
                        wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the word frequencies in descending order
        List<Map.Entry<String, Integer>> sortedWordFreq = new ArrayList<>(wordFreq.entrySet());
        sortedWordFreq.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        return sortedWordFreq;
    }
}
