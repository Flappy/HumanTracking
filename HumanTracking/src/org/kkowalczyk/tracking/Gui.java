package org.kkowalczyk.tracking;

import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gui extends Scene {
	private Group root;
	private ImageView cam_iv;
	private ImageView prcd_iv;
	
	

	public Gui(Parent root) {
		super(root,Main.C_WIDTH,Main.C_HEIGHT*2);
		this.root=(Group)root;
		
		cam_iv = new ImageView();
		prcd_iv = new ImageView();
		prcd_iv.setLayoutY(Main.C_HEIGHT);
		
		this.root.getChildren().add(cam_iv);
		this.root.getChildren().add(prcd_iv);
	}
	
	public void drawPrevFrame(){
		cam_iv.setImage(mat2Image(Main.frame));
	}
	public void drawPrcdFrame(){
		prcd_iv.setImage(mat2Image(Main.prcd_frame));
	}
	
	//Prawdopodobnie nie najszybsza metoda.(W testach wydaje siê dzia³aæ doœæ powolnie)
	public static Image mat2Image(Mat frame) {
	    // create a temporary buffer
	    MatOfByte buffer = new MatOfByte();
	    // encode the frame in the buffer, according to the PNG format
	    Imgcodecs.imencode(".png", frame, buffer);
	    // build and return an Image created from the image encoded in the
	    // buffer
	    return new Image(new ByteArrayInputStream(buffer.toArray()));
	}

}
