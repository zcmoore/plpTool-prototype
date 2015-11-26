package edu.asu.plp.tool.prototype.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ConsolePane extends BorderPane
{
	private static final String TEXT_PANE_ID = "textPane";
	private static final String TEXT_PANE_CLASS = "scrollPane";
	private static final String CSS_MESSAGE_CLASS = "message";
	
	private Element textPaneElement;
	private WebEngine webEngine;
	
	public ConsolePane()
	{
		WebView view = new WebView();
		webEngine = view.getEngine();
		
		webEngine.getLoadWorker().stateProperty()
				.addListener(new ChangeListener<State>() {
					public void changed(ObservableValue<? extends State> value,
							State oldState, State newState)
					{
						if (newState == State.SUCCEEDED)
						{
							Document dom = webEngine.getDocument();
							textPaneElement = dom.createElement("div");
							textPaneElement.setAttribute("id", TEXT_PANE_ID);
							textPaneElement.setAttribute("class", TEXT_PANE_CLASS);
							
							Node body = dom.getElementsByTagName("body").item(0);
							body.appendChild(textPaneElement);
							
							String cssPath = getClass().getResource(
									"defaultConsoleStyle.css").toExternalForm();
							addStylesheet(cssPath);
							
							for (int i = 0; i < 25; i++)
							{
								println("Test<br/>" + i);
							}
							
							webEngine.getLoadWorker().stateProperty()
									.removeListener(this);
						}
					}
				});
		String content = "<html><head></head><body></body></html>";
		webEngine.loadContent(content);
		
		this.setCenter(view);
	}
	
	public void println(String message)
	{
		Document dom = webEngine.getDocument();
		Element div = dom.createElement("div");
		div.setAttribute("class", CSS_MESSAGE_CLASS);
		
		Element content = dom.createElement("code");
		content.setTextContent(message);
		
		div.appendChild(content);
		textPaneElement.appendChild(div);
	}
	
	public void clear()
	{
		textPaneElement.setTextContent("");
	}
	
	public void addStylesheet(String path)
	{
		Document dom = webEngine.getDocument();
		Node head = dom.getElementsByTagName("head").item(0);
		
		Element styleReference = dom.createElement("link");
		styleReference.setAttribute("rel", "stylesheet");
		styleReference.setAttribute("type", "text/css");
		styleReference.setAttribute("href", path);
		head.appendChild(styleReference);
	}
}
