package org.kkowalczyk.tracking;

import org.opencv.core.Mat;

public class GuiPreWorker implements Runnable {
	private ThreadManager manager;
	private Gui gui;
	private Mat mat;
	private boolean cloned = true;
	
	public GuiPreWorker(ThreadManager mManager, Gui mGui, Mat mMat){
		gui = mGui;
		mat = mMat;
		manager = mManager;
	}
	
	public boolean isCloning(){
		return !cloned;
	}
	
	public void reset(){
		cloned = false;
	}

	@Override
	public void run() {
		Mat temp = mat.clone();
		cloned = true;
		synchronized(manager.camera_lock){
			manager.camera_lock.notify();
		}
		gui.setView(0, Utils.mat2Image(temp));
	}

}
