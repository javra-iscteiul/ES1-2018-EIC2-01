package BDA;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage){ 
		try{
			// Load the FXML File
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
			// Create the Scene
			Scene scene = new Scene(root,600,400);
			// Set the Title to the Stage
			primaryStage.setTitle("A FXML Example created with e(fx)clipse");
			// Add the Scene to the Stage
			primaryStage.setScene(scene);
			// Show the Stage
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args)
	{
		launch(args);
	}

}
