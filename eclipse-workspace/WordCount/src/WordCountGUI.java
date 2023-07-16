import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

/**
 * A Swing-based GUI application that counts the occurrences of words in a text file and displays the top 20 most repeated words.
 */
public class WordCountGUI extends JFrame {

    private JTextArea outputTextArea;
    private JButton countButton;

    /**
     * Constructs the WordCountGUI object.
     */
    public WordCountGUI() {
        setTitle("Word Occurrences");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        countButton = new JButton("Count Words");
        countButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countWords();
            }
        });
        add(countButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Counts the occurrences of words in the selected file and displays the top 20 most repeated words in the GUI.
     */
    private void countWords() {
        String filePath = "/Users/colinunderwood/Documents/Raven.txt";
        Map<String, Integer> wordMap = WordCount1.buildWordMap(filePath);
        List<Map.Entry<String, Integer>> list = WordCount1.sortByValueInDecreasingOrder(wordMap);

        StringBuilder sb = new StringBuilder();
        sb.append("List of top 20 most repeated words:\n");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (entry.getValue() > 1) {
                sb.append(entry.getKey()).append(" ~ ").append(entry.getValue()).append("\n");
                count++;
                if (count == 20) {
                    break;
                }
            }
        }

        outputTextArea.setText(sb.toString());
    }

    /**
     * The entry point of the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WordCountGUI wordCountGUI = new WordCountGUI();
                wordCountGUI.setVisible(true);
            }
        });
    }
}
