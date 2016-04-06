package de.mwvb.zelos.model.entity;

public interface Feld extends HatEigenschaften {

	Entitaet getEntitaet();
	
	String getName();

	void setName(String name);

	String getNameTabelle();

	void setNameTabelle(String nameTabelle);

	/**
	 * @return nie null
	 */
	Datentyp getTyp();
	
	/**
	 * @return Typ-Bezeichnung für GUI
	 */
	String getTypBezeichnung();

	void setTyp(Datentyp typ);

	void setTyp(String typ);
	
	int getLaenge();

	void setLaenge(int laenge);

	boolean isErforderlich();

	void setErforderlich(boolean erforderlich);

	String getVorbelegung();

	void setVorbelegung(String vorbelegung);

	String getBeschreibung();

	void setBeschreibung(String beschreibung);
	
	void setFremdschluessel(String fk);
	
	String getFremdschluessel();
	
	void setIndex(String index);
	
	String getIndex();
	
	/**
	 * Fachliche Validierung <p>
	 * Wirft bei gescheiteter Validierung eine ValidatorException. Diese enthält den Meldungstext, welcher dem Anwender anzuzeigen ist.
	 */
	void validate();

	/**
	 * Leere Felder füllen
	 */
	void anreichern();
	
	/**
	 * @return wie getName(), jedoch erster Buchstabe groß
	 */
	String getName1();
	
	String getGetter();
	
	String getJavaTyp();
	
	String getTypTabelle();
	
	/**
	 * @return "," - außer beim letzten Feld
	 */
	String getKomma();
}
