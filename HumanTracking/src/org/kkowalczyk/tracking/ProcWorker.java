package org.kkowalczyk.tracking;

import org.opencv.core.Mat;

public class ProcWorker implements Runnable{
	private ThreadManager manager;
	private Mat target, source;
	private boolean cloned = true;
	
	public ProcWorker(ThreadManager mManager, Mat mSource, Mat mTarget){
		manager=mManager;
		source=mSource;
		target=mTarget;
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
		manager.drawPost();
	}
}
