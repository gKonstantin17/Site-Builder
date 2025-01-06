package UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import UI.Controllers.ToolbarController;
import UI.DataTransfer.ProjectManager;
import UI.Helpers.StageSingleton;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DialogManager {
	Label projectName;
	Label projectSaveLabel;
	Label curentPageLabel;
	
	public DialogManager(Label projectName, Label projectSaveLabel,Label curentPageLabel)
	{
		this.projectName = projectName;
		this.projectSaveLabel = projectSaveLabel;
		this.curentPageLabel = curentPageLabel;
	}
	public DialogManager()
	{
	}
	
	public void showMainDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Выбор проекта"); 

        // Настроим основные кнопки
        ButtonType createButtonType = new ButtonType("Создать проект", ButtonBar.ButtonData.LEFT);
        ButtonType selectButtonType = new ButtonType("Выбрать существующий проект", ButtonBar.ButtonData.RIGHT);
        ButtonType cancelButtonType = new ButtonType("", ButtonBar.ButtonData.CANCEL_CLOSE); // for exit button

        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, selectButtonType, cancelButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.setMaxWidth(0);
        cancelButton.setMaxHeight(0);
        cancelButton.setVisible(false);  
        
        Label label = new Label("Выберите действие:");
        dialog.getDialogPane().setContent(label);

        dialog.setResultConverter(button -> {
            if (button == createButtonType) {
                return "create";
            } else if (button == selectButtonType) {
                return "select";
            }
            return null;
        });

        dialog.showAndWait().ifPresent(choice -> {
            if ("create".equals(choice)) {
                showCreateProjectDialog();
            } else if ("select".equals(choice)) {
                showSelectProjectDialog();
            } else {
                System.out.println("Действие отменено.");
            }
        });
    }
    
    public void showCreateProjectDialog() {
        Dialog<String> createDialog = new Dialog<>();
        createDialog.setTitle("Создать проект");


        ButtonType backButtonType = new ButtonType("Назад", ButtonBar.ButtonData.RIGHT);
        ButtonType okButtonType = new ButtonType("Ок", ButtonBar.ButtonData.LEFT);
        createDialog.getDialogPane().getButtonTypes().addAll(backButtonType, okButtonType);


        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Введите название проекта");

        createDialog.getDialogPane().setContent(projectNameField);

        createDialog.setResultConverter(button -> {
            if (button == okButtonType) {
                return projectNameField.getText();
            } else if (button == backButtonType) {
                return "back";
            }
            return null;
        });

        createDialog.showAndWait().ifPresent(result -> {
            if ("back".equals(result)) {
                showMainDialog(); 
            } else if (result != null && !result.isBlank()) {
            	if (isExistProject(result)) {
                	showExistProjectMessage();
                }
            	else {

                ProjectManager.setProjectName(result); 
                ProjectManager.createJsonForNewProject();
    	        projectName.setText(ProjectManager.getProjectName()); 
    	        
                projectSaveLabel.setText("Не сохранен");
                curentPageLabel.setText(ProjectManager.getJsonFile().replaceAll("\\.json$", ""));
                CEFWebView.RestartBrowser();
            	}
            } else {
                showMainDialog(); // back to main dialog
            }
        });
    }
    private void showSelectProjectDialog() {
    	CEFWebView.RestartBrowser();
    	boolean result = load();
    	if (!result)
    	{
    		showMainDialog();
    	}
    }
    static boolean isLoad = true;
    public boolean load() {
    	 DirectoryChooser directoryChooser = new DirectoryChooser();
    	 directoryChooser.setTitle("Выберите проект");
    	 directoryChooser.setInitialDirectory(new java.io.File("saves"));
    	 Platform.runLater(() -> {
         	Stage _stage = StageSingleton.getStage();
             _stage.setAlwaysOnTop(false); 
             
             java.io.File selectedDirectory = directoryChooser.showDialog(new Stage());
        	 if (selectedDirectory != null) {

    	        ProjectManager.setProjectName(selectedDirectory.getName());
    	        File file = new File(ProjectManager.getDirectory() + "/index.json"); 
    	        String filePath = file.getAbsolutePath();								
    	        projectName.setText(ProjectManager.getProjectName()); 
    	        
                projectSaveLabel.setText("сохранен (с момента загрузки)"); 
                curentPageLabel.setText(ProjectManager.getJsonFile().replaceAll("\\.json$", ""));
                
                
    	        System.out.println(filePath);
    	        StringBuilder stringBuilder = new StringBuilder();


    	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
    	            String line;
    	            while ((line = reader.readLine()) != null) {
    	                stringBuilder.append(line).append("\n");  
    	            }
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }

    	        String json = stringBuilder.toString();
    	        System.out.println(json);
    	        
    	        // load to browser
    	        CEFWebView.LoadElements(json);
    	        ProjectManager.loadPagesConfig();
    	        isLoad = true;
    	    } else {
    	        System.out.println("Файл не выбран.");
    	        isLoad = false;
    	    }
             
             Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
         });
    	 
    	 return isLoad;
	}
    private boolean isExistProject(String project)
    {
    	 String projectFolderPath = "saves/" + project;
         File projectFolder = new File(projectFolderPath);

         if (projectFolder.exists() && projectFolder.isDirectory()) {
             return true;
         }
         else
        	 return false;
    }
    private boolean isExistPage(String page)
    {
    	 if (page.equals("pages"))
    		 return true;
    	 String projectFolderPath = ProjectManager.getDirectory();
         File pageJson = new File(projectFolderPath+ "/"+page+".json");

         return pageJson.exists() && pageJson.isFile();
    }
    private void showExistProjectMessage()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Проект с таким именем уже существует");
        alert.showAndWait();
        showMainDialog();
    }
    private void showExistPageMessage()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Страница с таким именем уже существует");
        alert.showAndWait();
    }
    private void showNeedWritePageMessage()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Нужно ввести название страницы");
        alert.showAndWait();
    }
    private void showDeletePageMessage()
    {
    	// Папка с таким именем уже существует
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Нельзя удалять index.json");
        alert.showAndWait();
    }
    public void showCreatePageDialog() {
            Dialog<String> createDialog = new Dialog<>();
            createDialog.setTitle("Создать страницу");

            ButtonType backButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType okButtonType = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
            createDialog.getDialogPane().getButtonTypes().addAll(backButtonType, okButtonType);

            TextField projectNameField = new TextField();
            projectNameField.setPromptText("Введите название страницы");

            createDialog.getDialogPane().setContent(projectNameField);

            createDialog.setResultConverter(button -> {
                if (button == okButtonType) {
                    return projectNameField.getText();
                } else if (button == backButtonType) {
                    return "back";
                }
                return null;
            });

            createDialog.showAndWait().ifPresent(result -> {
                if (result != null && !result.isBlank()) {
                	if (isExistPage(result)) {	
                		showExistPageMessage(); 
                    }
                	else {
 
                	
                    ProjectManager.createNewPageJson(result);
                    CEFWebView.Reload();
                    projectSaveLabel.setText("Сохранен");
                    ProjectManager.setJsonFile(result+".json");
                    curentPageLabel.setText(ProjectManager.getJsonFile().replaceAll("\\.json$", ""));
                	}
                } else {
                	showNeedWritePageMessage(); 
                }
            });
        
    }
    public void choosePage()
    {
        
	    FileChooser fileChooser = new FileChooser();

	    fileChooser.setTitle("Выбрать страницу");

	    // set directory
	    fileChooser.setInitialDirectory(new java.io.File(ProjectManager.getDirectory()));

	    // only json files
	    fileChooser.getExtensionFilters().add(
	        new FileChooser.ExtensionFilter("JSON Files", "*.json")
	    );
	    Platform.runLater(() -> {
        	Stage _stage = StageSingleton.getStage();
            _stage.setAlwaysOnTop(false);
            
            java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());

    	    if (selectedFile != null) {
    	        String filePath = selectedFile.getAbsolutePath(); // FILE

    	        
    	        ProjectManager.setJsonFile(selectedFile.getName());
                projectSaveLabel.setText("сохранен (с момента загрузки)"); //label
                curentPageLabel.setText(ProjectManager.getJsonFile().replaceAll("\\.json$", ""));
                
                
    	        System.out.println(filePath);
    	        StringBuilder stringBuilder = new StringBuilder();

    	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
    	            String line;
    	            while ((line = reader.readLine()) != null) {
    	                stringBuilder.append(line).append("\n");  
    	            }
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }

    	        String json = stringBuilder.toString();
    	        System.out.println(json);
    	        CEFWebView.Reload();
    	        try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    	        CEFWebView.LoadElements(json);
    	        
    	        ProjectManager.loadPagesConfig();
    	    } else {
    	        System.out.println("Файл не выбран.");
    	    }
            
            Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
        });

	    
    }
    public void deletePage()
    {
	    FileChooser fileChooser = new FileChooser();

	    fileChooser.setTitle("Удалить страницу");

	    fileChooser.setInitialDirectory(new java.io.File(ProjectManager.getDirectory()));

	    fileChooser.getExtensionFilters().add(
	        new FileChooser.ExtensionFilter("JSON Files", "*.json")
	    );
	    Platform.runLater(() -> {
        	Stage _stage = StageSingleton.getStage();
            _stage.setAlwaysOnTop(false); 
            
    	    java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());

    	    if (selectedFile != null) {
    	    	if (selectedFile.getName().equals("index.json"))
    	    	{
    	    		showDeletePageMessage();
    	    		return;
    	    	}
    	        String fileName = selectedFile.getName();
    	        if (selectedFile.delete()) {
    	        	ProjectManager.deletePageJson(fileName);
    	            System.out.println("Файл " + fileName + " успешно удален из папки.");

    	            
    	        } else {
    	            System.out.println("Не удалось удалить файл " + fileName + ".");
    	        }
    	        
    	       
    	    } else {
    	        System.out.println("Файл не выбран.");
    	    }
            
            Platform.runLater(() -> _stage.setAlwaysOnTop(true)); 
        });
	    
    }
    public void showTimerInputDialog() {
        DatePicker datePicker = new DatePicker();
        ColorPicker colorPicker = new ColorPicker();
        TextField fontSizeField = new TextField();
        fontSizeField.setPromptText("Введите размер шрифта (например, 40px)");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(datePicker, 0, 0);
        grid.add(colorPicker, 0, 1);
        grid.add(fontSizeField, 0, 2);

        GridPane.setHgrow(datePicker, Priority.ALWAYS);
        GridPane.setHgrow(colorPicker, Priority.ALWAYS);
        GridPane.setHgrow(fontSizeField, Priority.ALWAYS);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Настройки таймера");
        alert.setHeaderText("Выберите параметры для таймера");
        alert.getDialogPane().setContent(grid);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(button -> {
            if (button == okButton) {
                String selectedDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "2025-01-01"; // default value
                String selectedColor = colorPicker.getValue() != null 
                	    ? formatColor(colorPicker.getValue()) 
                	    : "white"; // default value
                String fontSize = fontSizeField.getText().isEmpty() ? "40px" : fontSizeField.getText(); //default value

                
                CEFWebView.ExecuteJS("templates/modules/timer/timer.js");	
                CEFWebView.ExecuteJSOneString(String.format("timer('%s','%s','%s');", selectedDate, selectedColor, fontSize));
                CEFWebView.ExecuteJS("templates/modules/timer/startCountdown.js");	
                ProjectManager.saveTimer();
            }
        });
    }
   


    	private String formatColor(Color color) {
    	    int red = (int) (color.getRed() * 255);
    	    int green = (int) (color.getGreen() * 255);
    	    int blue = (int) (color.getBlue() * 255);

    	    // format HEX (#RRGGBB)
    	    return String.format("#%02X%02X%02X", red, green, blue);
    	}
}
