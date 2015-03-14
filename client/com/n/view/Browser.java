package com.n.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.n.utility.view.LodingPaneForBrowser;

public class Browser extends JPanel {

	private static final long serialVersionUID = -1073228227099117909L;
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	private String url;
	private CardLayout layout = new CardLayout();
	String defaultShow = "<body><div style='font-size: 2em; text-align:"
			+ " center;'>Kuet Chat Home</div><hr><div><h4 style='color:red;'>Kuet Chat Home is unavailable "
			+ "at the moment. Check back later to see your news and alerts.<h4></div></body>";

	public Browser(String url) {
		super();
		this.url = url;
		setLayout(new BorderLayout());
		initComponents();
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				Browser.this.loadURL(url);
			}
		});
	}

	private void initComponents() {
		createScene();
		setLayout(layout);

		LodingPaneForBrowser lod = new LodingPaneForBrowser();
		add(lod, "lod");
		add(lod);
		add(jfxPanel, "fx");
		layout.show(this, "lod");
	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				engine.getLoadWorker().stateProperty()
						.addListener(new ChangeListener<State>() {

							@Override
							public void changed(
									ObservableValue<? extends State> observable,
									State oldValue, State newValue) {
								if (newValue == State.SUCCEEDED) {
									layout.show(Browser.this, "fx");
								}
								if (newValue == State.FAILED) {
									engine.loadContent(defaultShow, "text/html");
								}
							}
						});

				engine.getLoadWorker().workDoneProperty()
						.addListener(new ChangeListener<Number>() {
							@Override
							public void changed(
									ObservableValue<? extends Number> observableValue,
									Number oldValue, final Number newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										// TODO DO HERE WHEN DONE
									}
								});
							}
						});

				jfxPanel.setScene(new Scene(view));
			}
		});
	}

	public void loadURL(final String url) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = toURL(url);

				if (tmp == null) {
					tmp = toURL("http://" + url);
				}
				engine.load(tmp);
			}
		});
	}

	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}
}