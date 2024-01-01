package com.facialrecognition;

import org.opencv.core.Core;

public class Application {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("works");
	}

}
