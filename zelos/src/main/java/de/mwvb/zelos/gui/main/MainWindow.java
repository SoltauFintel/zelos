package de.mwvb.zelos.gui.main;

import de.mwvb.zelos.ZelosApplication;
import de.mwvb.zelos.generator.CodeGeneratorFactory;
import de.mwvb.zelos.gui.DialogModel;
import de.mwvb.zelos.gui.Window;
import de.mwvb.zelos.gui.entitaet.EntitaetWindow;
import de.mwvb.zelos.model.entity.Entitaet;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// TODO Über TableViews so Überschriften machen und mit fx-CSS stylen (wie damals) ("Entitäten", "Felder")
public class MainWindow extends Window<MainWindowController> {
	
	@Override
	protected void initWindow(Stage stage) {
		stage.setX(300);
		stage.setY(100);
		stage.setWidth(450);
		stage.setHeight(500);
		stage.setMinWidth(450);
		stage.setMinHeight(210);
		stage.setTitle(ZelosApplication.APP_NAME + " " + ZelosApplication.APP_VERSION);
	}
	
	@Override
	protected void onClose() {
		if (ZelosApplication.modell.isDirty() && sollSpeichern()) {
			ZelosApplication.config.getModellDAO().save(ZelosApplication.modell);
			System.out.println("gespeichert");
		}
	}
	
	private boolean sollSpeichern() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Speichern");
		alert.setHeaderText("");
		ButtonType yes = new ButtonType("Speichern");
		ButtonType no = new ButtonType("Nicht speichern");
		alert.setContentText("Soll gespeichert werden?");
		alert.getButtonTypes().setAll(yes, no);
		return yes == alert.showAndWait().get();
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
			}
		});
	}
	
	public static boolean bearbeiten(Entitaet e, boolean neu) {
		DialogModel<Entitaet> model = new DialogModel<Entitaet>(e, neu);
		return new EntitaetWindow(model).show();
	}
	
	public static void generiere(ObservableList<Entitaet> entitaeten) {
		try {
			// erst alle Entitäten prüfen
			entitaeten.forEach(e -> {
				try {
					e.validate();
				} catch (Exception ex) {
					throw new RuntimeException("Fehler bei der Validierung von Entität " + e.getName() + ": " + ex.getMessage());
				}
			});
			// Generierung
			CodeGeneratorFactory fac = ZelosApplication.config.createCodeGeneratorFactory();
			entitaeten.forEach(e -> fac.create(e).generate());
			System.out.println("Generierung fertig");
		} catch (Exception ex2) {
			Window.errorAlert(ex2);
		}
	}

	@Override
	protected MainWindowController createController() {
		return new MainWindowController();
	}
}
