import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class TextAnalyzerGUI extends JFrame {

    private static final long serialVersionUID = 1L;
	private JTextArea filePathTextArea;
    private JTextArea wordTextArea;
    private JButton analyzeButton;
    private JTextArea resultArea;

    public TextAnalyzerGUI() {
        setTitle("Text Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        filePathTextArea = new JTextArea();
        JScrollPane filePathScrollPane = new JScrollPane(filePathTextArea);

        wordTextArea = new JTextArea();
        JScrollPane wordScrollPane = new JScrollPane(wordTextArea);

        analyzeButton = new JButton("Analyze");
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeFile();
            }
        });

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.add(filePathScrollPane);
        inputPanel.add(wordScrollPane);
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(analyzeButton, BorderLayout.SOUTH);
        contentPane.add(resultScrollPane, BorderLayout.EAST);

        setContentPane(contentPane);
    }

    private void analyzeFile() {
        String filePath = filePathTextArea.getText();
        String word = wordTextArea.getText().toLowerCase();
        List<Map.Entry<String, Integer>> wordFreq = TextAnalyzer.analyzeFile(filePath);

        // Find the frequency of the specified word
        int frequency = 0;
        for (Map.Entry<String, Integer> entry : wordFreq) {
            if (entry.getKey().equals(word)) {
                frequency = entry.getValue();
                break;
            }
        }

        // Display the frequency in the result area
        resultArea.setText("Frequency of \"" + word + "\": " + frequency);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TextAnalyzerGUI analyzerGUI = new TextAnalyzerGUI();
                analyzerGUI.setVisible(true);
            }
        });
    }
}
