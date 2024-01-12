package com.facialrecognition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterFrame extends JFrame implements ActionListener{
	
	JTextField firstNameField;
	JTextField lastNameField;
	JTextField emailField;
	JTextField passwordField;
	JButton registerButton;
	JButton goToButton;
	
	public RegisterFrame()
	{
		setTitle("Facial Recognition - Registration");
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // components
        JLabel lblTitle = new JLabel("Registration");
        lblTitle.setFont(new Font(null, Font.BOLD, 40));
        lblTitle.setPreferredSize(new Dimension(230, 120));
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setPreferredSize(new Dimension(340, 30));
        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setPreferredSize(new Dimension(340, 30));
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setPreferredSize(new Dimension(340, 30));
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setPreferredSize(new Dimension(340, 30));
        JLabel lblSpace = new JLabel("      ");
        
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(340, 30));
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(340, 30));
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(340, 30));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(340, 30));
        
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setPreferredSize(new Dimension(340, 32));
        
        goToButton = new JButton("Already have an account? Login");
        goToButton.addActionListener(this);
        goToButton.setPreferredSize(new Dimension(340, 32));
        goToButton.setBorder(null);
        goToButton.setBackground(new Color(0, 0, 0, 0));
        
        // Create panels
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        JPanel rightPanel = new JPanel(null);
        //rightPanel.setBackground(Color.CYAN);
        
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //loginPanel.setBackground(Color.GRAY);
        registerPanel.setSize(new Dimension(360, 600));
        registerPanel.setBounds(140, 50, 360, 600);
        
        registerPanel.add(lblTitle);
        registerPanel.add(lblFirstName);
        registerPanel.add(firstNameField);
        registerPanel.add(lblLastName);
        registerPanel.add(lastNameField);
        registerPanel.add(lblEmail);
        registerPanel.add(emailField);
        registerPanel.add(lblPassword);
        registerPanel.add(passwordField);
        registerPanel.add(lblSpace);
        registerPanel.add(registerButton);
        registerPanel.add(goToButton);
        
        rightPanel.add(registerPanel);
        
        setLayout(new GridLayout(1, 2));
        add(leftPanel);
        add(rightPanel);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==goToButton)
		{
			this.dispose();
			LoginFrame login = new LoginFrame();
		}
		
		if(e.getSource()==registerButton)
		{
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			
			String output = CRUDOperations.insertData(firstName, lastName, email, password);
			if(output == "-1")
			{
				JOptionPane.showMessageDialog(null, "Registered!", "Info", JOptionPane.INFORMATION_MESSAGE);
				
				this.dispose();
				LoginFrame login = new LoginFrame();
			}
			else
				JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

