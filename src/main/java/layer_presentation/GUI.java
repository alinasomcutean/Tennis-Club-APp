package layer_presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import layer_data_access.repo.TennisRepo;
import model.TennisSet;

import java.io.IOException;
import java.util.List;


public class GUI extends Application //implements EventHandler<ActionEvent>
{
	private Stage window;
	
    public static void main( String[] args )
    {
    	launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
    	ClassLoader classLoader = GUI.class.getClassLoader();
		Parent root = FXMLLoader.load(classLoader.getResource("login.fxml"));
		window=primaryStage;		
    	
		Scene mainScene = new Scene(root);
		mainScene.getStylesheets().add("Style.css");
		window.setScene(mainScene);
		window.setTitle("Assignment1");
		window.show();
	}
		

	public static Scene changeScene(String fxml) throws IOException{
		ClassLoader classLoader = GUI.class.getClassLoader();
		Parent pane = FXMLLoader.load(classLoader.getResource(fxml));
		
		Stage stage = new Stage();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add("Style.css");
		stage.setScene(newScene);
		stage.show();
		
		return newScene;
	}
}
