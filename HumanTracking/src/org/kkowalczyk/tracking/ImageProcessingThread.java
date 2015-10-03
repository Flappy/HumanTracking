package org.kkowalczyk.tracking;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
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
			bgsub.apply(Main.frame, result);
			//Core.absdiff(Main.frame, background, result);
			//Imgproc.threshold(result, result, 220, 255, Imgproc.THRESH_BINARY);
			Main.fromProcess(result);
			//Core.addWeighted(background, 0.9, Main.frame, 0.1, 0, background);
		}
	}

}
