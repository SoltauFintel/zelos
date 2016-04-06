package de.mwvb.zelos.model.dao;

import de.mwvb.zelos.model.entity.Modelltyp;

public interface ModellFactory {

	/**
	 * Liefert ein Objekt mit dem man ein Modell laden und speichern kann.
	 */
	ModellDAO getModellDAO();

	/**
	 * Liefert Modelltyp anhand dateiname. Ein Modelltyp repräsentiert eine Architektur und definiert die Templates und Datentypen.
	 */
	Modelltyp getModelltyp(String dateiname);

}