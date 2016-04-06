package de.mwvb.zelos.model.entity;

public interface Datentyp {

	/**
	 * @return dem Benutzer anzuzeigende fachliche Bezeichnung f�r den Datentyp
	 */
	String getBezeichnung();
	
	/**
	 * Hier soll true geliefert werden, wenn die Feldl�nge f�r das DB-Script relevant ist (VARCHAR2).
	 * Bei einem CLOB wird beispielsweise false geliefert.
	 */
	boolean isText();

	boolean isBoolean();
	
	String getJavaTyp();

	String getDbTyp();
}
