import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A Swing-based GUI application that allows the user to input a word and get the word occurrences from the server.
 */
public class WordCountGUI extends JFrame {

    private JTextArea outputTextArea;
    private JTextField inputField;
    private JButton countButton;

    /**
     * Constructs the WordCountGUI object.
     */
    public WordCountGUI() {
        setTitle("Word Occurrences");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea(20, 50);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField(20);
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countWords();
            }
        });
        add(inputField, BorderLayout.NORTH);

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
    public JTextField getInputField() {
    	return inputField;
    }
    /**
     * Sends the word input by the user to the server and displays the word occurrences in the JTextArea.
     */
    void countWords() {
        String word = inputField.getText().toLowerCase();

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                try (Socket clientSocket = new Socket("localhost", 8888)) {
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(word);

                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    String response = (String) ois.readObject();
                    ois.close();
                    oos.close();
                    return response;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    String response = get();
                    if (response != null) {
                        outputTextArea.setText(response);
                    } else {
                        outputTextArea.setText("Error occurred during word count.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        worker.execute();
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

    // Getter for the outputTextArea
    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }
}
