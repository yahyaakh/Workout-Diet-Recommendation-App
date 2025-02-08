package com.gymproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class Login {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    
    private static final String WRITABLE_USERS_FILE = "data/users.txt";

    public Login() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 30);
        frame.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 150, 100, 30);
        frame.add(registerButton);

        // Login Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    String userDataLine = getUserData(username, password);
                    if (userDataLine != null) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        frame.dispose(); // Close login window
                        loadUserData(userDataLine);
                        new App(); // Launch your existing App
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Registration Action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid username and password.");
                    return;
                }
                try {
                    if (userExists(username)) {
                        JOptionPane.showMessageDialog(frame, "Username already exists.");
                    } else {
                        frame.dispose(); // Close login window
                        new App(username, password); // Launch App for registration
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

    public static boolean userExists(String username) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Login.class.getClassLoader().getResourceAsStream("users.txt"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[0].equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

public static String getUserData(String username, String password) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(
            Objects.requireNonNull(Login.class.getClassLoader().getResourceAsStream("users.txt"))))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(username) && parts[1].equals(password)) {
                return line;
            }
        }
    }
    return null;
}


    public static void loadUserData(String userDataLine) {
        String[] data = userDataLine.split(",");
        App.userData = new String[]{data[2], data[3], data[4], data[5]};
        App.selections = new String[]{data[6], data[7], data[8]};
    }
}
