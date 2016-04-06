package de.mwvb.zelos.model.dao.xml;

import de.mwvb.base.xml.XMLDocument;
import de.mwvb.base.xml.XMLElement;
import de.mwvb.zelos.model.dao.ModellDAO;
import de.mwvb.zelos.model.dao.ModellFactory;
import de.mwvb.zelos.model.entity.Entitaet;
import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.Modell;
import de.mwvb.zelos.model.entity.Modelltyp;

/**
 * @author Marcus Warm
 */
public class XMLModellDAO implements ModellDAO {
	private ModellFactory factory;
	
	public XMLModellDAO(ModellFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Modell load(String dateiname) {
		XMLDocument dok = XMLDocument.load(dateiname);
		Modelltyp modelltyp = factory.getModelltyp(dok.getElement().getValue("modelltyp"));
		Modell modell = modelltyp.createModell();
		modell.setDateiname(dateiname);
		loadEntitaeten(modell, dok);
		modell.getEntitaeten().sort((a, b) -> a.getName().toLowerCase().compareTo(b.getName().toLowerCase()));
		return modell;
	}
	
	private void loadEntitaeten(Modell modell, XMLDocument dok) {
		dok.getChildren().stream()
			.filter(e_e -> "entitaet".equals(e_e.getName()))
			.forEach(e_e -> {
				Entitaet e = modell.createEntitaet();
				e.setName(e_e.getValue("name"));
				e.setNameTabelle(e_e.getValue("nameTabelle"));
				e.setBeschreibung(e_e.getMultiLineValue("beschreibung"));
				e.setEigenschaften(e_e.getMultiLineValue("eigenschaften"));
				loadFelder(e_e, e, modell);
				modell.getEntitaeten().add(e);
			});
	}
	
	private void loadFelder(XMLElement e_e, Entitaet e, Modell modell) {
		e_e.getChildren().stream()
			.filter(j -> "feld".equals(j.getName()))
			.forEach(j -> {
				Feld f = e.createFeld();
				f.setBeschreibung(j.getMultiLineValue("beschreibung"));
				f.setErforderlich("true".equals(j.getValue("erforderlich")));
				f.setLaenge(Integer.parseInt(j.getValue("laenge")));
				f.setName(j.getValue("name"));
				f.setNameTabelle(j.getValue("nameTabelle"));
				f.setTyp(modell.getModelltyp().toDatentyp(j.getValue("typ")));
				f.setVorbelegung(j.getValue("vorbelegung"));
				f.setEigenschaften(j.getMultiLineValue("eigenschaften"));
				f.setFremdschluessel(j.getValue("fremdschluessel"));
				f.setIndex(j.getValue("index"));
				e.getFelder().add(f);
			});
	}
	
	@Override
	public void save(Modell modell) {
		toDok(modell).saveFile(modell.getDateiname());
	}
	
	private XMLDocument toDok(Modell modell) {
		XMLDocument dok = new XMLDocument("<MODELL/>");
		XMLElement root = dok.getElement();
		modell.getEntitaeten().forEach(e -> {
			XMLElement e_e = root.add("entitaet");
			e_e.setValue("name", e.getName());
			e_e.setValue("nameTabelle", e.getNameTabelle());
			e_e.setMultiLineValue("beschreibung", e.getBeschreibung());
			e_e.setMultiLineValue("eigenschaften", e.getEigenschaften());
			saveFelder(e, e_e);
		});
		return dok;
	}

	private void saveFelder(Entitaet e, XMLElement e_e) {
		e.getFelder().forEach(f -> {
			XMLElement e_f = e_e.add("feld");
			e_f.setValue("name", f.getName());
			e_f.setValue("nameTabelle", f.getNameTabelle());
			e_f.setValue("typ", f.getTyp().getBezeichnung());
			e_f.setValue("laenge", "" + f.getLaenge());
			e_f.setValue("erforderlich", "" + f.isErforderlich());
			e_f.setValue("vorbelegung", f.getVorbelegung());
			e_f.setValue("fremdschluessel", f.getFremdschluessel());
			e_f.setValue("index", f.getIndex());
			e_f.setMultiLineValue("beschreibung", f.getBeschreibung());
			e_f.setMultiLineValue("eigenschaften", f.getEigenschaften());
		});
	}
}
