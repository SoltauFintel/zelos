package de.mwvb.zelos.model.entity.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.mwvb.zelos.model.entity.Datentyp;
import de.mwvb.zelos.model.entity.Entitaet;
import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.ValidatorException;

public class FeldImpl implements Feld {
	private final Entitaet entitaet;
	private String name = "";
	private String nameTabelle = "";
	private Datentyp datentyp; // Zugriff darauf nur durch: Konstruktor, getDatentyp(), setDatentyp().
	private int laenge = 1;
	private boolean erforderlich = false;
	private String vorbelegung = "";
	private String beschreibung = "";
	private String eigenschaften = "";
	private String fremdschluessel = "";
	private String index = "";

	public FeldImpl(Entitaet entitaet) {
		this.entitaet = entitaet;
		datentyp = entitaet.getModell().getModelltyp().getDatentypen().get(0);
	}
	
	@Override
	public Entitaet getEntitaet() {
		return entitaet;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameTabelle() {
		return nameTabelle;
	}

	public void setNameTabelle(String nameTabelle) {
		this.nameTabelle = nameTabelle;
	}

	@Override
	public Datentyp getTyp() {
		return datentyp;
	}

	@Override
	public void setTyp(Datentyp typ) {
		if (typ == null) {
			throw new ValidatorException("Bitte Datentyp wählen!", getEntitaet().getName(), name);
		}
		this.datentyp = typ;
	}
	
	public void setTyp(String bezeichnung) {
		Datentyp datentyp2 = getEntitaet().getModell().getModelltyp().toDatentyp(bezeichnung);
		if (!datentyp2.getBezeichnung().equalsIgnoreCase(bezeichnung)) {
			throw new RuntimeException("Datentyp '" + bezeichnung + "' kann nicht gesetzt werden, da unbekannt!");
		}
		setTyp(datentyp2);
	}

	public int getLaenge() {
		return laenge;
	}

	public void setLaenge(int laenge) {
		this.laenge = laenge;
	}

	public boolean isErforderlich() {
		return erforderlich;
	}

	public void setErforderlich(boolean erforderlich) {
		this.erforderlich = erforderlich;
	}

	public String getVorbelegung() {
		return vorbelegung;
	}

	public void setVorbelegung(String vorbelegung) {
		this.vorbelegung = vorbelegung;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	@Override
	public String getEigenschaften() {
		return eigenschaften;
	}

	@Override
	public void setEigenschaften(String eigenschaften) {
		this.eigenschaften = eigenschaften;
	}

	@Override
	public Map<String, String> getProperties() {
		return EntitaetImpl.eigenschaften2Properties(eigenschaften);
	}

	@Override
	public void setFremdschluessel(String fk) {
		this.fremdschluessel = fk;
	}

	@Override
	public String getFremdschluessel() {
		return fremdschluessel;
	}

	@Override
	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public String getIndex() {
		return index;
	}

	@Override
	public void validate() {
		validateName();
		validateNameTabelle();
		validateLaenge();
		validateVorbelegung();
		validateEigenschaften();
	}

	protected void validateName() {
		EntitaetImpl.validateName(name, "Name", getEntitaet().getName(), name);
		if (!(name.charAt(0) >= 'a' && name.charAt(0) <= 'z')) {
			throw new ValidatorException("Der Name muss mit einem Kleinbuchstaben beginnen!", getEntitaet().getName(), name);
		}
	}

	protected void validateNameTabelle() {
		EntitaetImpl.validateName(nameTabelle, "Name Tabelle", getEntitaet().getName(), name);
	}

	protected void validateLaenge() {
		if (getTyp().isText() && laenge < 1) {
			throw new ValidatorException("Bitte für Länge eine Zahl ab 1 eingeben!", getEntitaet().getName(), name);
		}
	}

	protected void validateVorbelegung() {
	}

	protected void validateEigenschaften() {
		EntitaetImpl.eigenschaften2Properties(eigenschaften);
	}

	@Override
	public String getTypBezeichnung() {
		return getTyp().getBezeichnung();
	}
	
	@Override
	public void anreichern() {
		if (StringUtils.isNotBlank(name) && StringUtils.isBlank(nameTabelle)) {
			nameTabelle = name;
		} else if (StringUtils.isNotBlank(nameTabelle) && StringUtils.isBlank(name)) {
			name = nameTabelle;
		}
		if (!getTyp().isText() && laenge != 0) {
			laenge = 0;
		}
		if (vorbelegung == null) {
			vorbelegung = "";
		}
		if (beschreibung == null) {
			beschreibung = "";
		}
		if (eigenschaften == null) {
			eigenschaften = "";
		}
		if (fremdschluessel == null) {
			fremdschluessel = "";
		}
		if (index == null) {
			index = "";
		}
	}

	@Override
	public String getName1() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	@Override
	public String getJavaTyp() {
		if ("Id".equals(getTypBezeichnung())) {
			return getEntitaet().getName() + "Id";
		}
		return getTyp().getJavaTyp();
	}

	@Override
	public String getGetter() {
		return (getTyp().isBoolean() ? "is" : "get") + getName1();
	}

	@Override
	public String getKomma() {
		List<Feld> felder = getEntitaet().getFelder();
		if (felder.get(felder.size() - 1) == this) {
			return "";
		}
		return ",";
	}

	@Override
	public String getTypTabelle() {
		return getTyp().getDbTyp().replace("$laenge", "" + getLaenge());
	}
}
