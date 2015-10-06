package org.kkowalczyk.tracking;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;

public class ImageProcessingThread implements Runnable{
	volatile public boolean stop = false;
	private BackgroundSubtractorMOG2 bgsub;
	
	
	@Override
	public void run() {
		bgsub = Video.createBackgroundSubtractorMOG2();
		while(!stop){
			Mat result = Mat.zeros(Main.C_HEIGHT, Main.C_WIDTH, CvType.CV_8UC3);
			bgsub.apply(Main.frame, result, 0.0001);
			Mat morp_kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5,5));
			Imgproc.morphologyEx(result, result, Imgproc.MORPH_OPEN, morp_kernel);
			Main.fromProcess(result);
		}
	}

}
