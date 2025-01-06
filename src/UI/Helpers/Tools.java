package UI.Helpers;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Tools {
	CREATE_DIV("createDiv.js"),
	CREATE_P("createP.js"),
	CREATE_H1("createH1.js"),
	CREATE_H2("createH2.js"),
	CREATE_H3("createH3.js"),
	CREATE_COLUMNS("createColumns.js"),
	CREATE_BUTTON("createButton.js"),
	CREATE_IMAGE("createImg.js"),
	CREATE_FORM("createForm.js"),
	CREATE_FIELD("createField.js"),
	CREATE_HEADER("createHeader.js"),
	CREATE_SECTION("createSection.js"),
	DELETE("deleteElemet.js"),
	SAVE("serialization.js"),
	LOAD("loaderElements.js");
	
	
	String path;
	
	Tools(String path) {
		this.path = path;
	}

	public String getPath() {
		String filePath = "templates/tools/" + path;
		Path absolutePath = Paths.get(filePath).toAbsolutePath();
        return absolutePath.toString();
	}

	public void setPath(String path) {
		this.path = path; // файл в папке tools
	}
}
