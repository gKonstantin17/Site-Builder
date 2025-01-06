package UI;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JFrame;

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

import UI.Browser.BrowserView;
import UI.Browser.BrowserWeb;
import UI.Helpers.Tools;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CEFWebView {
	private static Stage _stage;
	private static JFrame frame;
	private static CefBrowser browser;
	// static String URL = "https://google.com";
	private static String URL = "file:///C:/Users/kocm8/eclipse-workspace/Prototype/templates/newproject/index.html";

	public static void start(Stage stage) {
		_stage = stage;
		Web();
		View();
	}

	public static void startOver() {
		if (_stage == null)
			throw new IllegalStateException("This first start!, need to call start(args,stage)");
		BrowserWeb.runBrowser(URL);
		View();
	}

	private static void Web() {
		BrowserWeb.createBrowser(URL,_stage);
		browser = BrowserWeb.getBrowser();
	}

	private static void View() {
		frame = BrowserView.createBrowserFrame(_stage);
		frame.add(browser.getUIComponent(), BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public static void RestartBrowser() {
		startOver();
	}
	public static void RestartFrameBrowser() {
		frame.dispose();
		startOver();
	}

	public static void Reload() {
		browser.reload();
	}

	public static void LoadURL() {
		browser.loadURL(URL);
	}

	public static String ReadJS(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void ExecuteJS(String filePath) {
		String jsCode = ReadJS(filePath);
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}
	public static void ExecuteJSOneString(String jsCode) {
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}

	public static void ChangeElemets(String id, String json) {
		String jsCode = "loaderFromJava(\"" +id + "\"," + json + ");";
		System.out.println(jsCode);
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}
	public static void LoadElements(String json)
	{
		ExecuteJS(Tools.LOAD.getPath());
		String jsCode = "loaderElements("+json+");";
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}

}