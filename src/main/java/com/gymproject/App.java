package com.gymproject;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class App implements ActionListener {
    
    
    // For GUI
    private JFrame frame = new JFrame(); 
    private JPanel panel = new JPanel();

    private JButton SubmitButton;
    private JComboBox<String> comboBox;
    private JComboBox<String> comboBox2; 
    private JComboBox<String> comboBox3;

    public static String[] selections = new String[3]; // For ComboBox selections
    public static String[] userData = new String[4];   // For user data (username, age, weight, height)

    private JTextField userText;
    private JTextField userAgeField;
    private JTextField userWeightField;
    private JTextField userHeightField;

    private static final String USERS_FILE = "src/main/resources/users.txt";


    private String username;
    private String password;

    public App() {
        setupGUI();
    }

    // Constructor used during registration
    public App(String username, String password) {
        this.username = username;
        this.password = password;
        setupGUI();
    }

    private void setupGUI() {
        frame.setSize(900,700);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gym Application");
        ImageIcon image = new ImageIcon("GYMLOGO.jpg");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(Color.white);

        panel.setLayout(null);

        // Username Label and Field
        JLabel userlabel = new JLabel("USER NAME");
        userlabel.setBounds(10,20,80,25);
        panel.add(userlabel);
        userText = new JTextField(20);
        userText.setBounds(150,20,165,25);
        panel.add(userText); 

        JLabel userAge = new JLabel("USER AGE");
        userAge.setBounds(10,50,80,25);
        panel.add(userAge);
        userAgeField = new JTextField(20);
        userAgeField.setBounds(150,50,165,25);
        panel.add(userAgeField); 

        JLabel userWeight = new JLabel("USER WEIGHT");
        userWeight.setBounds(10,80,200,25);
        panel.add(userWeight);
        userWeightField = new JTextField(20);
        userWeightField.setBounds(150,80,165,25);
        panel.add(userWeightField); 

        JLabel userHeight = new JLabel("USER HEIGHT");
        userHeight.setBounds(10,110,200,25);
        panel.add(userHeight);
        userHeightField = new JTextField(20);
        userHeightField.setBounds(150,110,165,25);
        panel.add(userHeightField); 

        JLabel userGender = new JLabel("USER GENDER");
        userGender.setBounds(10,140,200,25);
        panel.add(userGender);
        String[] list = { "male", "female" };
        comboBox = new JComboBox<>(list);
        comboBox.setBounds(150,140,165,25);
        panel.add(comboBox);

        // Goals ComboBox
        JLabel userGoals = new JLabel("USER Goals");
        userGoals.setBounds(10,170,200,25);
        panel.add(userGoals);
        String[] Goalslist = { "losing weight", "building muscle" };
        comboBox2 = new JComboBox<>(Goalslist);
        comboBox2.setBounds(150,170,165,25);
        panel.add(comboBox2);

        // Activity Level ComboBox
        JLabel userActivityLevel = new JLabel("USER Activity Level");
        userActivityLevel.setBounds(10,200,200,25);
        panel.add(userActivityLevel);
        String[] Levellist = { "active", "extra active" };
        comboBox3 = new JComboBox<>(Levellist);
        comboBox3.setBounds(150,200,165,25);
        panel.add(comboBox3);

        // Submit Button
        SubmitButton = new JButton("Submit");
        SubmitButton.setBounds(100,230,165,25);
        panel.add(SubmitButton);

        // Output Area for Recommendations
        JTextArea outputArea = new JTextArea(3, 30);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBounds(20,290,700,150);
        panel.add(outputArea);

        // Assign ComboBox selections to global array on selection
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selections[0] = ((String) comboBox.getSelectedItem()).trim().toLowerCase();
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selections[1] = ((String) comboBox2.getSelectedItem()).trim().toLowerCase();
            }
        });

        comboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selections[2] = ((String) comboBox3.getSelectedItem()).trim().toLowerCase();
            }
        });

        // Pre-fill fields if userData and selections are populated
        prefillFields();

        // Store user input on Submit
        SubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userData[0] = userText.getText();
                userData[1] = userAgeField.getText();
                userData[2] = userWeightField.getText();
                userData[3] = userHeightField.getText();

                selections[0] = (String) comboBox.getSelectedItem();
                selections[1] = (String) comboBox2.getSelectedItem();
                selections[2] = (String) comboBox3.getSelectedItem();

                System.out.println("Username: " + userData[0]);
                System.out.println("Age: " + userData[1]);
                System.out.println("Weight: " + userData[2]);
                System.out.println("Height: " + userData[3]);
                System.out.println(selections[0]);
                System.out.println(selections[1]);
                System.out.println(selections[2]);

                // Save data if registering
                if (username != null && password != null) {
                    try {
                        saveUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Output Button
        JButton outputButton = new JButton("Show Output");
        outputButton.setBounds(230,260,165,25);
        panel.add(outputButton);

        // Generate and display recommendations based on case
        outputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int caseNumber = cases2();
                String outputText = "";

                switch (caseNumber) {
                    case 1:
                        outputText =  readFileToString("text1.txt");
                        break;
                    case 2:
                        outputText = readFileToString("text2.txt");
                        break;
                    case 3:
                        outputText = readFileToString("text3.txt");
                        break;
                    case 4:
                        outputText = readFileToString("text4.txt");
                        break;
                    case 5:
                        outputText = readFileToString("text5.txt");
                        break;
                    case 6:
                        outputText = readFileToString("text6.txt");
                        break;
                    case 7:
                        outputText = readFileToString("text7.txt");
                        break;
                    case 8:
                        outputText = readFileToString("text8.txt");
                        break;
                    case 9:
                        outputText = readFileToString("text9.txt");
                        break;
                    case 10:
                        outputText = readFileToString("text10.txt");
                        break;
                    default:
                        outputText = "No specific match found. For general fitness...";
                        break;
                }

                // Display recommendations in output area
                outputArea.setText(outputText);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void prefillFields() {
        if (userData[0] != null) {
            userText.setText(userData[0]);
        }
        if (userData[1] != null) {
            userAgeField.setText(userData[1]);
        }
        if (userData[2] != null) {
            userWeightField.setText(userData[2]);
        }
        if (userData[3] != null) {
            userHeightField.setText(userData[3]);
        }
        if (selections[0] != null) {
            comboBox.setSelectedItem(selections[0]);
        }
        if (selections[1] != null) {
            comboBox2.setSelectedItem(selections[1]);
        }
        if (selections[2] != null) {
            comboBox3.setSelectedItem(selections[2]);
        }
    }

    // Save user data to users.txt
    void saveUserData() throws IOException {
        try (FileWriter fw = new FileWriter(USERS_FILE, true)) { // 'true' appends data
            fw.write(username + "," + password + "," +
                     userData[0] + "," + userData[1] + "," + userData[2] + "," + userData[3] + "," +
                     selections[0] + "," + selections[1] + "," + selections[2] + "\n");
        }
    }



public static String readFileToString(String fileName) {
    try (InputStream is = App.class.getClassLoader().getResourceAsStream(fileName)) {
        if (is == null) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toString();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}



    public static void main(String[] args) {
        new App(); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public int cases2() {
        int weight;
        try {
            weight = Integer.parseInt(userData[2].trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid weight input: " + userData[2]);
            return 0; // Return default if parsing fails
        }
    
        System.out.println("Selections: " + selections[0] + ", " + selections[1] + ", " + selections[2]);
        System.out.println("Weight: " + weight);
    
        // Male Cases
        if ("male".equals(selections[0])) {
            if (weight >= 50 && weight <= 70) { // Lightweight
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 1;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 1;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 2;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 2;
            } else if (weight >= 71 && weight <= 100) { // Medium weight
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 9;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 9;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 2;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 2;
            } else if (weight >= 101 && weight <= 130) { // Heavyweight
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 3;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 3;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 2;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 2;
            } else if (weight > 130) { // Obese
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 4;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 4;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 4;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 4;
            }
        }
    
        // Female Cases
        if ("female".equals(selections[0])) {
            if (weight >= 50 && weight <= 70) { // Lightweight
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 10;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 10;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 5;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 5;
            } else if (weight >= 71 && weight <= 100) { // Medium weight
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 6;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 6;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 5;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 5;
            } else if (weight >= 101 && weight <= 130) { // Heavyweight
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 6;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 6;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 7;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 7;
            } else if (weight > 130) { // Obese
                if ("losing weight".equals(selections[1]) && "active".equals(selections[2])) return 8;
                if ("losing weight".equals(selections[1]) && "extra active".equals(selections[2])) return 8;
                if ("building muscle".equals(selections[1]) && "active".equals(selections[2])) return 8;
                if ("building muscle".equals(selections[1]) && "extra active".equals(selections[2])) return 8;
            }
        }
    
        return 0; // Default case if no match found
    }

}







