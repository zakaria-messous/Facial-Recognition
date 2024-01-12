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

public class Profile extends JFrame implements ActionListener{
	
	int id;
	JTextField firstNameField;
	JTextField lastNameField;
	JTextField emailField;
	JTextField passwordField;
	JButton updateButton;
	JButton logoutButton;
	
	public Profile(int id)
	{
		this.id = id;
		
		setTitle("Facial Recognition - Profile");
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Get data from db
        String data = CRUDOperations.selectProfileData(id);
        String firstname = "";
        String lastname = "";
        String email = "";
        String password = "";
        if(!data.equals("error"))
        {
        	String[] dataArr = data.split(" ");
        	firstname = dataArr[0];
        	lastname = dataArr[1];
        	email = dataArr[2];
        	password = dataArr[3];
        }
        
        // components
        JLabel lblTitle = new JLabel("Profile");
        lblTitle.setFont(new Font(null, Font.BOLD, 40));
        lblTitle.setPreferredSize(new Dimension(130, 120));
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
        firstNameField.setText(firstname);
        firstNameField.setPreferredSize(new Dimension(340, 30));
        lastNameField = new JTextField();
        lastNameField.setText(lastname);
        lastNameField.setPreferredSize(new Dimension(340, 30));
        emailField = new JTextField();
        emailField.setText(email);
        emailField.setPreferredSize(new Dimension(340, 30));
        passwordField = new JPasswordField();
        passwordField.setText(password);
        passwordField.setPreferredSize(new Dimension(340, 30));
        
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        updateButton.setPreferredSize(new Dimension(340, 32));
        
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setPreferredSize(new Dimension(340, 32));
        
        // Create panels
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        JPanel rightPanel = new JPanel(null);
        //rightPanel.setBackground(Color.CYAN);
        
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //loginPanel.setBackground(Color.GRAY);
        profilePanel.setSize(new Dimension(360, 600));
        profilePanel.setBounds(140, 50, 360, 600);
        
        profilePanel.add(lblTitle);
        profilePanel.add(lblFirstName);
        profilePanel.add(firstNameField);
        profilePanel.add(lblLastName);
        profilePanel.add(lastNameField);
        profilePanel.add(lblEmail);
        profilePanel.add(emailField);
        profilePanel.add(lblPassword);
        profilePanel.add(passwordField);
        profilePanel.add(lblSpace);
        profilePanel.add(updateButton);
        profilePanel.add(logoutButton);
        
        rightPanel.add(profilePanel);
        
        setLayout(new GridLayout(1, 2));
        add(leftPanel);
        add(rightPanel);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==updateButton)
		{
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			
			String output = CRUDOperations.updateData(this.id, firstName, lastName, email, password);
			if(output == "-1")
				JOptionPane.showMessageDialog(null, "Registered!", "Info", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
		}
		if(e.getSource()==logoutButton)
		{
			this.dispose();
			LoginFrame login = new LoginFrame();
		}
	}
}

