package org.kkowalczyk.tracking;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.WindowEvent; 

public class Main extends Application{
	public static final int C_WIDTH=640;
	public static final int C_HEIGHT=480;
	
	public static Gui GUI;
	public static volatile Mat frame;
	public static volatile Mat prcd_frame;
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		frame = Mat.zeros(C_HEIGHT, C_WIDTH, CvType.CV_8UC3);
		prcd_frame = Mat.zeros(C_HEIGHT, C_WIDTH, CvType.CV_8UC3);
		
		primaryStage.setTitle("Test przechwytywania obrazu i interfejsu");
		
        Group root = new Group();
        GUI = new Gui(root);
        primaryStage.setScene(GUI);
        
        //Uruchamianie w¹tku kamery
        CameraThread camera = new CameraThread();
		Thread camera_thread = new Thread(camera);
		camera_thread.start();
		
		//Uruchamianie w¹tku przetwarzania obrazu
		ImageProcessingThread im_proc = new ImageProcessingThread();
		Thread proc_thread = new Thread(im_proc);
		proc_thread.start();
		
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	@Override
            public void handle(WindowEvent we) {
                System.out.println("Window closed - releasing camera");
                camera.stop=true;
                im_proc.stop=true;
            }
        });     
	}
	
	//TODO: przeniesienie konwersji i rysowania na osobny w¹tek+synchronizacja.
	public static void fromCamera(Mat m){
		frame=m;
		GUI.drawPrevFrame();
	}
	
	public static void fromProcess(Mat m){
		prcd_frame=m;
		GUI.drawPrcdFrame();
	}
	

}
