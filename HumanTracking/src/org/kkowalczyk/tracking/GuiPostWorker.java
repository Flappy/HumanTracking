package org.kkowalczyk.tracking;

import org.opencv.core.Mat;

public class GuiPostWorker implements Runnable {
	private ThreadManager manager;
	private Gui gui;
	private Mat mat;
	private boolean cloned = true;
	
	public GuiPostWorker(ThreadManager mManager, Gui mGui, Mat mMat){
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
		gui.setView(1, Utils.mat2Image(temp));
	}

}
