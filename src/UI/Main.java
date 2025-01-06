package UI;


import java.io.IOException;

import UI.Helpers.StageSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

 
public class Main extends Application{
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
    	StageSingleton.setStage(stage);
    	stage.setWidth(1550); // default size of stage
        stage.setHeight(880);
        stage.initStyle(StageStyle.TRANSPARENT); 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/mainScene.fxml"));
    	MainSceneController controller = new MainSceneController();
    	loader.setController(controller);
    	try {
			Parent root = loader.load();
	        Scene scene = new Scene(root, Color.TRANSPARENT);
	        stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	stage.setMaximized(true);
    	
        stage.show();
        CEFWebView.start(stage);
        
        
    }
}
	












