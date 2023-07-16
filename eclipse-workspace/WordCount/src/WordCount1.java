import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A utility class that counts the occurrences of words in a text file and provides sorting functionalities.
 */
public class WordCount1 {

    /**
     * Counts the occurrences of words in the specified file and returns a map of word occurrences.
     *
     * @param filePath The path of the file to read.
     * @return A map containing words as keys and their respective occurrences as values.
     */
    public static Map<String, Integer> buildWordMap(String filePath) {
        Map<String, Integer> wordMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return wordMap;
    }

    /**
     * Sorts the word map entries in decreasing order of their occurrence counts.
     *
     * @param wordMap The map of words and their occurrences.
     * @return A list of map entries sorted by value in decreasing order.
     */
    public static List<Map.Entry<String, Integer>> sortByValueInDecreasingOrder(Map<String, Integer> wordMap) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordMap.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }

    /**
     * The entry point of the utility.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        String filePath = "/Users/colinunderwood/Documents/Raven.txt";
        Map<String, Integer> wordMap = buildWordMap(filePath);
        List<Map.Entry<String, Integer>> list = sortByValueInDecreasingOrder(wordMap);

        System.out.println("List of top 20 most repeated words:");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " ~ " + entry.getValue());
                count++;
                if (count == 20) {
                    break;
                }
            }
        }
    }
}
