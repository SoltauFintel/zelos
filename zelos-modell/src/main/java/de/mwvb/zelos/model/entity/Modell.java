package de.mwvb.zelos.model.entity;

import java.util.List;

/**
 * Ein Modell hat einen Modelltyp und enthält alle Entitäten eines Subsystems.
 */
public interface Modell {

	/** Info für das Laden+Speichern des Modells */
	String getDateiname();
	void setDateiname(String dateiname);

	/** alle Entitäten eines Subsystems */
	List<Entitaet> getEntitaeten();
	void validateEntitaeten();
	Entitaet createEntitaet();

	Modelltyp getModelltyp();

	// für GUI			TODO müsste eigentlich hier raus
	boolean isDirty();
	void setDirty(boolean dirty);
}
