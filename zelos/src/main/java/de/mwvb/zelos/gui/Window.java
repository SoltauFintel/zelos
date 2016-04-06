package de.mwvb.zelos.gui;

import java.io.IOException;

import com.sun.javafx.scene.control.skin.TextAreaSkin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class Window<CTR> {
	protected CTR controller;
	
	public final void show(Stage stage, boolean modal) {
		stage.getIcons().add(new Image(getClass().getResourceAsStream(getClass().getSimpleName() + ".png")));
		Scene scene = new Scene(root());
		stage.setScene(scene);
		keyBindings(scene);
		initWindow(stage);
		// TODO Fensterposition wiederherstellen
		stage.setOnCloseRequest(event -> {
			// TODO Fensterposition merken
			onClose();
		});
		if (modal) {
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} else {
			stage.show();
		}
	}

	protected void initWindow(Stage stage) {
		// Template-Methode
	}
	
	protected void keyBindings(Scene scene) {
		// Template-Methode
	}
	
	protected void onClose() {
		// Template-Methode
	}

	protected Parent root() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(getClass().getSimpleName() + ".fxml"));
			controller = createController();
			loader.setController(controller);
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract CTR createController();
	
	public static void errorAlert(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fehler");
		alert.setHeaderText("");
		alert.setContentText(ex.getMessage());
		alert.showAndWait();
	}

	public static void disableTabKey(final TextArea textArea) {
		textArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.TAB)) {
				TextAreaSkin skin = (TextAreaSkin) textArea.getSkin();
				if (event.isShiftDown()) {
					skin.getBehavior().traversePrevious();
				} else {
					skin.getBehavior().traverseNext();
				}
				event.consume();
			}
		});
	}
}
