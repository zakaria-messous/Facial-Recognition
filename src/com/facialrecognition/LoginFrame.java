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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements ActionListener{
	
	JTextField emailField;
	JTextField passwordField;
	JButton loginButton;
	JButton goToButton;
	
	public LoginFrame()
	{
		setTitle("Facial Recognition - Login");
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // components
        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font(null, Font.BOLD, 40));
        lblTitle.setPreferredSize(new Dimension(120, 120));
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setPreferredSize(new Dimension(340, 30));
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setPreferredSize(new Dimension(340, 30));
        JLabel lblSpace = new JLabel("      ");
        
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(340, 30));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(340, 30));
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setPreferredSize(new Dimension(340, 32));
        
        goToButton = new JButton("Don't have an account? Register");
        goToButton.addActionListener(this);
        goToButton.setPreferredSize(new Dimension(340, 32));
        goToButton.setBorder(null);
        goToButton.setBackground(new Color(0, 0, 0, 0));
        
        // Create panels
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        JPanel rightPanel = new JPanel(null);
        //rightPanel.setBackground(Color.CYAN);
        
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //loginPanel.setBackground(Color.GRAY);
        loginPanel.setSize(new Dimension(360, 400));
        loginPanel.setBounds(140, 90, 360, 400);
        
        loginPanel.add(lblTitle);
        loginPanel.add(lblEmail);
        loginPanel.add(emailField);
        loginPanel.add(lblPassword);
        loginPanel.add(passwordField);
        loginPanel.add(lblSpace);
        loginPanel.add(loginButton);
        loginPanel.add(goToButton);
        
        rightPanel.add(loginPanel);
        
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
			RegisterFrame register = new RegisterFrame();
		}
		
		if(e.getSource()==loginButton)
		{
			String email = emailField.getText();
			String password = passwordField.getText();
			
			int result = CRUDOperations.login(email, password);
			
			if(result == -1) return;
			
			this.dispose();
			Profile profile = new Profile(result);
		}
	}
}
