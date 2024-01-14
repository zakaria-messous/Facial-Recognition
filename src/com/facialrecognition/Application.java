package com.facialrecognition;

import javax.swing.SwingUtilities;

import org.opencv.core.Core;

public class Application {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
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

}
