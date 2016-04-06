package de.mwvb.zelos.model.entity;

public interface Datentyp {

	/**
	 * @return dem Benutzer anzuzeigende fachliche Bezeichnung für den Datentyp
	 */
	String getBezeichnung();
	
	/**
	 * Hier soll true geliefert werden, wenn die Feldlänge für das DB-Script relevant ist (VARCHAR2).
	 * Bei einem CLOB wird beispielsweise false geliefert.
	 */
	boolean isText();

	boolean isBoolean();
	
	String getJavaTyp();

	String getDbTyp();
}
