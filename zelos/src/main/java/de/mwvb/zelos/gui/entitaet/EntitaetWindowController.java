package de.mwvb.zelos.gui.entitaet;

import java.util.List;

import de.mwvb.zelos.gui.DialogModel;
import de.mwvb.zelos.gui.Window;
import de.mwvb.zelos.gui.feld.FeldWindow;
import de.mwvb.zelos.model.entity.Entitaet;
import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.ValidatorException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO ähnlicher oder gleicher Code mit MainWindowController und FeldWindowController !!!
public class EntitaetWindowController {
	@FXML
	private TableView<Feld> grid;
	@FXML
	private Button apply;
	@FXML
	private Button loeschen;
	@FXML
	private TextField name;
	@FXML
	private TextField nameTabelle;
	@FXML
	private TextArea beschreibung;
	@FXML
	private TextArea eigenschaften;

	@FXML
	protected void initialize() {
		onDoubleClick();
		Window.disableTabKey(beschreibung);
		Window.disableTabKey(eigenschaften);
	}

	// muss von außen aufgerufen werden, in initialize() wäre es zu früh
	void model2View() {
		DialogModel<Entitaet> model = getModel();
		name.setText(model.e.getName());
		nameTabelle.setText(model.e.getNameTabelle());
		beschreibung.setText(model.e.getBeschreibung());
		eigenschaften.setText(model.e.getEigenschaften());
		
		grid.getItems().addAll(model.e.getFelder());
	}
	
	private boolean view2Model() {
		try {
			Entitaet entitaet = getModel().e;
			entitaet.setName(name.getText() == null ? "" : name.getText().trim());
			entitaet.setNameTabelle(nameTabelle.getText() == null ? "" : nameTabelle.getText().trim());
			entitaet.setBeschreibung(beschreibung.getText() == null ? "" : beschreibung.getText().trim());
			entitaet.setEigenschaften(eigenschaften.getText() == null ? "" : eigenschaften.getText().trim());
			entitaet.validate();
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
//		alert.getButtonTypes().setAll(ButtonType.OK);
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

	@FXML
	protected void onNeu() {
		bearbeiten(getModel().e.createFeld(), true);
	}

	private void onDoubleClick() {
		// Doppelklick -> Bearbeiten
		grid.setRowFactory(it -> {
			TableRow<Feld> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					bearbeiten(row.getItem(), false);
				}
			});
			return row;
		});
	}

	@FXML
	protected void onBearbeiten() {
		if (grid.getSelectionModel().getSelectedItems().size() == 1) {
			bearbeiten(grid.getSelectionModel().getSelectedItem(), false);
		}
	}
	
	private void bearbeiten(Feld feld, boolean neu) {
		if (neu) {
			grid.getSelectionModel().clearSelection();
		}
		DialogModel<Feld> fm = new DialogModel<>(feld, neu);
		fm.parentBez = getModel().e.getName();
		if (new FeldWindow(fm).show()) {
			if (neu) {
				grid.getItems().add(feld);
				grid.getSelectionModel().select(feld);
				getModel().e.getFelder().add(feld);
			} else {
				refreshGrid();
			}
		}
	}
	
	private void refreshGrid() {
		grid.getColumns().get(0).setVisible(false);  // Workaround für Refresh der Zeile
		grid.getColumns().get(0).setVisible(true);
	}

	@FXML
	protected void onLoeschen() {
		ObservableList<Feld> auswahl = grid.getSelectionModel().getSelectedItems();
		int n = auswahl.size();
		if (n < 1) return;
		Integer last = grid.getSelectionModel().getSelectedIndices().get(grid.getSelectionModel().getSelectedIndices().size() - 1);
		
		getFelder_work().removeAll(auswahl); 
		grid.getItems().removeAll(auswahl);

		if (getFelder_work().isEmpty()) {
			loeschen.setDisable(true);
		} else {
			grid.getSelectionModel().clearSelection();
			if (last > grid.getItems().size() - 1) {
				last = grid.getItems().size() - 1;
			}
			grid.getSelectionModel().select(last);
		}
	}
	
	private List<Feld> getFelder_work() {
		// FIXME Änderungen auf dieser Rückgabe dürfen erst mit onApply() übernommen werden! Denn sonst wirkt der Abbruch Button nicht.
		//       Es muss eine Arbeitskopie der Liste und der Einträge gemacht werden.
		return getModel().e.getFelder();
	}
	
	@SuppressWarnings("unchecked")
	private DialogModel<Entitaet> getModel() {
		return (DialogModel<Entitaet>) getStage().getProperties().get("model");
	}
	
	private Stage getStage() {
		return (Stage) apply.getScene().getWindow();
	}
}
