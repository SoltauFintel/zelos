package de.mwvb.zelos.model.entity.impl;

import de.mwvb.zelos.model.entity.Datentyp;

public class DatentypImpl implements Datentyp {
	private final String bezeichnung;
	private final String javaTyp;
	private final String dbTyp;
	
	/**
	 * Bezeichnung ist auch der Java Typ.
	 */
	public DatentypImpl(String bezeichnung, String dbTyp) {
		this(bezeichnung, bezeichnung, dbTyp);
	}
	
	public DatentypImpl(String bezeichnung, String javaTyp, String dbTyp) {
		this.bezeichnung = bezeichnung;
		this.javaTyp = javaTyp;
		this.dbTyp = dbTyp;
	}

	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	@Override
	public String toString() { // wichtig für Combobox
		return getBezeichnung();
	}

	@Override
	public boolean isText() {
		return "Text".equals(getBezeichnung());
	}

	@Override
	public String getJavaTyp() {
		return javaTyp;
	}

	@Override
	public boolean isBoolean() {
		return "Boolean".equals(getBezeichnung());
	}

	@Override
	public String getDbTyp() {
		return dbTyp;
	}
}
