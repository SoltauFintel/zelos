package de.mwvb.zelos.model.entity;

import java.util.List;

public interface Entitaet extends HatEigenschaften {
	
	String getName();

	void setName(String name);

	/** Name der Datenbanktabelle falls abweichend von getName() */
	String getNameTabelle();
	
	void setNameTabelle(String nameTabelle);

	String getBeschreibung();

	void setBeschreibung(String beschreibung);

	List<Feld> getFelder();
	
	int getAnzahlFelder();
	
	Feld createFeld();
	
	/**
	 * Fachliche Validierung der Entität inkl. aller Unterobjekte. <p>
	 * Wirft bei gescheiteter Validierung eine ValidatorException. Diese enthält den Meldungstext, welcher dem Anwender anzuzeigen ist.
	 */
	void validate();

	List<Fremdschluessel> getFremdschluessel();
	
	List<Index> getIndizes();
	
	/** INTERN */
	Modell getModell();

	/** Leere Felder füllen */
	void anreichern();
}
