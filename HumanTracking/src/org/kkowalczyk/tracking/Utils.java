package org.kkowalczyk.tracking;

import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;

public class Utils {
	public static Image mat2Image(Mat mat){
	    MatOfByte buffer = new MatOfByte();
	    Imgcodecs.imencode(".bmp", mat, buffer);
	    return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
}
