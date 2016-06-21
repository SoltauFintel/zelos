package de.mwvb.zelos.model.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import de.mwvb.zelos.model.entity.Entitaet;
import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.Fremdschluessel;
import de.mwvb.zelos.model.entity.Index;
import de.mwvb.zelos.model.entity.Modell;
import de.mwvb.zelos.model.entity.ValidatorException;

public class EntitaetImpl implements Entitaet {
	private Modell modell;
	private String name = "Neu";
	private String nameTabelle;
	private String beschreibung = "";
	private String eigenschaften = "";
	private final List<Feld> felder = new ArrayList<>();

	EntitaetImpl(Modell modell) {
		this.modell = modell;
	}

	@Override
	public Modell getModell() {
		return modell;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameTabelle() {
		if (nameTabelle == null || nameTabelle.isEmpty()) {
			return getName();
		}
		return nameTabelle;
	}
	
	public void setNameTabelle(String nameTabelle) {
		this.nameTabelle = nameTabelle;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public List<Feld> getFelder() {
		return felder;
	}
	
	public int getAnzahlFelder() {
		return felder.size();
	}

	@Override
	public Feld createFeld() {
		return new FeldImpl(this);
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
		return eigenschaften2Properties(eigenschaften);
	}
	
	public static Map<String, String> eigenschaften2Properties(final String eigenschaften) {
		Map<String, String> properties = new TreeMap<String, String>();
		String[] zeilen = eigenschaften.replace("\r\n", "\n").split("\n");
		for (int i = 0; i < zeilen.length; i++) {
			String zeile = zeilen[i];
			int o = zeile.indexOf("#"); // Kommentarzeichen
			if (o >= 0) {
				zeile = zeile.substring(0, o);
			}
			o = zeile.indexOf("=");
			if (o >= 0) {
				String li = zeile.substring(0, o).trim();
				if (!li.isEmpty()) {
					String re = zeile.substring(o + 1);
					final String zeilenfortsetzungszeichen = " \\";
					if (re.endsWith(zeilenfortsetzungszeichen)) { 
						StringBuilder sb = new StringBuilder();
						do {
							sb.append(re.substring(0, re.length() - zeilenfortsetzungszeichen.length()).trim());
							sb.append(" ");
							re = ++i < zeilen.length ? zeilen[i] : "";
						} while(re.endsWith(zeilenfortsetzungszeichen));
						sb.append(re);
						re = sb.toString();
					}
					properties.put(li, re.trim());
				}
			}
		}
		return properties;
	}

	@Override
	public List<Fremdschluessel> getFremdschluessel() {
		List<Fremdschluessel> ret = new ArrayList<>();
		// TODO
		return ret;
	}

	@Override
	public List<Index> getIndizes() {
		List<Index> ret = new ArrayList<>();
		// TODO
		return ret;
	}

	@Override
	public void validate() {
		validateName();
		validateNameTabelle();
		validateEigenscahften();

		// TODO Felder: Name nur 1x vergeben
		// TODO Felder: Name Tabelle nur 1x vergeben
		
		getFelder().forEach(f -> f.validate());
	}

	protected void validateName() {
		validateName(name, "Name", name, null, true);
		if (!(name.charAt(0) >= 'A' && name.charAt(0) <= 'Z')) {
			throw new ValidatorException("Der Name muss mit einem Großbuchstaben beginnen!", name, null);
		}
	}

	protected void validateNameTabelle() {
		validateName(nameTabelle, "Name Tabelle", name, null, false);
	}

	protected void validateEigenscahften() {
		EntitaetImpl.eigenschaften2Properties(eigenschaften);
	}

	public static void validateName(String eingabe, String feldname, String entitaetName, String feldName, boolean pflicht) {
		if (pflicht && (eingabe == null || eingabe.isEmpty())) {
			throw new ValidatorException("Bitte machen Sie eine Eingabe für das Feld " + feldname + "!", entitaetName, feldname);
		}
		for (int i = 0; i < eingabe.length(); i++) {
			char c = eingabe.charAt(i);
			if (!(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || (i > 0 && c >= '0' && c <= '9') || c == '_')) {
				if (c == ' ') {
					throw new ValidatorException("Bitte verwenden Sie keine Leerzeichen im Feld " + feldname + "!", entitaetName, feldname);
				} else if (c < ' ') {
					throw new ValidatorException("Unerlaubte Zeichen im Feld " + feldname, entitaetName, feldname);
				} else {
					throw new ValidatorException("Bitte verwenden Sie nicht das Zeichen '" + c + "' im Feld " + feldname + "!", entitaetName, feldname);
				}
			}
		}
	}

	@Override
	public void anreichern() {
		if (StringUtils.isNotBlank(name) && StringUtils.isBlank(nameTabelle)) {
			nameTabelle = name;
		} else if (StringUtils.isNotBlank(nameTabelle) && StringUtils.isBlank(name)) {
			name = nameTabelle;
		}
		if (beschreibung == null) {
			beschreibung = "";
		}
		if (eigenschaften == null) {
			eigenschaften = "";
		}
		getFelder().forEach(f -> f.anreichern());
	}
}
