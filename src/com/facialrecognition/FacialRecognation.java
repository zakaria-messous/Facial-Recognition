package com.facialrecognition;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.imgcodecs.Imgcodecs;

public class FacialRecognation {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private CascadeClassifier faceDetector;

	//test
	private String findMatchingImage(Mat detectedFace) {
		// Obtenez la liste des fichiers dans le dossier imgs
		File folder = new File("images");
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					Mat referenceFace = loadImage(file.getAbsolutePath());

					// Calculez la différence moyenne des histogrammes
					double difference = compareHistograms(detectedFace, referenceFace);

					// Si la différence est inférieure à un certain seuil, considérez-le comme une correspondance
					if (difference < 0.1) {
						return file.getName(); // Retourne le nom du fichier/image
					}
				}
			}
		}
		return null;  // Aucune correspondance trouvée
	}

	private double compareHistograms(Mat detectedFace, Mat referenceFace) {
		// Convertissez les images en niveaux de gris
		Imgproc.cvtColor(detectedFace, detectedFace, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(referenceFace, referenceFace, Imgproc.COLOR_BGR2GRAY);

		// Calculez les histogrammes
		Mat histDetected = new Mat();
		Mat histReference = new Mat();
		Imgproc.calcHist(List.of(detectedFace), new MatOfInt(0), new Mat(), histDetected, new MatOfInt(256), new MatOfFloat(0, 256));
		Imgproc.calcHist(List.of(referenceFace), new MatOfInt(0), new Mat(), histReference, new MatOfInt(256), new MatOfFloat(0, 256));

		// Calculez la différence moyenne des histogrammes
		return Imgproc.compareHist(histDetected, histReference, Imgproc.HISTCMP_CORREL);
	}

	private Mat loadImage(String imagePath) {
		// Chargez l'image en tant que matrice OpenCV
        return Imgcodecs.imread(imagePath);
	}
	
	public void loadCascade() {
		String cascadePath = "haarcascade_frontalface_default.xml";
		faceDetector = new CascadeClassifier(cascadePath);
	}
	
	public int detectAndDrawFace(Mat image) {
		MatOfRect faceDetection = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetection, 1.1, 7, 0, new Size(50, 20), new Size());

		for (Rect rect : faceDetection.toArray()) {
			String matchingImageName = findMatchingImage(image.submat(rect));

			if (matchingImageName != null) {
				Imgproc.putText(image, matchingImageName, new Point(rect.x + rect.height, rect.y + rect.width / 10),
						1, 1, new Scalar(124, 252, 0), 1);
				
				return Integer.parseInt(matchingImageName.replace(".jpg", ""));
			} else {
				Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(0, 255, 0));
				Imgproc.putText(image, "Face", new Point(rect.x + rect.height, rect.y + rect.width / 10),
						1, 1, new Scalar(124, 252, 0), 1);
				
				return -1;
			}
		}
		return -1;
	}
}
