package rvt.TodoTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInterface extends JFrame {
    private TodoList list;
    private JTextField taskInputField;
    private JTextField removeInputField;
    private JTextArea taskDisplayArea;
    private JButton addButton;
    private JButton removeButton;
    private JButton listButton;
    private JButton clearButton;

    public UserInterface() {
        list = new TodoList();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Todo List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Task"));

        inputPanel.add(new JLabel("Task Name:"));
        taskInputField = new JTextField();
        inputPanel.add(taskInputField);

        inputPanel.add(new JLabel("Remove Task #:"));
        removeInputField = new JTextField();
        inputPanel.add(removeInputField);

        addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        inputPanel.add(addButton);

        removeButton = new JButton("Remove Task");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });
        inputPanel.add(removeButton);

        listButton = new JButton("Refresh List");
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDisplay();
            }
        });
        inputPanel.add(listButton);

        clearButton = new JButton("Clear Display");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskDisplayArea.setText("");
            }
        });
        inputPanel.add(clearButton);

        // Display panel
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));

        taskDisplayArea = new JTextArea();
        taskDisplayArea.setEditable(false);
        taskDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        taskDisplayArea.setLineWrap(true);
        taskDisplayArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(taskDisplayArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(displayPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Initialize display
        refreshDisplay();

        setVisible(true);
    }

    private void addTask() {
        String taskName = taskInputField.getText().trim();
        if (taskName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a task name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        list.add(taskName);
        taskInputField.setText("");
        refreshDisplay();
        JOptionPane.showMessageDialog(this, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeTask() {
        String input = removeInputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a task number to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int taskNumber = Integer.parseInt(input);
            list.remove(taskNumber);
            removeInputField.setText("");
            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Task removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Tasks:\n");
        sb.append("=".repeat(30)).append("\n");
        sb.append(list.getTasksAsString());
        taskDisplayArea.setText(sb.toString());
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserInterface();
            }
        });
    }
}