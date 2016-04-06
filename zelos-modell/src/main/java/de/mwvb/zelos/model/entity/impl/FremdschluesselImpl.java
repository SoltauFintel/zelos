package de.mwvb.zelos.model.entity.impl;

import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.Fremdschluessel;

public class FremdschluesselImpl implements Fremdschluessel {
	private final Feld feld;
	private final String fremdTabellenname;
	private final String fremdFeldname;
	
	public FremdschluesselImpl(Feld feld, String fremdTabellenname, String fremdFeldname) {
		this.feld = feld;
		this.fremdTabellenname = fremdTabellenname;
		this.fremdFeldname = fremdFeldname;
	}
	
	@Override
	public Feld getFeld() {
		return feld;
	}

	@Override
	public String getFremdTabellenname() {
		return fremdTabellenname;
	}

	@Override
	public String getFremdFeldname() {
		return fremdFeldname;
	}

	@Override
	public String toString() {
		return feld.getNameTabelle() + ">" + fremdTabellenname + "." + fremdFeldname;
	}
}
