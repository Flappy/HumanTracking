package org.kkowalczyk.tracking;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javafx.application.Platform;

public class CameraThread implements Runnable {
	volatile public boolean stop = false;
	private VideoCapture camera;
	private final int fps = 25;
	
	@Override
	public void run() {
		camera = new VideoCapture();
		//Sekunda na uruchomienie kamery (Do wstawienia jakaœ pêtla sprawdzaj¹ca czy kamera sie uruchomi³a, z timeoutem)
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		camera.open(1);
		if(!camera.isOpened()){
	        System.out.println("Camera Error");
	        camera.release();
	        Platform.exit();
	        return;
	    }
	    else{
	        System.out.println("Camera OK");
	    }
		long timer = 0;
		long sleep = 0;
		while(!stop){
			timer = System.currentTimeMillis()+(1000/fps);
			Mat frame = new Mat();
			camera.read(frame);
			Main.fromCamera(frame);
			sleep=timer-System.currentTimeMillis();
			if(sleep>0){
				try{
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		camera.release();
	}

}
