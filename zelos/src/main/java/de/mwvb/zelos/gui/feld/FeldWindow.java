package de.mwvb.zelos.gui.feld;

import de.mwvb.zelos.gui.Dialog;
import de.mwvb.zelos.gui.DialogModel;
import de.mwvb.zelos.model.entity.Feld;
import javafx.stage.Stage;

public class FeldWindow extends Dialog<FeldWindowController, Feld> {

	public FeldWindow(DialogModel<Feld> model) {
		super(model, (model.neu ? "Neues Feld" : ("Feld " + model.e.getName() + " bearbeiten")) + " (Tabelle " + model.parentBez + ")");
	}

	@Override
	protected FeldWindowController createController() {
		return new FeldWindowController();
	}
	
	@Override
	protected void initWindow(Stage stage) {
		stage.setWidth(700 - 50);
		stage.setHeight(600 -150+70- 50);
		stage.setMinWidth(610 - 50);
		stage.setMinHeight(400 -150+70- 50);
		
		controller.model2View();
	}
}
