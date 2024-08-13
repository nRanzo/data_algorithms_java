import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LinkedPositionalListGUI extends JFrame {
    private LinkedPositionalList<String> list;
    private JTextField inputField;
    private JTextArea displayArea;
    private JButton addButton, removeButton, showButton;

    public LinkedPositionalListGUI() {
        list = new LinkedPositionalList<>();
        
        setTitle("LinkedPositionalList GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        inputField = new JTextField(20);
        panel.add(inputField, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        showButton = new JButton("Show");
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(showButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        add(panel);
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String item = inputField.getText();
                if (!item.isEmpty()) {
                    list.addLast(item);
                    inputField.setText("");
                    displayArea.append("Added: " + item + "\n");
                }
            }
        });
        
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!list.isEmpty()) {
                        Position<String> firstPosition = list.first();
                        if (firstPosition != null) {
                            String removed = list.remove(firstPosition);
                            displayArea.append("Removed: " + removed + "\n");
                        } else {
                            displayArea.append("Error: Unable to get first position\n");
                        }
                    } else {
                        displayArea.append("Error: List is empty\n");
                    }
                } catch (IllegalArgumentException ex) {
                    displayArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });
        
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("");
                if (list.isEmpty()) {
                    displayArea.append("List is empty\n");
                } else {
                    Position<String> current = list.first();
                    while (current != null) {
                        displayArea.append(current.getElement() + "\n");
                        current = list.after(current);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LinkedPositionalListGUI().setVisible(true);
            }
        });
    }
}