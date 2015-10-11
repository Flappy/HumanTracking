package org.kkowalczyk.tracking;

import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

public class ProcWorker implements Runnable{
	private ThreadManager manager;
	private Mat target, source;
	private boolean cloned = true;
	private FeatureDetector detector;
	
	public ProcWorker(ThreadManager mManager, Mat mSource, Mat mTarget){
		manager=mManager;
		source=mSource;
		target=mTarget;
		detector = FeatureDetector.create(FeatureDetector.ORB);
	}
	
	public boolean isCloning(){
		return !cloned;
	}
	
	public void reset(){
		cloned = false;
	}

	@Override
	public void run() {
		source.copyTo(target);
		cloned=true;
		synchronized(manager.camera_lock){
			manager.camera_lock.notify();
		}
		MatOfKeyPoint points = new MatOfKeyPoint();
		detector.detect(target, points);
		for(KeyPoint p : points.toList()){
			Imgproc.circle(target, p.pt, 5, new Scalar(0,255,0), 3);
		}
		manager.drawPost();
	}
}
