package org.kkowalczyk.tracking;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gui extends Scene {
	private final static int resX = 360;
	private final static int resY = 240;
	
	private Group root;
	private ImageView[] views = new ImageView[4];
	
	
	

	public Gui(Parent root) {
		super(root,resX*2,resY*2);
		this.root=(Group)root;
		for(int i = 0; i < 4; i++){
			views[i] = new ImageView();
			views[i].setFitWidth(resX);
			views[i].setFitHeight(resY);
			views[i].setPreserveRatio(true);
			this.root.getChildren().add(views[i]);
		}
			views[1].setLayoutX(resX);
			views[2].setLayoutY(resY);
			views[3].setLayoutX(resX);
			views[3].setLayoutY(resY);
	}
	
	//Tu jest potencjalny spowalniacz(Do późniejszego zbenchmarkowania)
	public void setView(int id, Image im){
		views[id].setImage(im);
	}
}
