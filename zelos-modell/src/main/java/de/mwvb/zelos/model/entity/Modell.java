package de.mwvb.zelos.model.entity;

import java.util.List;

/**
 * Ein Modell hat einen Modelltyp und enth�lt alle Entit�ten eines Subsystems.
 */
public interface Modell {

	/** Info f�r das Laden+Speichern des Modells */
	String getDateiname();
	void setDateiname(String dateiname);

	/** alle Entit�ten eines Subsystems */
	List<Entitaet> getEntitaeten();
	void validateEntitaeten();
	Entitaet createEntitaet();

	Modelltyp getModelltyp();

	// f�r GUI			TODO m�sste eigentlich hier raus
	boolean isDirty();
	void setDirty(boolean dirty);
}
