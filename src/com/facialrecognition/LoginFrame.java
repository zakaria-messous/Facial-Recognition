package com.facialrecognition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class LoginFrame extends JFrame implements ActionListener{
	
	FacialRecognation facialRecognation;
	JLabel imageLabel;
	VideoCapture capture;
	Mat webcamImage;
	
	JTextField emailField;
	JTextField passwordField;
	JButton loginButton;
	JButton goToButton;
	
	public LoginFrame(FacialRecognation facialRecognation)
	{
		this.facialRecognation = facialRecognation;
		
		setTitle("Facial Recognition - Login");
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(640, 720));
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
        
        leftPanel.add(imageLabel);
        
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				capture.release();
				webcamImage.release();
				System.exit(0);
			}
		});
        
        setLayout(new GridLayout(1, 2));
        add(leftPanel);
        add(rightPanel);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	public void runMainLoop() {	
		ImageProcessor imageProcessor = new ImageProcessor();
		webcamImage = new Mat();
		Image tempImage;
		
		capture = new VideoCapture(0);
		capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
		capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
		if(capture.isOpened()) {
			while(true) {
				capture.read(webcamImage);
				if(!webcamImage.empty()) {
				 	int result = facialRecognation.detectAndDrawFace(webcamImage);
					tempImage = imageProcessor.toBufferImage(webcamImage);//Convert image to buffer from our imgProcess class
					
					ImageIcon imageIcon= new ImageIcon(tempImage, "Captured Video"); 
					imageLabel.setIcon(imageIcon);
					this.pack();
					
					if(result != -1)
					{
						//Login successful
						System.out.println("Login succesfful\nProfile: "+result);
						this.dispose();
						SwingUtilities.invokeLater(new Runnable() {
				            public void run() {
				            	new Profile(result);
				            }
				        });
						return;
					}
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Could not open webcam");
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==goToButton)
		{
			this.dispose();
			
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	RegisterFrame registerFrame = new RegisterFrame();
	            	//New thread for Camera input
	                new Thread(new Runnable() {
	        			@Override
	        			public void run()
	        			{
	        				registerFrame.startCamera();
	        			}
	        		}).start();
	            }
	        });
		}
		
		if(e.getSource()==loginButton)
		{
			String email = emailField.getText();
			String password = passwordField.getText();
			
			int result = CRUDOperations.login(email, password);
			
			if(result == -1) return;
			
			this.dispose();
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	new Profile(result);
	            }
	        });
		}
	}
}
