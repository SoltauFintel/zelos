package de.mwvb.zelos.model.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.mwvb.zelos.model.entity.Datentyp;
import de.mwvb.zelos.model.entity.Modell;
import de.mwvb.zelos.model.entity.Modelltyp;

public class ModelltypImpl implements Modelltyp {
	private static final List<Datentyp> datentypen = new ArrayList<>();
	// TODO Verweis auf die Template-Dateien
	// TODO Angaben wohin generiert werden soll (stets akt. Verzeichnis?)

	public ModelltypImpl(String dateiname) {
		datentypen.add(new DatentypImpl("Text", "String", "VARCHAR($laenge)")); // erster Typ ist stets DefaultTyp
		datentypen.add(new DatentypImpl("Integer", "int", "INT"));
		datentypen.add(new DatentypImpl("Boolean", "INT(1)"));
		datentypen.add(new DatentypImpl("Betrag", "double", "DECIMAL"));
		datentypen.add(new DatentypImpl("Double", "double", "DECIMAL"));
		datentypen.add(new DatentypImpl("Datum+Zeit", "java.util.Date", "DATETIME"));
		datentypen.add(new DatentypImpl("Datum", "java.sql.Date", "DATE"));
		datentypen.add(new DatentypImpl("Zeit", "java.util.Time", "TIME"));
		datentypen.add(new DatentypImpl("Long", "INT"));
		datentypen.add(new DatentypImpl("Text lang", "String", "CLOB"));
		datentypen.add(new DatentypImpl("char", "CHAR(1)"));
		datentypen.add(new DatentypImpl("Id", "String", "VARCHAR(16 BYTE)"));
	}
	
	public Modell createModell() {
		return new ModellImpl(this);
	}
	
	@Override
	public List<Datentyp> getDatentypen() {
		return datentypen;
	}
	
	@Override
	public Datentyp toDatentyp(String bezeichnung) {
		Optional<Datentyp> r = getDatentypen().stream()
				.filter(typ -> typ.getBezeichnung().equals(bezeichnung))
				.findFirst();
		return r.isPresent() ? r.get() : getDatentypen().get(0);
	}
}
