package UI.Controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import UI.CEFWebView;
import UI.DialogManager;
import UI.DataTransfer.ProjectManager;
import UI.Helpers.StageSingleton;
import UI.Helpers.Tools;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeftPanelController {
	
	@FXML private VBox leftContent;
	@FXML private ImageView visibleLeftImg;
	private DialogManager dialogManager;
	
	@FXML private Button parallaxImgBtn;
	@FXML private Button bgrSnowBtn;
	@FXML private Button timerBtn;
	@FXML private Button fallingObjsBtn;
	
	@FXML
	    private void initialize() {
		
		leftContent.setVisible(false);
		Image image = new Image("file:src/resources/icons/shop.png"); 
		visibleLeftImg.setImage(image);
		
		Tooltip parallaxImgBtnHelp = new Tooltip("Изображение, реагирующие на курсор");
		parallaxImgBtnHelp.setShowDelay(Duration.seconds(0.3));
		parallaxImgBtnHelp.setHideDelay(Duration.seconds(0.5));
		parallaxImgBtn.setTooltip(parallaxImgBtnHelp);
		
		Tooltip bgrSnowBtnHelp = new Tooltip("Задний фон в виде падающего снега. Чтобы элементы страницы отображались, нужно поместить их в блок");
		bgrSnowBtnHelp.setShowDelay(Duration.seconds(0.3));
		bgrSnowBtnHelp.setHideDelay(Duration.seconds(0.5));
		bgrSnowBtn.setTooltip(bgrSnowBtnHelp);
		
		Tooltip timerBtnHelp = new Tooltip("Создает таймер до заданной даты. Чтобы его передвигать, нужно поместить в блок");
		timerBtnHelp.setShowDelay(Duration.seconds(0.3));
		timerBtnHelp.setHideDelay(Duration.seconds(0.5));
		timerBtn.setTooltip(timerBtnHelp);
		
		Tooltip fallingObjsBtnHelp = new Tooltip("Создает поле с объектами с физическими свойствами, представленные изображениями. Занимает всё место на странице.");
		fallingObjsBtnHelp.setShowDelay(Duration.seconds(0.3));
		fallingObjsBtnHelp.setHideDelay(Duration.seconds(0.5));
		fallingObjsBtn.setTooltip(fallingObjsBtnHelp);
	 }
	
	@FXML
    private VBox vboxVisibleLeft;
	public void visibleLeft()
	{
		dialogManager = new DialogManager();
		leftContent.setVisible(!leftContent.isVisible());
        if (leftContent.isVisible()) {
        	Image image = new Image("file:src/resources/icons/hide.png"); 
        	visibleLeftImg.setImage(image);
            vboxVisibleLeft.setMinWidth(225);
            vboxVisibleLeft.setMaxWidth(225);
        } else {
        	Image image = new Image("file:src/resources/icons/shop.png"); 
            visibleLeftImg.setImage(image);
            vboxVisibleLeft.setMinWidth(15);
            vboxVisibleLeft.setMaxWidth(15);
        }
	}
	
	public void createSnow()
	{
		CEFWebView.ExecuteJS("templates/modules/snow/snow.js");
		ProjectManager.saveSnow();
	}
	
	public void createTimer()
	{
		dialogManager.showTimerInputDialog();
	}
	public void createParallaxImage()
	{
		CEFWebView.ExecuteJS("templates/modules/parallax/parallax.js");
		CEFWebView.ExecuteJS("templates/modules/parallax/addParallaxImg.js");
		if (ProjectManager.checkIfImgFolderIsEmpty()) {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Информация");
	        alert.setHeaderText("Изображения нужно сначала загрузить");
	        alert.showAndWait();
	    } else {
	        String directoryPath = ProjectManager.getDirectoryImg();
	        File directory = new File(directoryPath);

	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Выберите изображение");
	        fileChooser.setInitialDirectory(directory);
	        fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
	        );
	        Platform.runLater(() -> {
	        	Stage _stage = StageSingleton.getStage();
	            _stage.setAlwaysOnTop(false); 
	            
	            File file = fileChooser.showOpenDialog(new Stage());
		        
		        if (file != null) {
		        	
		        	 String filePath = "../../" + ProjectManager.getDirectoryImg() + "/" + file.getName();
		            
		            TextInputDialog valueDialog = new TextInputDialog();
		            valueDialog.setTitle("Параметр для изображения");
		            valueDialog.setHeaderText("сила смещения (-5..5):");
		            valueDialog.setContentText("Value:");

		            Optional<String> result = valueDialog.showAndWait();
		            
		            if (result.isPresent()) {
		                String value = result.get();

		                CEFWebView.ExecuteJSOneString(String.format("addParallaxImg('%s','%s');", filePath, value));
		            }
		        }
	            
	            Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
	        });
	        ProjectManager.saveParallax();
	    }
	}
	

	public void createFallingObjects() {
	    // run scripts
	    CEFWebView.ExecuteJS("templates/modules/matter/matter.js");
	    CEFWebView.ExecuteJS("templates/modules/matter/setupCanvasAndPhysics.js");


	    if (ProjectManager.checkIfImgFolderIsEmpty()) {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Информация");
	        alert.setHeaderText("Изображения нужно сначала загрузить");
	        alert.showAndWait();
	    } else {

	        TextInputDialog inputDialog = new TextInputDialog("5"); 
	        inputDialog.setTitle("Количество объектов");
	        inputDialog.setHeaderText("Введите количество объектов каждого вида, затем как они будут выглядеть (несколько изображений)");
	        inputDialog.setContentText("Количество:");

	        Optional<String> result = inputDialog.showAndWait();
	        if (result.isPresent()) {
	            try {
	                int objectCount = Integer.parseInt(result.get());

	                if (objectCount <= 0) {
	                    throw new NumberFormatException();
	                }

	                String directoryPath = ProjectManager.getDirectoryImg();
	                File directory = new File(directoryPath);

	                FileChooser fileChooser = new FileChooser();
	                fileChooser.setTitle("Выберите несколько изображений");
	                fileChooser.setInitialDirectory(directory);
	                fileChooser.getExtensionFilters().addAll(
	                    new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
	                );

	                Platform.runLater(() -> {
	                    Stage _stage = StageSingleton.getStage();
	                    _stage.setAlwaysOnTop(false); 
	                    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(_stage);

	                    if (selectedFiles != null && !selectedFiles.isEmpty()) {
	                        List<String> imageUrls = new ArrayList<>();
	                        for (File file : selectedFiles) {
	                        	String filePath = "../../" + ProjectManager.getDirectoryImg() + "/" + file.getName();
	                            imageUrls.add(filePath);
	                        }

	                        StringBuilder jsImagesArray = new StringBuilder("const images = [");
	                        for (int i = 0; i < imageUrls.size(); i++) {
	                            jsImagesArray.append("'").append(imageUrls.get(i)).append("'");
	                            if (i < imageUrls.size() - 1) {
	                                jsImagesArray.append(", ");
	                            }
	                        }
	                        jsImagesArray.append("];");

	                        CEFWebView.ExecuteJSOneString(jsImagesArray.toString());
	                        CEFWebView.ExecuteJSOneString("setupCanvasAndPhysics(images, " + objectCount + ");");
	                        ProjectManager.saveFallingObjects(jsImagesArray.toString(), objectCount);
	                    }

	                    Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
	                });
	            } catch (NumberFormatException e) {
	                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	                errorAlert.setTitle("Ошибка");
	                errorAlert.setHeaderText("Некорректный ввод");
	                errorAlert.setContentText("Введите положительное целое число.");
	                errorAlert.showAndWait();
	            }
	        }
	    }
	}


	
	
	
	
	
}
