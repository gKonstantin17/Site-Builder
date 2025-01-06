package UI;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import UI.DataTransfer.ExportSite;
import UI.DataTransfer.ProjectManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainSceneController {
	@FXML private HBox header;
	@FXML private HBox toolbar;
	@FXML private VBox left;
	@FXML private VBox right;
   
    
    
    @FXML private Label projectName;
	@FXML private Label projectSaveLabel;
	@FXML private Label curentPageLabel;
	private DialogManager dialogManager;
    
    
    @FXML
    private void initialize() {
    	dialogManager = new DialogManager(projectName,projectSaveLabel,curentPageLabel);
        Platform.runLater(() -> {
        	try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	dialogManager.showMainDialog();
        	
        	
        });
        
    }
    
    @FXML
    private void create()
	{
		dialogManager.showCreateProjectDialog();
	}
    @FXML
    private void save()
	{
		boolean result = ProjectManager.saveCurrentPage();
		if (!result)
		{
			 projectSaveLabel.setText("сохранен");
		}
		
	}
    @FXML
    private void load() {
	    dialogManager.load();
	    ProjectManager.setSaved(true);
	}
    @FXML
    private void export() {
		try {
			ExportSite.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    private void createNewPage()
	{
		save();
		dialogManager.showCreatePageDialog();
	}
    @FXML
    private void goToProjectPage()
	{
		save();
		dialogManager.choosePage();
	}
    @FXML
    private void deletePage()
	{
		dialogManager.deletePage();
	}
    @FXML
    private void openDoc()
	{
		File pdfFile = new File("src/resources/doc.pdf");
		    
		    if (pdfFile.exists()) {
		        try {
		            Desktop.getDesktop().open(pdfFile);
		        } catch (IOException e) {
		            e.printStackTrace();
		            System.out.println("Ошибка при открытии PDF файла.");
		        }
		    } else {
		        System.out.println("Файл не найден.");
		    }
		
	}
    @FXML
    private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("О программе");
	    alert.setHeaderText("Конструктор сайтов");
	    alert.setContentText("Версия: 1.0\nРазработчик: Гусев К.Е. 235 группа");

	    Image image = new Image("file:src/resources/about.png");
	    ImageView imageView = new ImageView(image);
	    imageView.setFitWidth(200);
	    imageView.setFitHeight(150);
	    alert.setGraphic(imageView);

	    alert.showAndWait();
	}

}





