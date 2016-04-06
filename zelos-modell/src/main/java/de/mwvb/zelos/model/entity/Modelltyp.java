package de.mwvb.zelos.model.entity;

import java.util.List;

/**
 * Ein Modelltyp beschreibt wie die Dateien für eine Architektur zu generieren sind.
 * Bestandteil sind insbesondere die Template-Dateien.
 */
public interface Modelltyp {

	Modell createModell();
	
	// Templates
	// TODO ...

	List<Datentyp> getDatentypen();
	Datentyp toDatentyp(String bezeichnung);
}
