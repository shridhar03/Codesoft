import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordCounterGUI extends JFrame {
    private JTextArea textArea;
    private JLabel countLabel;

    public WordCounterGUI() {
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton countButton = new JButton("Count Words");
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                int wordCount = countWords(text);
                countLabel.setText("Word Count: " + wordCount);
            }
        });

        JButton fileButton = new JButton("Open File");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(WordCounterGUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        String fileContent = readFile(filePath);
                        textArea.setText(fileContent);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(WordCounterGUI.this,
                                "Error reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        countLabel = new JLabel("Word Count: 0");
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(countButton);
        buttonPanel.add(fileButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(countLabel, BorderLayout.NORTH);

        add(panel);
        setVisible(true);
    }

    private int countWords(String text) {
        if (text.isEmpty())
            return 0;
        String[] words = text.split("\\W+");
        return words.length;
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordCounterGUI();
            }
        });
    }
}
