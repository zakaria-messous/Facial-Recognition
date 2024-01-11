package com.facialrecognition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame {
    private JTextField firstNameField, lastNameField, emailField;

    public RegisterForm() {
        setTitle("Registration Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // components
        JLabel lblFirstName = new JLabel("First Name:");
        JLabel lblLastName = new JLabel("Last Name:");
        JLabel lblEmail = new JLabel("Email:");

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(30);

        JButton btnRegister = new JButton("Register");

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                
                CRUDOperations.insertData(firstName, lastName, email);

                JOptionPane.showMessageDialog(RegisterForm.this, "Registration Successful!", 
                		"Registration Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Create layout
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(lblFirstName);
        panel.add(firstNameField);
        panel.add(lblLastName);
        panel.add(lastNameField);
        panel.add(lblEmail);
        panel.add(emailField);
        panel.add(btnRegister);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
