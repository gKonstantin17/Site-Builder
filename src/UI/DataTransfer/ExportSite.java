package UI.DataTransfer;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExportSite {
    public static void generate() throws IOException {
        // read  cfg
        String configFilePath = ProjectManager.getDirectory() + "/pages.cfg";
        String configContent = new String(Files.readAllBytes(Paths.get(configFilePath)));
        JSONObject configJson = new JSONObject(configContent);

        // get list pages
        JSONArray pages = configJson.getJSONArray("pages");

        // directory for html files
        String outputDirectory = "sites/" + ProjectManager.getProjectName() + "/";

        // create directory if it doesn't exist'
        Files.createDirectories(Paths.get(outputDirectory));

        // directory img
        String imagesDirectory = ProjectManager.getDirectory() + "/img";
        String outputImagesDirectory = outputDirectory + "/img";
        
        // copy to directory img
        if (Files.exists(Paths.get(imagesDirectory))) {
            Files.createDirectories(Paths.get(outputImagesDirectory));
            copyDirectory(Paths.get(imagesDirectory), Paths.get(outputImagesDirectory));
        }

        // generate html files
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            String title = page.getString("title");
            String jsonFilePath = ProjectManager.getDirectory() + "/" + page.getString("url");

            // read json
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONArray jsonArray = new JSONArray(jsonContent);

            String generatedHtml = generateHtmlFromJson(jsonArray);

            // get template for insert html content
            String htmlTemplate = new String(Files.readAllBytes(Paths.get("templates/forExport/index.html")));

            String finalHtml = htmlTemplate.replace("<body></body>", "<body>" + generatedHtml + "</body>");
            String outputFilePath = outputDirectory + title + ".html";
            Files.write(Paths.get(outputFilePath), finalHtml.getBytes(), StandardOpenOption.CREATE);

            System.out.println("HTML файл для страницы \"" + title + "\" успешно создан: " + outputFilePath);
        }
    }

    public static String generateHtmlFromJson(JSONArray jsonArray) {
        StringBuilder htmlContent = new StringBuilder();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            htmlContent.append(createHtmlElement(obj));
        }

        return htmlContent.toString();
    }

    public static String createHtmlElement(JSONObject obj) {
        StringBuilder element = new StringBuilder();

        String tag = obj.getString("tag");
        String id = obj.optString("id", null);
        String styles = obj.optString("styles", null);
        String value = obj.optString("value", null);
        String content = obj.optString("content", null);
        JSONArray children = obj.optJSONArray("children");

        element.append("<").append(tag);

        if (id != null) {
            element.append(" id=\"").append(id).append("\"");
        }
        if (styles != null) {
            element.append(" style=\"").append(styles).append("\"");
        }

        element.append(">");

        if (content != null) {
            element.append(content);
        }
        if (value != null) {
            element.append(value);
        }

        // recursively append children elements
        if (children != null) {
            element.append(generateHtmlFromJson(children));
        }

        element.append("</").append(tag).append(">");

        return element.toString();
    }

    public static void copyDirectory(Path sourceDir, Path destinationDir) throws IOException {
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path destFile = destinationDir.resolve(sourceDir.relativize(file));
                Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path dirToCreate = destinationDir.resolve(sourceDir.relativize(dir));
                Files.createDirectories(dirToCreate);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
