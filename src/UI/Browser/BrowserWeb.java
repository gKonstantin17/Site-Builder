package UI.Browser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import UI.Controllers.RightPanelController;
import UI.Controllers.ToolbarController;
import UI.DataTransfer.ProjectManager;
import UI.Helpers.TagGroups;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BrowserWeb {
	static final boolean OFFSCREEN = false;
	static final boolean TRANSPARENT = false;
	static CefBrowser browser;
	static CefClient client;
	static CefSettings settings;
	static CefMessageRouter messageRouter;
	
	public static void createBrowser(String URL,Stage _stage)
	{
		setSetting();
        initClient();
        initMessageRouter();
        configureMessageRouter(_stage); 
        runBrowser(URL);
	}
	
	private static void setSetting()
	{
		settings = new CefSettings();
		settings.windowless_rendering_enabled = OFFSCREEN;
	}
	
	private static void initClient()
	{
		CefApp cefApp = CefApp.getInstance(settings);
		client = cefApp.createClient();
	}
	
	private static void initMessageRouter()
	{
		messageRouter = CefMessageRouter.create();
		client.addMessageRouter(messageRouter);
	}
	
	public static void runBrowser(String URL)
	{
		browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
	}
	
	public static CefBrowser getBrowser()
	{
		return browser;
	}
	private static void configureMessageRouter(Stage _stage) 
	{
		messageRouter.addHandler(new CefMessageRouterHandler() {

			@Override
			public long getNativeRef(String arg0) {
				return 0;
			}
			@Override
			public void setNativeRef(String arg0, long arg1) {
			}

			@Override
			public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent,
					CefQueryCallback callback) {
				// check request from browser
				if (request.startsWith("logMessage:")) {
					getWebElement(request,callback, _stage);
					return true; 
				}
				if (request.startsWith("serMessage:")) {
					getSerializatedElements(request, callback);
					return true; 
				}
				
				return false;
			}

			@Override
			public void onQueryCanceled(CefBrowser arg0, CefFrame arg1, long arg2) {
			}
		}, false);
	}
	
	private static void getWebElement(String request, CefQueryCallback callback,Stage _stage)
	{
		
			// excract message from request
			String message = request.substring("logMessage:".length());

			JSONObject json = new JSONObject(message);
			System.out.println(json);
			String id = json.getString("id");
			String tagName = json.getString("tagName");	
			
			JSONObject styles = json.getJSONObject("styles");

			String text = json.getString("text");
			String fontSize = styles.optString("font-size", "16px");
			String colorText = styles.optString("color", "#000000");
			String fontWeight = styles.optString("font-weight", "400");
			
			String backgroundColor = styles.optString("background-color", "#FFFFFF");
			String width = styles.optString("width", "200px");
			String height = styles.optString("height", "150px");
			String opacity = styles.optString("opacity", "1");
			String borderWidth = styles.optString("border-width", "0px");
			String borderStyle = styles.optString("border-style", "none");
			String borderColor = styles.optString("border-color", "#000000");
			String borderRadius = styles.optString("border-radius", "0%");

			Scene scene = _stage.getScene();
			Platform.runLater(() -> {
				// try use fx:id, but fxml elements is null
//                	MainSceneController msc = new MainSceneController();
//					msc.idSelected.setText(id);
//					msc.areaProperty.setText(text);
//					msc.fontSizeProperty.setText(fontSize);
//					msc.colorTextProperty.setValue(Color.web(colorText));
//					msc.colorBackgroundProperty.setValue(Color.web(backgroundColor));

				// transfer to fxml on styleClass
				Label labelId = (Label) scene.lookup(".id-selected");
				labelId.setText(id);
				Label labelTag = (Label) scene.lookup(".tag-selected");
				if (id != "")
				{
					ToolbarController.setiIdSelectedForDelete(labelId);
					labelTag.setText(tagName);
				} else {
					labelTag.setText("не выбран");
				}

				TextArea textArea = (TextArea) scene.lookup(".area-property");
				textArea.setText(text);

				TextField textField = (TextField) scene.lookup(".font-size-property");
				textField.setText(fontSize);

				ColorPicker textColorPicker = (ColorPicker) scene.lookup(".color-text-property");
				textColorPicker.setValue(Color.web(colorText));

				ComboBox<String> fontWeightTF = (ComboBox<String>) scene.lookup(".font-weight-property");
				fontWeightTF.setValue(fontWeight);
				
				
				
				ColorPicker backgroundColorPicker = (ColorPicker) scene.lookup(".color-background-property");
				backgroundColorPicker.setValue(Color.web(backgroundColor));
				
				TextField blockWidth = (TextField) scene.lookup(".width-property"); 
				blockWidth.setText(width);
						
				TextField blockHeight = (TextField) scene.lookup(".heigth-property"); 
				blockHeight.setText(height);
				
				TextField opacityTF = (TextField) scene.lookup(".opacity-property"); 
				opacityTF.setText(opacity);
				
				TextField borderWidthTF = (TextField) scene.lookup(".border-width-property");
				borderWidthTF.setText(borderWidth);
				
				ComboBox<String> borderStyleTF = (ComboBox<String>) scene.lookup(".border-style-property");
				borderStyleTF.setValue(borderStyle);
				
				ColorPicker borderColorTF = (ColorPicker) scene.lookup(".border-color-property");
				borderColorTF.setValue(Color.web(borderColor));
				
				TextField borderRadiusTF = (TextField) scene.lookup(".border-radius-property");
				borderRadiusTF.setText(borderRadius);
				// show right pael
				if (id != "" && !id.equals("myCanvas"))
				{
					VBox rightContent = (VBox) scene.lookup(".vbox-right-content");
					rightContent.setVisible(true);
					
					VBox vboxVisibleRight = (VBox) scene.lookup(".visible-right");
					ImageView visibleRightImg = (ImageView) scene.lookup(".visible-right-img");
					Image image = new Image("file:src/resources/icons/hide.png"); 
			    	visibleRightImg.setImage(image);
			        vboxVisibleRight.setMinWidth(225);
			        vboxVisibleRight.setMaxWidth(225);
			        
			     // show params
			        VBox vboxText = (VBox) scene.lookup(".vbox-right-content-text");
			        VBox vboxFontSize = (VBox) scene.lookup(".vbox-right-content-fontSize");
			        VBox vboxFontColor = (VBox) scene.lookup(".vbox-right-content-fontColor");
			        VBox vboxFontWeight = (VBox) scene.lookup(".vbox-right-content-font-weight");
			        
			        VBox vboxBackgroundColor = (VBox) scene.lookup(".vbox-right-content-backgroundColor");
			        VBox vboxSize = (VBox) scene.lookup(".vbox-right-content-size");
			        VBox vboxBorder = (VBox) scene.lookup(".vbox-right-content-border");
			        VBox vboxBorderRadius = (VBox) scene.lookup(".vbox-right-content-borderRadius");
			        
			        
					if (tagName != "")
					{
						if (TagGroups.isBlockTag(tagName))
						{
							
							setShowingVbox(vboxText,false);
							setShowingVbox(vboxFontSize,false);
							setShowingVbox(vboxFontColor,false);
							setShowingVbox(vboxFontWeight,false);
							
							setShowingVbox(vboxSize,true);
							setShowingVbox(vboxBackgroundColor,true);
							setShowingVbox(vboxBorder,true);
							setShowingVbox(vboxBorderRadius,true);

						}

						if (TagGroups.isTextTag(tagName))
						{
							setShowingVbox(vboxText,true);
							setShowingVbox(vboxFontSize,true);
							setShowingVbox(vboxFontColor,true);
							setShowingVbox(vboxFontWeight,true);
							
							setShowingVbox(vboxBackgroundColor,false);
							setShowingVbox(vboxSize,false);
							setShowingVbox(vboxBorder,false);
							setShowingVbox(vboxBorderRadius,false);
						}
						
						if (TagGroups.isButtonTag(tagName))
						{
							setShowingVbox(vboxText,true);
							setShowingVbox(vboxFontSize,true);
							setShowingVbox(vboxFontColor,true);
							setShowingVbox(vboxFontWeight,true);
							
							setShowingVbox(vboxBackgroundColor,true);
							setShowingVbox(vboxSize,false);
							setShowingVbox(vboxBorder,true);
							setShowingVbox(vboxBorderRadius,true);
						}
					}
				}
				
				
			});

			callback.success(""); 
			
			
			
	}
	private static void getSerializatedElements(String request,CefQueryCallback callback)
	{

		String message = request.substring("serMessage:".length());


		JSONArray json = new JSONArray(message);


        String fileName = ProjectManager.getDirectory() + "/"+ ProjectManager.getJsonFile();

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json.toString(4)); 
            System.out.println("JSON был успешно записан в файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        

		callback.success(""); 
	}
	
	private static void setShowingVbox(VBox vbox, boolean flag)
	{
		vbox.setVisible(flag);
		vbox.setManaged(flag);
	}
}
