import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WordCount1Test {

    private static WordCount1 wordCount;

    @BeforeClass
    public static void setUp() {
        // Create a WordCount1 object and connect to the database
        wordCount = new WordCount1();
    }

    @AfterClass
    public static void tearDown() {
        // Close the connection to the database
        wordCount.closeConnection();
    }

    @Test
    public void testSortByValueInDecreasingOrder() {
        // Create a sample word map for testing
        Map<String, Integer> wordMap = Map.of(
            "the", 56,
            "and", 38,
            "word", 25,
            "apple", 12,
            "banana", 8
        );

        // Sort the word map using the sortByValueInDecreasingOrder method
        List<Map.Entry<String, Integer>> sortedList = wordCount.sortByValueInDecreasingOrder(wordMap);

        // Create an array with the expected order of words based on frequency
        String[] expectedOrder = { "the", "and", "word", "apple", "banana" };

        // Verify that the sorting is correct
        for (int i = 0; i < expectedOrder.length; i++) {
            assertEquals(expectedOrder[i], sortedList.get(i).getKey());
        }
    }
}
