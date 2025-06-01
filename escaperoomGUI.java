import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class escaperoomGUI {
    private boolean hasKey = false;
    private boolean doorUnlocked = false;
    private JTextArea output;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new escaperoomGUI().createGUI());
    }

    public void createGUI() {
        JFrame frame = new JFrame("Escape the Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        output = new JTextArea();
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(output);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));

        JButton lookButton = new JButton("Look Around");
        JButton checkDoorButton = new JButton("Check Door");
        JButton useKeyButton = new JButton("Use Key");
        JButton quitButton = new JButton("Quit");

        lookButton.addActionListener(e -> lookAround());
        checkDoorButton.addActionListener(e -> checkDoor());
        useKeyButton.addActionListener(e -> useKey());
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(lookButton);
        buttonPanel.add(checkDoorButton);
        buttonPanel.add(useKeyButton);
        buttonPanel.add(quitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        print("You wake up in a locked room...");
    }

    private void lookAround() {
        if (!hasKey) {
            print("You find a key under the bed!");
            hasKey = true;
        } else {
            print("There's nothing else of interest.");
        }
    }

    private void checkDoor() {
        if (!doorUnlocked) {
            print("The door is locked.");
        } else {
            print("The door is open! You escaped!");
        }
    }

    private void useKey() {
        if (hasKey) {
            if (!doorUnlocked) {
                doorUnlocked = true;
                print("You use the key and unlock the door.");
            } else {
                print("The door is already unlocked.");
            }
        } else {
            print("You don't have a key.");
        }
    }

    private void print(String message) {
        output.append(message + "\n");
    }
}
