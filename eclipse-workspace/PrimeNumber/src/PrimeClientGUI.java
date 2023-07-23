import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PrimeClientGUI extends JFrame {

    private JTextField inputField;
    private JButton checkButton;
    private JLabel resultLabel;

    public PrimeClientGUI() {
        setTitle("Prime Number Checker");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        inputField = new JTextField();
        checkButton = new JButton("Check Prime");
        resultLabel = new JLabel("Result: ");

        add(inputField);
        add(checkButton);
        add(resultLabel);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    try (Socket socket = new Socket("localhost", 5000)) {
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        writer.println(input);

                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String result = reader.readLine();

                        if (result.equals("YES")) {
                            resultLabel.setText("Result: " + input + " is a prime number.");
                        } else {
                            resultLabel.setText("Result: " + input + " is not a prime number.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrimeClientGUI gui = new PrimeClientGUI();
                gui.setVisible(true);
            }
        });
    }
}
