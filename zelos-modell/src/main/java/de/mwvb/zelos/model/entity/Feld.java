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
	 * @return Typ-Bezeichnung f�r GUI
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
	 * Wirft bei gescheiteter Validierung eine ValidatorException. Diese enth�lt den Meldungstext, welcher dem Anwender anzuzeigen ist.
	 */
	void validate();

	/**
	 * Leere Felder f�llen
	 */
	void anreichern();
	
	/**
	 * @return wie getName(), jedoch erster Buchstabe gro�
	 */
	String getName1();
	
	String getGetter();
	
	String getJavaTyp();
	
	String getTypTabelle();
	
	/**
	 * @return "," - au�er beim letzten Feld
	 */
	String getKomma();
}
