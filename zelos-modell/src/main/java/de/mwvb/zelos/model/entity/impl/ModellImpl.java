package de.mwvb.zelos.model.entity.impl;

import java.util.ArrayList;
import java.util.List;

import de.mwvb.zelos.model.entity.Entitaet;
import de.mwvb.zelos.model.entity.Modell;
import de.mwvb.zelos.model.entity.Modelltyp;

public class ModellImpl implements Modell {
	private final Modelltyp modelltyp;
	private final List<Entitaet> entitaeten = new ArrayList<>();
	private String dateiname;
	private boolean dirty = false;
	
	public ModellImpl(Modelltyp modelltyp) {
		this.modelltyp = modelltyp;
	}

	@Override
	public Entitaet createEntitaet() {
		return new EntitaetImpl(this);
	}

	public List<Entitaet> getEntitaeten() {
		return entitaeten;
	}

	public String getDateiname() {
		return dateiname;
	}

	public void setDateiname(String dateiname) {
		this.dateiname = dateiname;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	@Override
	public void validateEntitaeten() {
		entitaeten.forEach(entitaet -> entitaet.validate());
	}

	@Override
	public Modelltyp getModelltyp() {
		return modelltyp;
	}
	
	public static Modell createForJUnit() {
		return new ModellImpl(new ModelltypImpl(""));
	}
}
