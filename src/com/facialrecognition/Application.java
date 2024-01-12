package com.facialrecognition;

import javax.swing.SwingUtilities;

import org.opencv.core.Core;

public class Application {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();
            }
        });
	}

}
