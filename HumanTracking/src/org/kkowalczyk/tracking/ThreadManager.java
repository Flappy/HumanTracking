package org.kkowalczyk.tracking;

public class ThreadManager {
	private Thread cameraThread, procThread, guiPreThread, guiPostThread;
	private CameraWorker cameraWorker;
	private ProcWorker procWorker;
	private GuiPreWorker guiPreWorker;
	private GuiPostWorker guiPostWorker;
	private boolean stop = false;
	public Object camera_lock = new Object();
	public Object proc_lock = new Object();
	
	public boolean isStopping(){
		return stop;
	}
	
	public void stop(){
		stop = true;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		synchronized(camera_lock){
			camera_lock.notify();
		}
	}
	
	public void setCameraWorker(CameraWorker mWorker){
		cameraWorker=mWorker;
	}
	
	public void setProcWorker(ProcWorker mWorker){
		procWorker=mWorker;
	}
	
	public void setGuiPreWorker(GuiPreWorker mWorker){
		guiPreWorker=mWorker;
	}
	
	public void setGuiPostWorker(GuiPostWorker mWorker){
		guiPostWorker=mWorker;
	}

	public void start() {
		cameraThread = new Thread(cameraWorker);
		cameraThread.start();
	}
	
	public boolean waitingForCamera(){
		return (!procWorker.isCloning()&&!guiPreWorker.isCloning());
	}
	
	public void cameraWait(){
		synchronized(camera_lock){
			while(!waitingForCamera()){
				try {
					camera_lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void procFrame() {
		procWorker.reset();
		procThread = new Thread(procWorker);
		procThread.start();
		guiPreWorker.reset();
		guiPreThread = new Thread(guiPreWorker);
		guiPreThread.start();
	}
	
	public void drawPost(){
		guiPostWorker.reset();
		guiPostThread = new Thread(guiPostWorker);
		guiPostThread.start();
	}
	
}
