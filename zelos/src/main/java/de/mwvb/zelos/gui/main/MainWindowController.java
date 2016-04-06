package de.mwvb.zelos.gui.main;

import de.mwvb.zelos.ZelosApplication;
import de.mwvb.zelos.model.entity.Entitaet;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class MainWindowController {
	@FXML
	private TableView<Entitaet> grid;
	@FXML
	private Button neu; // TODO erforderlich?
	@FXML
	private Button bearbeiten; // TODO erforderlich?
	@FXML
	private Button loeschen;
	@FXML
	private Button generiere; // TODO erforderlich?
	
	@FXML
	protected void initialize() {
		grid.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		model2View();
		onDoubleClick();
	}
	
	private void model2View() {
		grid.getItems().addAll(ZelosApplication.modell.getEntitaeten());
		
		if (ZelosApplication.modell.getEntitaeten().isEmpty()) {
			loeschen.setDisable(true);
		} else {
			grid.getSelectionModel().select(0);
		}
	}
	
	@FXML
	protected void onNeu() {
		bearbeiten(ZelosApplication.modell.createEntitaet(), true);
	}

	private void onDoubleClick() {
		// Doppelklick -> Bearbeiten
		grid.setRowFactory(it -> {
			TableRow<Entitaet> row = new TableRow<>();
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
			// TODO Ich muss eine Kopie bearbeiten. Und erst wenn der Dialog erfolgreich zurückkehrt, darf ichs zurueckkopieren.
			bearbeiten(grid.getSelectionModel().getSelectedItem(), false);
		}
	}
	
	private void bearbeiten(Entitaet e, boolean neu) {
		if (neu) {
			grid.getSelectionModel().clearSelection();
		}
		if (MainWindow.bearbeiten(e, neu)) {
			if (neu) {
				grid.getItems().add(e);
				grid.getSelectionModel().select(e);
			} else {
				refreshGrid();
			}
			ZelosApplication.modell.setDirty(true);
		}
	}

	private void refreshGrid() {
		grid.getColumns().get(0).setVisible(false);  // Workaround für Refresh der Zeile
		grid.getColumns().get(0).setVisible(true);
	}
	
	@FXML
	protected void onLoeschen() {
		ObservableList<Entitaet> auswahl = grid.getSelectionModel().getSelectedItems();
		int n = auswahl.size();
		if (n < 1 || !deleteMsg(n)) return;
		Integer last = grid.getSelectionModel().getSelectedIndices().get(grid.getSelectionModel().getSelectedIndices().size() - 1);
		
		ZelosApplication.modell.getEntitaeten().removeAll(auswahl);
		ZelosApplication.modell.setDirty(true);
		grid.getItems().removeAll(auswahl);

		if (ZelosApplication.modell.getEntitaeten().isEmpty()) {
			loeschen.setDisable(true);
		} else {
			grid.getSelectionModel().clearSelection();
			if (last > grid.getItems().size() - 1) {
				last = grid.getItems().size() - 1;
			}
			grid.getSelectionModel().select(last);
		}
	}
	
	private boolean deleteMsg(int n) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Löschen");
		alert.setHeaderText("");
		alert.setContentText(n == 1 ? "Möchten Sie diese Entität löschen?" : "Möchten Sie diese " + n + " Entitäten löschen?");
		ButtonType ok = new ButtonType("Löschen");
		ButtonType cancel = new ButtonType("Abbruch");
		alert.getButtonTypes().setAll(ok, cancel);
		Button cancelBtn = (Button) alert.getDialogPane().lookupButton(cancel);
		cancelBtn.setDefaultButton(true);
		cancelBtn.setCancelButton(true);
		return ok == alert.showAndWait().get();
	}
	
	@FXML
	protected void onGeneriere() {
		ObservableList<Entitaet> auswahl = grid.getSelectionModel().getSelectedItems();
		if (auswahl.size() > 0) {
			MainWindow.generiere(auswahl);
		}
	}
}
