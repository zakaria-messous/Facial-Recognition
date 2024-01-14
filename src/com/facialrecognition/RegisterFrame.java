package com.facialrecognition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class RegisterFrame extends JFrame implements ActionListener{
	
	private int id;
	private JLabel cameraScreen;
	private VideoCapture capture;
	private Mat image;
	private boolean clicked = false;

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
        
        cameraScreen = new JLabel();
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
        
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        
        leftPanel.add(cameraScreen);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				capture.release();
				image.release();
				System.exit(0);
			}
		});
        
        setLayout(new GridLayout(1, 2));
        add(leftPanel);
        add(rightPanel);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	public void startCamera()
	{
		capture = new VideoCapture(0);
		image = new Mat();
		byte[] imageData;
		
		ImageIcon icon;
		
		while(true)
		{
			//read image to matrix
			capture.read(image);
			
			final MatOfByte buf = new MatOfByte();
			Imgcodecs.imencode(".jpg", image, buf);
			
			imageData = buf.toArray();
			//add to JLabel
			icon = new ImageIcon(imageData);
			cameraScreen.setIcon(icon);
			//capture and save to file
			if(clicked)
			{
				Imgcodecs.imwrite("images/"+id+".jpg", image);
				clicked = false;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==goToButton)
		{
			this.dispose();
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	FacialRecognation facialRecognation = new FacialRecognation();
					facialRecognation.loadCascade();
					LoginFrame loginFrame = new LoginFrame(facialRecognation);
	                
	                //New thread for Camera input
	                new Thread(new Runnable() {
	        			@Override
	        			public void run()
	        			{
	        				loginFrame.runMainLoop();
	        			}
	        		}).start();
	            }
	        });
		}
		
		if(e.getSource()==registerButton)
		{
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			
			String output = CRUDOperations.insertData(firstName, lastName, email, password);
			
			try
			{
				id = Integer.parseInt(output);
				if(id <= 0) throw new Exception("Invalid ID");
				clicked = true;
				
				JOptionPane.showMessageDialog(null, "Registered!", "Info", JOptionPane.INFORMATION_MESSAGE);
				
				this.dispose();
				SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            	FacialRecognation facialRecognation = new FacialRecognation();
						facialRecognation.loadCascade();
						LoginFrame loginFrame = new LoginFrame(facialRecognation);
		                
		                //New thread for Camera input
		                new Thread(new Runnable() {
		        			@Override
		        			public void run()
		        			{
		        				loginFrame.runMainLoop();
		        			}
		        		}).start();
		            }
		        });
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

