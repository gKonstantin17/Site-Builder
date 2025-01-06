package UI.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import UI.CEFWebView;
import UI.DataTransfer.ProjectManager;
import UI.Helpers.StageSingleton;
import UI.Helpers.Tools;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ToolbarController {
 	@FXML private ImageView createDivImg;
 	@FXML private SplitMenuButton splitMenuButton;
 	@FXML private ImageView createTextImg;
 	@FXML private ImageView createColumnsImg;
 	@FXML private ImageView createButtonsImg;
 	@FXML private ImageView createIMGImg;
 	@FXML private ImageView createFormImg;
 	@FXML private ImageView createFieldImg;
 	@FXML private ImageView createHeaderImg;
 	
 	
 	@FXML private ImageView deleteElementImg; 
 	@FXML private static Label idSelectedForDelete;
 	@FXML private ImageView ImgUploadImg;
    @FXML private ImageView pageReloadImg;
    @FXML private ImageView pageLoadImg;
    @FXML private ImageView browserReloadImg;
    
    
    @FXML
    private void initialize() 
    {
    	 Image image = new Image("file:src/resources/icons/tools/block_icon.png"); 
         createDivImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/text_icon.png"); 
         createTextImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/columns_icon.png"); 
         createColumnsImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/button_icon.png"); 
         createButtonsImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/img_icon.png"); 
         createIMGImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/form_icon.png"); 
         createFormImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/field_icon.png"); 
         createFieldImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/header_icon.png"); 
         createHeaderImg.setImage(image);
         
         image = new Image("file:src/resources/icons/tools/delete_icon.png"); 
         deleteElementImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/uploadimg_icon.png"); 
         ImgUploadImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/clear_icon.png"); 
         pageReloadImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/load_icon.png"); 
         pageLoadImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/reload_icon.png"); 
         browserReloadImg.setImage(image);
         
    }
    
    @FXML
    private void createDiv()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_DIV.getPath());
    }
    @FXML
    private void createP()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_P.getPath());
    }
    @FXML
    private void createH1()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_H1.getPath());
    }
    @FXML
    private void createH2()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_H2.getPath());
    }
    @FXML
    private void createH3()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_H3.getPath());
    }
    @FXML
    private void createColums()
	{
	 	TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Введите количество колонок");
	    dialog.setHeaderText("Укажите количество колонок, которое нужно создать:");
	    dialog.setContentText("Количество колонок:");

	    dialog.showAndWait().ifPresent(input -> {
	        try {
	            int numberOfColumns = Integer.parseInt(input);  
	            CEFWebView.ExecuteJS(Tools.CREATE_COLUMNS.getPath());
	            CEFWebView.ExecuteJSOneString("CreateColums(" + numberOfColumns + ");");
	        } catch (NumberFormatException e) {
	            Alert alert = new Alert(AlertType.ERROR, "Пожалуйста, введите корректное число!", ButtonType.OK);
	            alert.showAndWait();
	        }
	    });
    }
    @FXML
    private void createButton()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_BUTTON.getPath());
    }
    @FXML
    private void createImg()
    {
    	System.out.println(ProjectManager.getDirectoryImg());
    	if (ProjectManager.checkIfImgFolderIsEmpty())
    	{
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Информация");
	        alert.setHeaderText("Изображения нужно сначала загрузить");
	        alert.showAndWait();
    	}
    	else
    	{
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
		            System.out.println(filePath);
		            CEFWebView.ExecuteJS(Tools.CREATE_IMAGE.getPath());
		            CEFWebView.ExecuteJSOneString("createImg('"+filePath+"');");
		        }
	            
	            Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
	        });

	        
    	    
    	}
    }
    @FXML
    private void createForm()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_FORM.getPath());
    }
    @FXML
    private void createField()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_FIELD.getPath());
    }
    @FXML
    private void createHeader()
	{
       
        TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Введите количество элементов");
	    dialog.setHeaderText("Укажите количество элементов, которое нужно создать:");
	    dialog.setContentText("Количество элементов:");

	    dialog.showAndWait().ifPresent(input -> {
	        try {
	            int numberOfElmts = Integer.parseInt(input);  
	            CEFWebView.ExecuteJS(Tools.CREATE_HEADER.getPath());
	            CEFWebView.ExecuteJSOneString("createHeader(" + numberOfElmts + ");");
	        } catch (NumberFormatException e) {
	            Alert alert = new Alert(AlertType.ERROR, "Пожалуйста, введите корректное число!", ButtonType.OK);
	            alert.showAndWait();
	        }
	    });
    }
    @FXML
    private void createSection()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_SECTION.getPath());
    }
    @FXML
    private void openSplitMenu() {
        splitMenuButton.show();
    }

    
    @FXML
    private void PASS() {
        
    }
    
    @FXML
    private void deleteElement()
    {
    	CEFWebView.ExecuteJS(Tools.DELETE.getPath());
    	CEFWebView.ExecuteJSOneString("removeElementById('"+idSelectedForDelete.getText()+"');");
    }
    
    public static void setiIdSelectedForDelete(Label label)
    {
    	idSelectedForDelete = label;
    }
    
    @FXML
    private void uploadImg()
	{	
    	Platform.runLater(() -> {
        	Stage _stage = StageSingleton.getStage();
            _stage.setAlwaysOnTop(false); 
            
            if (ProjectManager.isSaved())
        	{
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
                
                File file = fileChooser.showOpenDialog((Stage) ImgUploadImg.getScene().getWindow());
                
                if (file != null) {
                    try {
                        String directoryPath = ProjectManager.getDirectoryImg();
                        
                        Path directory = Paths.get(directoryPath).toAbsolutePath();
                        if (!Files.exists(directory)) {
                            Files.createDirectories(directory);
                        }

                        Path destination = directory.resolve(file.getName());
                        Files.copy(file.toPath(), destination);

                        System.out.println("Изображение успешно скопировано в: " + destination.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        	}
        	else {
            	Alert alert = new Alert(AlertType.WARNING);
            	alert.setTitle("Информация");
            	alert.setHeaderText("Проект нужно сначала сохранить");
            	alert.showAndWait();
            }
            
            Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
        });
    	
    	
        
	}
    @FXML
    private void pageReload()
	{
		CEFWebView.Reload();
	}

    @FXML
    private void runJS()
	{
		TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Запуск JavaScript");
	    dialog.setHeaderText("Вставьте JavaScript код");
	    dialog.showAndWait().ifPresent(input -> {
	    CEFWebView.ExecuteJSOneString(input);
	    });
    }
	
    @FXML
    private void browserLoad()
	{
		CEFWebView.RestartFrameBrowser();
	}

    @FXML
    private static void browserReload()
	{
		CEFWebView.RestartBrowser();
	}
	
}
