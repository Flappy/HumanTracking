package org.kkowalczyk.tracking;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	volatile Mat preFrame = new Mat();
	volatile Mat postFrame = new Mat();
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Human Body Tracking");
		ThreadManager manager = new ThreadManager();
		
		
		Group root = new Group();
		Gui gui = new Gui(root);
		stage.setScene(gui);
		
		manager.setCameraWorker(new CameraWorker(manager, preFrame));
		manager.setProcWorker(new ProcWorker(manager, preFrame, postFrame));
		manager.setGuiPreWorker(new GuiPreWorker(manager,gui, preFrame));
		manager.setGuiPostWorker(new GuiPostWorker(manager,gui, postFrame));
		
		stage.show();
		
		manager.start();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	@Override
            public void handle(WindowEvent we) {
                System.out.println("Zamknięto okno, wyłączanie kamery");
                manager.stop();
            }
        });
	}

}
