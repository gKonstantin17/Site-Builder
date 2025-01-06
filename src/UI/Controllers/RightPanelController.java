package UI.Controllers;

import UI.CEFWebView;
import UI.Helpers.TagGroups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RightPanelController {
	@FXML private ImageView visibleRightImg;
	@FXML  private VBox rightContent;
	@FXML private VBox vboxVisibleRight;
	@FXML private VBox vboxText;
	@FXML private VBox vboxFontSize;
	@FXML private VBox vboxFontWeight;
	@FXML private VBox vboxFontColor;
	
	@FXML private Label idSelected;
	@FXML private Label tagSelected;
	
	@FXML private TextArea areaProperty;
	@FXML private TextField fontSizeProperty;
	@FXML private ColorPicker colorTextProperty;
	@FXML private ComboBox<String> fontWeightProperty; 
	
	@FXML private ColorPicker colorBackgroundProperty;
	@FXML private TextField widthProperty;
	@FXML private TextField heigthProperty;
	@FXML private TextField opacityProperty;
	@FXML private TextField borderWidthProperty; 
	@FXML private ComboBox<String>  borderStyleProperty;
	@FXML private ColorPicker borderColorProperty;
	@FXML private TextField borderRadiusProperty;
	
	@FXML private Button changer;

    @FXML
    private void initialize() {
    	rightContent.setVisible(false);

    	setShowingVbox(vboxText,false);
		setShowingVbox(vboxFontSize,false);
		setShowingVbox(vboxFontColor,false);
		setShowingVbox(vboxFontWeight,false);
        Image image = new Image("file:src/resources/icons/set_element.png"); 
        visibleRightImg.setImage(image);
        
     // dont show id
        idSelected.setVisible(false);
        ObservableList<String> styles = FXCollections.observableArrayList(
        	"none", "solid","dotted","dashed","groove","hidden"
        		);
        borderStyleProperty.setItems(styles);
        ObservableList<String> fontWeights = FXCollections.observableArrayList(
            	"100", "200","300","400","500","600","700","800","900"
            		);
        fontWeightProperty.setItems(fontWeights);
        
    }
    

	public void visibleRight()
	{
		rightContent.setVisible(!rightContent.isVisible());
        if (rightContent.isVisible()) {
        	Image image = new Image("file:src/resources/icons/hide.png"); 
        	visibleRightImg.setImage(image);
            vboxVisibleRight.setMinWidth(225);
            vboxVisibleRight.setMaxWidth(225);
        } else {
        	Image image = new Image("file:src/resources/icons/set_element.png"); 
        	visibleRightImg.setImage(image);
            vboxVisibleRight.setMinWidth(15);
            vboxVisibleRight.setMaxWidth(15);
        }
	}
	public void visibleTrueRight()
	{
		rightContent.setVisible(true);
    	Image image = new Image("file:src/resources/icons/hide.png"); 
    	visibleRightImg.setImage(image);
        vboxVisibleRight.setMinWidth(225);
        vboxVisibleRight.setMaxWidth(225);
        
	}
	
	
	@FXML
    private void change()
	{
		String tag = tagSelected.getText();
		String id = idSelected.getText();
		
		String updatedText = areaProperty.getText();
		String fontSize = fontSizeProperty.getText();
		String textColor = toWebColorString((colorTextProperty.getValue()));
		String fontWeight = fontWeightProperty.getValue();
	    
	    String backgroundColor = toWebColorString(colorBackgroundProperty.getValue());
	    String blockWidth = widthProperty.getText();
	    String blockHeight = heigthProperty.getText();
	    String opacity = opacityProperty.getText();
		String borderWidth = borderWidthProperty.getText();
		String borderStyle = borderStyleProperty.getValue();
		String borderColor = toWebColorString((borderColorProperty.getValue()));
		String borderRadius = borderRadiusProperty.getText();
		
		String request = "";
	    if (TagGroups.isTextTag(tag))
	    {
	    	request = String.format(
		    	    "{" +
		    	        "\"text\":\"%s\"," +
		    	        "\"fontSize\":\"%s\"," +
		    	        "\"color\":\"%s\"," +
		    	        "\"fontWeight\":\"%s\"," +  
		    	    "}",
		    	    updatedText, fontSize, textColor, fontWeight
		    	);
	    }
	    if (TagGroups.isBlockTag(tag))
	    {
	    	 request = String.format(
	 	    	    "{" +
	 	    	        "\"backgroundColor\":\"%s\"," +
	 	    	        "\"width\":\"%s\"," +
	 	    	        "\"height\":\"%s\"," +
	 	    	        "\"opacity\":\"%s\"," +
	 	    	        "\"borderWidth\":\"%s\"," +
	 	    	        "\"borderStyle\":\"%s\"," +
	 	    	        "\"borderColor\":\"%s\"," +
	 	    	        "\"borderRadius\":\"%s\"" +
	 	    	    "}",
	 	    	    backgroundColor,blockWidth, blockHeight, opacity, 
	 	    	    borderWidth, borderStyle, borderColor, borderRadius
	 	    	);
	    }
	    if (TagGroups.isButtonTag(tag))
	    {
	    	 request = String.format(
	 	    	    "{" +
	 	    	        "\"text\":\"%s\"," +
	 	    	        "\"fontSize\":\"%s\"," +
	 	    	        "\"color\":\"%s\"," +
	 	    	        "\"fontWeight\":\"%s\"," +
	 	    	        "\"backgroundColor\":\"%s\"," +
	 	    	       "\"width\":\"%s\"," +
	 	    	        "\"height\":\"%s\"," +
	 	    	        "\"opacity\":\"%s\"," +
	 	    	        "\"borderWidth\":\"%s\"," +
	 	    	        "\"borderStyle\":\"%s\"," +
	 	    	        "\"borderColor\":\"%s\"," +
	 	    	        "\"borderRadius\":\"%s\"" +
	 	    	    "}",
	 	    	    updatedText, fontSize, textColor, fontWeight, backgroundColor, blockWidth, blockHeight,
	 	    	    opacity, borderWidth, borderStyle, borderColor, borderRadius
	 	    	);
	    }   
        CEFWebView.ChangeElemets(id,request);
    
	}
	private String toWebColorString(Color color) { 
		int red = (int) (color.getRed() * 255);
	    int green = (int) (color.getGreen() * 255);
	    int blue = (int) (color.getBlue() * 255);
	    int alpha = (int) (color.getOpacity() * 255); 
	    return String.format("#%02X%02X%02X%02X", red, green, blue, alpha);
	}
	private static void setShowingVbox(VBox vbox, boolean flag)
	{
		vbox.setVisible(flag);
		vbox.setManaged(flag);
	}
	
}
