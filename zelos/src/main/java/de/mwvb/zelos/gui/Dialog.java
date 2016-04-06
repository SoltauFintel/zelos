package de.mwvb.zelos.gui;

import javafx.stage.Stage;

// TODO kein Eintrag in Taskbar
public abstract class Dialog<CTR, T> extends Window<CTR> {
	protected final DialogModel<T> model;
	private final String title;
	
	public Dialog(DialogModel<T> model, String title) {
		this.model = model;
		this.title = title;
	}

	public boolean show() {
		Stage stage = new Stage();
		stage.getProperties().put("model", model);
		stage.setTitle(title);
		show(stage, true);
		return model.ok;
	}
}
