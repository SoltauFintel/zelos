package de.mwvb.zelos.gui.feld;

import de.mwvb.zelos.ZelosApplication;
import de.mwvb.zelos.gui.DialogModel;
import de.mwvb.zelos.gui.Window;
import de.mwvb.zelos.model.entity.Datentyp;
import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.ValidatorException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FeldWindowController {
	@FXML
	private Button apply;
	@FXML
	private TextField name;
	@FXML
	private TextField nameTabelle;
	@FXML
	private TextArea beschreibung;
	@FXML
	private TextArea eigenschaften;
	@FXML
	private ComboBox<Datentyp> typ;
	@FXML
	private TextField laenge;
	@FXML
	private CheckBox erforderlich;
	@FXML
	private TextField vorbelegung;
	
	@FXML
	protected void initialize() {
		if (ZelosApplication.modell.getModelltyp().getDatentypen().isEmpty()) {
			throw new RuntimeException("Es gibt keine Datentypen!");
		}
		typ.getItems().addAll(ZelosApplication.modell.getModelltyp().getDatentypen());
		Window.disableTabKey(beschreibung);
		Window.disableTabKey(eigenschaften);
		typ.setOnAction((event) -> showLaenge());
	}

	private void showLaenge() {
		Datentyp dt = typ.getSelectionModel().getSelectedItem();
		laenge.setVisible(!dt.getBezeichnung().toLowerCase().equals("id")  && (dt.getJavaTyp().equals("String") || dt.getJavaTyp().equals("char")));
	}
	
	public void model2View() {
		DialogModel<Feld> model = getModel();
		name.setText(model.e.getName());
		nameTabelle.setText(model.e.getNameTabelle());
		beschreibung.setText(model.e.getBeschreibung());
		typ.getSelectionModel().select(model.e.getTyp());
		laenge.setText("" + model.e.getLaenge());
		erforderlich.setSelected(model.e.isErforderlich());
		vorbelegung.setText(model.e.getVorbelegung());
		eigenschaften.setText(model.e.getEigenschaften());
		showLaenge();
	}
	
	private boolean view2Model() {
		try {
			// technische Validierung
			Datentyp gewaehlterTyp = typ.getSelectionModel().getSelectedItem();
			if (gewaehlterTyp == null) {
				throw new ValidatorException("Bitte Datentyp wählen!", getModel().parentBez, getModel().e.getName());
			}
			int l;
			try {
				l = Integer.parseInt(laenge.getText().trim());
			} catch (NumberFormatException e) {
				throw new ValidatorException("Bitte für Länge eine Zahl eingeben!", getModel().parentBez, getModel().e.getName());
			}
		
			Feld feld = getModel().e;
			feld.setTyp(gewaehlterTyp);
			feld.setErforderlich(erforderlich.isSelected());
			feld.setName(name.getText().trim());
			feld.setNameTabelle(nameTabelle.getText().trim());
			feld.setVorbelegung(vorbelegung.getText());
			feld.setBeschreibung(beschreibung.getText().trim());
			feld.setEigenschaften(eigenschaften.getText().trim());
			feld.setLaenge(l);
			feld.validate();
			return true;
		} catch (ValidatorException e) {
			alert(e.getMessage());
			return false;
		}
	}
	
	private void alert(String meldung) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Validierung");
		alert.setHeaderText("");
		alert.setContentText(meldung);
		alert.showAndWait();
	}
	
	@FXML
	protected void onApply() {
		if (view2Model()) {
			getModel().ok = true;
			getStage().close();
		}
	}
	
	@FXML
	protected void onCancel() {
		getStage().close();
	}

	@SuppressWarnings("unchecked")
	private DialogModel<Feld> getModel() {
		return (DialogModel<Feld>) getStage().getProperties().get("model");
	}
	
	private Stage getStage() {
		return (Stage) apply.getScene().getWindow();
	}
}
