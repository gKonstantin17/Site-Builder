package UI.Browser;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import UI.Helpers.StageSingleton;
import javafx.application.Platform;
import javafx.stage.Stage;

public class BrowserView {
	private static JFrame frame;
	private static final int WIDTH = 1535;
	private static final int HEIGHT = 740;
	private static final int X_POS = 0;
	private static final int Y_POS = 105;
	private static final int Y_POS_UNDER_STAGE = 758;
	
	public static JFrame createBrowserFrame(Stage _stage)
	{
		createFrame();
		connectToStage(_stage);
		return frame;
	}
	private static void createFrame() {
		frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);  
		frame.setLocation(X_POS, Y_POS); // between toolbar and condition string
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setType(JFrame.Type.UTILITY); // hide from taskbar
	}
	
	private static void connectToStage(Stage _stage) 
	{
		setMovementToStage(_stage);
		setHideToStage(_stage);
		setCloseToStage(_stage);
		setStageToFront(_stage);
	}
	private static void setMovementToStage(Stage _stage)
	{
		_stage.xProperty().addListener((obs, oldVal, newVal) -> {
			frame.setLocation(newVal.intValue(), frame.getY());
		});
		_stage.yProperty().addListener((obs, oldVal, newVal) -> {
			frame.setLocation(frame.getX(), newVal.intValue() - Y_POS_UNDER_STAGE + (int) _stage.getHeight());
		});
	}
	
	private static void setHideToStage(Stage _stage)
	{
		_stage.iconifiedProperty().addListener((obs, wasIconified, isNowIconified) -> {
			if (isNowIconified) {
				frame.setVisible(false);
			} else {
				frame.setVisible(true);
			}
		});
		_stage.setOnShown(event -> frame.setVisible(true));
		_stage.setOnHidden(event -> frame.setVisible(false));
	}
	private static void setCloseToStage(Stage _stage)
	{
		_stage.setOnCloseRequest(event -> frame.dispose());
	}
	private static void setStageToFront(Stage _stage)
	{
		// when focus on frame, stage to front
		frame.addWindowListener(new WindowAdapter() { 
		    @Override
		    public void windowActivated(WindowEvent e) {
		    	Platform.runLater(() -> {
		        _stage.setAlwaysOnTop(true);
		    	});
		    }
		    @Override
		    public void windowDeactivated(WindowEvent e) {
		    	Platform.runLater(() -> {
		        _stage.setAlwaysOnTop(false);
		    	});
		    }
		});

        // when focus on stage, frame to front, but lower than stage
        _stage.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                Platform.runLater(() -> {
                	 _stage.setAlwaysOnTop(true);
                	//frame.toFront();
                });
            } else {
                Platform.runLater(() -> {
                    _stage.setAlwaysOnTop(false);
                });
            }
        });
        frame.requestFocusInWindow();

	}
	public static JFrame getFrame()
	{
		return frame;
	}
}
