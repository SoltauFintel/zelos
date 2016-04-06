package de.mwvb.zelos.gui.entitaet;

import de.mwvb.zelos.gui.Dialog;
import de.mwvb.zelos.gui.DialogModel;
import de.mwvb.zelos.model.entity.Entitaet;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class EntitaetWindow extends Dialog<EntitaetWindowController, Entitaet> {
	
	public EntitaetWindow(DialogModel<Entitaet> model) {
		super(model, model.neu ? "Neue Entität" : ("Entität " + model.e.getName() + " bearbeiten"));
	}

	@Override
	protected EntitaetWindowController createController() {
		return new EntitaetWindowController();
	}
	
	@Override
	protected void initWindow(Stage stage) {
		stage.setWidth(700);
		stage.setHeight(600);
		stage.setMinWidth(610);
		stage.setMinHeight(400);
		
		controller.model2View();
	}
	
	
	@Override
	protected void keyBindings(Scene scene) {
		scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			final KeyCode code = event.getCode();
			if (KeyCode.F2.equals(code) || KeyCode.INSERT.equals(code)) {
				controller.onNeu();
			} else if (KeyCode.ENTER.equals(code)) {
				controller.onBearbeiten();
			} else if (KeyCode.DELETE.equals(code)) {
				controller.onLoeschen();
			} else if (KeyCode.ESCAPE.equals(code)) {
				controller.onCancel();
			} else if (KeyCode.F8.equals(code)) {
				controller.onApply();
			}
			// TODO Feature-Idee: Mit einer Tastenkombi das aktuell gewählte Feld duplizieren.
		});
	}
}
