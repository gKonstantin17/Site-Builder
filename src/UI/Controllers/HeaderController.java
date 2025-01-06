package UI.Controllers;

import UI.Browser.BrowserView;
import UI.Helpers.StageSingleton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HeaderController {
	@FXML private Button minimizeButton;
	@FXML private Button maximizeButton;
    @FXML private Button closeButton;
    @FXML private HBox titleBar;
    @FXML private ImageView programImg;
    @FXML private ImageView closeWindowImg;
    @FXML private ImageView maximizeWindowImg;
    @FXML private ImageView minimizeWindowImg;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage = StageSingleton.getStage();;
    
    
    
    @FXML
    private void initialize() 
    {
    		// drag and drop
            titleBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            titleBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            
            Image image = new Image("file:src/resources/icons/head/logo.png"); 
            programImg.setImage(image);
            image = new Image("file:src/resources/icons/head/close_icon.png"); 
            closeWindowImg.setImage(image);
            image = new Image("file:src/resources/icons/head/maximize_icon.png"); 
            maximizeWindowImg.setImage(image);
            image = new Image("file:src/resources/icons/head/minimaze_icon.png"); 
            minimizeWindowImg.setImage(image);
    }
    

    @FXML
    private void closeWindow() 
    {
        stage.close();
        System.exit(0);
    }
    @FXML
    private void minimizeWindow() 
    {
    	var frame = BrowserView.getFrame();
        stage.setIconified(true);
        frame.setVisible(false);
    }
    @FXML
    private void maximizeWindow() 
    {
        stage.setMaximized(!stage.isMaximized());
    }
}
