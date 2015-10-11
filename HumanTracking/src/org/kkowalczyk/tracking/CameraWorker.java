package org.kkowalczyk.tracking;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CameraWorker implements Runnable {
	//Konfiguracja
	private final int timeout = 5000; //Timeout uruchomienia kamery
	private final boolean flipV = false;
	private final boolean flipH = false;
	private final int fps = 7;
	
	private long open_time;
	private ThreadManager manager;
	private VideoCapture capture;
	private Mat target;
	
	public CameraWorker(ThreadManager mManager, Mat mTarget){
		capture = new VideoCapture();
		manager = mManager;
		target = mTarget;
	}
	
	@Override
	public void run() {
		capture.open(0);
		open_time=System.currentTimeMillis()+timeout;
		
		//Pętla z ustalonym timeoutem na uruchomienie kamery
		while(open_time>=System.currentTimeMillis()){
			if(capture.isOpened()){
				System.out.println("Kamera uruchomiona.");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(!capture.isOpened()){
			System.out.println("Błąd kamery: timeout");
			capture.release();
			return;
		}
		long next_frame_t=0;
		long sleep_t=0;
		//Główna pętla odczytująca obraz z podana częstotliwością
		while(!manager.isStopping()){
			next_frame_t=System.currentTimeMillis()+(1000/fps);
			manager.cameraWait();
			//Warunek do obsługi specjalnej sytuacji, kiedy podczas oczekiwania wyłączono program
			if(manager.isStopping())
				break;
			capture.read(target);
			Core.flip(target, target, -1);
			manager.procFrame();
			sleep_t=next_frame_t-System.currentTimeMillis();
			if(sleep_t>0){
				try {
					Thread.sleep(sleep_t);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("Zbyt duzo fps!(Przy uruchomieniu to normalne)");
			}
			
				
		}
		
		//wyłączenie kamery. Bez tego wątek się nie kończy i blokuje kamerę.
		capture.release();
	}

}
