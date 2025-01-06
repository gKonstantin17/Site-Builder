package UI.DataTransfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import UI.CEFWebView;
import UI.Helpers.Tools;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProjectManager {
	static String projectName;
	static String jsonFile;
	static String directory;
	static String directoryImg;
	static boolean isSaved = false;
	public static boolean isSaved() {
		return isSaved;
	}
	public static void setSaved(boolean isSaved) {
		ProjectManager.isSaved = isSaved;
	}
	public static String getProjectName() {
		return projectName;
	}
	public static void setProjectName(String projectName) {
		ProjectManager.projectName = projectName;
		directory = "saves/"+ projectName;
		jsonFile = "index.json";
		createDirectory();
	}
	public static String getJsonFile() {
		return jsonFile;
	}
	public static void createDirectory()
	{
		Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);          
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	public static String getDirectory() {
        return directory;
    }
	public static void setJsonFile(String jsonFilePath) {
		ProjectManager.jsonFile = jsonFilePath;
	}
	private static void createDirectoryImg()
	{
		Path dirPath = Paths.get(ProjectManager.getDirectory() + "/img");
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);           
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        directoryImg = ProjectManager.getDirectory() + "/img";
	}
	public static String getDirectoryImg()
	{
		if (directoryImg == null)
		{
			createDirectoryImg();
			return directoryImg;
		}
		else
			return directoryImg;
	}
	public static void createJsonForNewProject() {
		File indexFile = new File(directory + "/index.json");
		try {
			indexFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject pagesData = new JSONObject();
        JSONArray pagesArray = new JSONArray();

        // create index page
        pagesArray.put(new JSONObject().put("title", "index").put("url", "index.json").put("modules", JSONObject.NULL));
        pagesData.put("pages", pagesArray);

        File pagesFile = new File(directory + "/pages.cfg");

        try {
            FileWriter writer = new FileWriter(pagesFile);
            writer.write(pagesData.toString(4)); 
            writer.close();
            System.out.println("pages.json created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static boolean saveCurrentPage()
	{
		if (ProjectManager.getProjectName() == null)
		{
			Alert alert = new Alert(AlertType.WARNING); 
        	alert.setTitle("Информация");
        	alert.setHeaderText("Проект нужно сначала создать");
        	alert.showAndWait();
        	return true;
		}
		else {
			 CEFWebView.ExecuteJS(Tools.SAVE.getPath());
			 ProjectManager.setSaved(true);
			 return false;
		}
	}
	public static void createNewPageJson(String pageName)
	{
		File indexFile = new File(directory + "/" + pageName +".json");
		try {
			indexFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String title = pageName;
		String url  = pageName +".json";
		File pagesFile = new File(directory + "/pages.cfg");
		try {
            FileReader reader = new FileReader(pagesFile);
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject pagesData = new JSONObject(tokener);
            JSONArray pagesArray = pagesData.getJSONArray("pages");

            JSONObject newPage = new JSONObject();
            newPage.put("title", title);
            newPage.put("url", url);
            newPage.put("modules", JSONObject.NULL);

            pagesArray.put(newPage);

            FileWriter writer = new FileWriter(pagesFile);
            writer.write(pagesData.toString(4));
            writer.close();
            System.out.println("Page added successfully: " + title);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		public static void deletePageJson(String pageJsonFile)
		{
			 String pagesFilePath = ProjectManager.getDirectory() + "/pages.cfg";
			 System.out.println(pagesFilePath);
			 File pagesFile = new File(pagesFilePath);
			 if (!pagesFile.exists()) {
			        System.out.println("Файл pages.json не найден.");
			        return;
			 }
			try {

	            FileReader reader = new FileReader(pagesFile);
	            JSONTokener tokener = new JSONTokener(reader);
	            JSONObject pagesData = new JSONObject(tokener);
	            JSONArray pagesArray = pagesData.getJSONArray("pages");

	            boolean removed = false;
	            for (int i = 0; i < pagesArray.length(); i++) {
	                JSONObject pageNode = pagesArray.getJSONObject(i);
	                if (pageNode.getString("url").equals(pageJsonFile)) {
	                    pagesArray.remove(i);
	                    removed = true;
	                    break;
	                }
	            }
	            if (removed) {
	                try (FileWriter fileWriter = new FileWriter(pagesFile)) {
	                    fileWriter.write(pagesData.toString(4));  
	                    System.out.println("Страница " + pageJsonFile + " удалена из pages.json.");
	                }
	            } else {
	                System.out.println("Страница с url " + pageJsonFile + " не найдена в pages.json.");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
		
		public static boolean checkIfImgFolderIsEmpty() {
		    String directoryPath = ProjectManager.getDirectoryImg();
		    File directory = new File(directoryPath);

		    if (directory.isDirectory() && (directory.listFiles() == null || directory.listFiles().length == 0)) {
		        return true;
		    } else {
		    	return false;
		    }
		}
	public static void saveTimer() {
    try {
        String directory = ProjectManager.getDirectory();
        String filePath = directory + "/pages.cfg";
        
        File file = new File(filePath);
        String content = readFile(file);
        
        JSONObject jsonObject = new JSONObject(content);
        
        String currentUrl = ProjectManager.getJsonFile();

        JSONArray pages = jsonObject.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (page.getString("url").equals(currentUrl)) {
                JSONArray modules;
                if (page.has("modules") && page.get("modules") != JSONObject.NULL) {
                    modules = page.getJSONArray("modules");
                } else {
                    modules = new JSONArray();
                    page.put("modules", modules);  
                }
                
                JSONObject module = new JSONObject();
                module.put("name", "timer");
                module.put("initscripts", new JSONArray().put("templates/modules/timer/startCountdown.js"));
                module.put("startup", JSONObject.NULL);  
                modules.put(module);
                
                break;
            }
        }
        
        writeFile(file, jsonObject.toString(4));  
        
        System.out.println("File updated successfully!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	private static String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void writeFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
	
    public static void loadPagesConfig() {
        File pagesConfigFile = new File(ProjectManager.getDirectory() + "/pages.cfg");
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(pagesConfigFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray pages = jsonObject.getJSONArray("pages");

        String currentUrl = ProjectManager.getJsonFile(); 
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (page.getString("url").equals(currentUrl)) {
                if (page.has("modules") && !page.get("modules").equals("null")) {
                    JSONArray modules = page.getJSONArray("modules");
                    for (int j = 0; j < modules.length(); j++) {
                        JSONObject module = modules.getJSONObject(j);
                        
                        
                        if ("timer".equals(module.getString("name"))) {
                            if (module.has("initscripts")) {
                                JSONArray initScripts = module.getJSONArray("initscripts");
                                for (int k = 0; k < initScripts.length(); k++) {
                                    String scriptPath = initScripts.getString(k);
                                    System.out.println("Executing script: " + scriptPath);
                                    CEFWebView.ExecuteJS(scriptPath); 
                                }
                            }
                        }
                        if ("snow".equals(module.getString("name"))) {
                            if (module.has("initscripts")) {
                                JSONArray initScripts = module.getJSONArray("initscripts");
                                for (int k = 0; k < initScripts.length(); k++) {
                                    String scriptPath = initScripts.getString(k);
                                    System.out.println("Executing script: " + scriptPath);
                                    CEFWebView.ExecuteJS(scriptPath); 
                                }
                            }
                        }
                        if ("parallax".equals(module.getString("name"))) {
                        	System.out.println("ЗАПУСК ПАРАЛЛАКСА");
                            if (module.has("initscripts")) {
                                JSONArray initScripts = module.getJSONArray("initscripts");
                                for (int k = 0; k < initScripts.length(); k++) {
                                    String scriptPath = initScripts.getString(k);
                                    System.out.println("Executing script: " + scriptPath);
                                    CEFWebView.ExecuteJS(scriptPath);
                                }
                            }
                        }
                        if ("matter".equals(module.getString("name"))) {
                            if (module.has("initscripts")) {
                                JSONArray initScripts = module.getJSONArray("initscripts");
                                for (int k = 0; k < initScripts.length(); k++) {
                                    String scriptPath = initScripts.getString(k);
                                    System.out.println("Executing script: " + scriptPath);
                                    CEFWebView.ExecuteJS(scriptPath); 
                                }
                            }

                            if (module.has("startup")) {
                                JSONObject startup = module.getJSONObject("startup");
                                if (startup.has("exec")) {
                                    String execCommand = startup.getString("exec");
                                    System.out.println("Executing exec command: " + execCommand);
                                    CEFWebView.ExecuteJSOneString(execCommand); 
                                }
                            }
                        }
                    }
                }
            }
        }
    }
	
    public static void saveSnow() {
    try {
        // Получаем путь к файлу pages.cfg
        String directory = ProjectManager.getDirectory();
        String filePath = directory + "/pages.cfg";
        
        // Читаем файл
        File file = new File(filePath);
        String content = readFile(file);
        
        // Преобразуем содержимое в JSONObject
        JSONObject jsonObject = new JSONObject(content);
        
        // Получаем текущий URL
        String currentUrl = ProjectManager.getJsonFile();
        System.out.println("URL ДЛЯ ЗАПИСИ" + currentUrl);
        // Находим соответствующую страницу по url
        JSONArray pages = jsonObject.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (page.getString("url").equals(currentUrl)) {
                // Если найдено соответствие, обновляем поле "modules"
                JSONArray modules;
                if (page.has("modules") && page.get("modules") != JSONObject.NULL) {
                    // Если поле "modules" существует и не равно null, добавляем в существующий массив
                    modules = page.getJSONArray("modules");
                } else {
                    // Если поле "modules" не существует или равно null, создаём новый массив
                    modules = new JSONArray();
                    page.put("modules", modules);  // Обновляем страницу, добавляем пустой массив
                }
                
                JSONObject module = new JSONObject();
                module.put("name", "snow");
                module.put("initscripts", new JSONArray().put("templates/modules/snow/snow.js"));
                module.put("startup", JSONObject.NULL);  // Используем JSONObject.NULL для корректного null
                
                // Добавляем новый модуль в массив
                modules.put(module);
                
                break;
            }
        }
        
        // Сохраняем изменения обратно в файл
        writeFile(file, jsonObject.toString(4));  // Красиво отформатированный JSON
        
        System.out.println("File updated successfully!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public static void saveParallax() {
    try {
        // Получаем путь к файлу pages.cfg
        String directory = ProjectManager.getDirectory();
        String filePath = directory + "/pages.cfg";
        
        // Читаем файл
        File file = new File(filePath);
        String content = readFile(file);
        
        // Преобразуем содержимое в JSONObject
        JSONObject jsonObject = new JSONObject(content);
        
        // Получаем текущий URL
        String currentUrl = ProjectManager.getJsonFile();
        
        // Находим соответствующую страницу по url
        JSONArray pages = jsonObject.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (page.getString("url").equals(currentUrl)) {
                // Если найдено соответствие, обновляем поле "modules"
                JSONArray modules;
                if (page.has("modules") && page.get("modules") != JSONObject.NULL) {
                    // Если поле "modules" существует и не равно null, добавляем в существующий массив
                    modules = page.getJSONArray("modules");
                } else {
                    // Если поле "modules" не существует или равно null, создаём новый массив
                    modules = new JSONArray();
                    page.put("modules", modules);  // Обновляем страницу, добавляем пустой массив
                }
                
                JSONObject module = new JSONObject();
                module.put("name", "parallax");
                module.put("initscripts", new JSONArray().put("templates/modules/parallax/parallax.js"));
                module.put("startup", JSONObject.NULL);  // Используем JSONObject.NULL для корректного null
                
                // Добавляем новый модуль в массив
                modules.put(module);
                
                break;
            }
        }
        
        // Сохраняем изменения обратно в файл
        writeFile(file, jsonObject.toString(4));  // Красиво отформатированный JSON
        
        System.out.println("File updated successfully!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
	public static void saveFallingObjects(String jsImagesArrayString, int objectCount) {
    try {
        // Получаем путь к файлу pages.cfg
        String directory = ProjectManager.getDirectory();
        String filePath = directory + "/pages.cfg";
        
        // Читаем файл
        File file = new File(filePath);
        String content = readFile(file);
        
        // Преобразуем содержимое в JSONObject
        JSONObject jsonObject = new JSONObject(content);
        
        // Получаем текущий URL
        String currentUrl = ProjectManager.getJsonFile();
        
        // Находим соответствующую страницу по url
        JSONArray pages = jsonObject.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (page.getString("url").equals(currentUrl)) {
                // Если найдено соответствие, обновляем поле "modules"
                JSONArray modules;
                if (page.has("modules") && page.get("modules") != JSONObject.NULL) {
                    // Если поле "modules" существует и не равно null, добавляем в существующий массив
                    modules = page.getJSONArray("modules");
                } else {
                    // Если поле "modules" не существует или равно null, создаём новый массив
                    modules = new JSONArray();
                    page.put("modules", modules); 
                }
                
                JSONObject module = new JSONObject();
                module.put("name", "matter");
                
                JSONArray initscripts = new JSONArray();
                initscripts.put("templates/modules/matter/matter.js");
                initscripts.put("templates/modules/matter/setupCanvasAndPhysics.js");
                module.put("initscripts", initscripts);
                
                jsImagesArrayString = jsImagesArrayString.trim();
                if (jsImagesArrayString.startsWith("const")) {
                    jsImagesArrayString = jsImagesArrayString.substring(5).trim(); // Убираем 'const'
                }
                if (jsImagesArrayString.endsWith(";")) {
                    jsImagesArrayString = jsImagesArrayString.substring(0, jsImagesArrayString.length() - 1).trim(); // Убираем ';'
                }

                String startupScript = "setupCanvasAndPhysics(" + jsImagesArrayString + ", " + objectCount + ");";
                JSONObject startup = new JSONObject();
                startup.put("exec", startupScript);
                startup.put("value", JSONObject.NULL);
                module.put("startup", startup);
                modules.put(module);
                break;
            }
        }
        writeFile(file, jsonObject.toString(4));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
