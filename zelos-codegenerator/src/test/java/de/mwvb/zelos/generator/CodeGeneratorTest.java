package de.mwvb.zelos.generator;

import org.junit.Test;

import de.mwvb.zelos.model.entity.Entitaet;
import de.mwvb.zelos.model.entity.Feld;
import de.mwvb.zelos.model.entity.Modell;
import de.mwvb.zelos.model.entity.ValidatorException;
import de.mwvb.zelos.model.entity.impl.ModellImpl;

public class CodeGeneratorTest {

	private CodeGenerator g(Entitaet ent) {
		CodeGenerator gen = new CodeGeneratorFactory().create(ent);
		gen.write = false;
		gen.debugOutput = true;
		return gen;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void entitaetNull() {
		g(null).generate();
	}
	
	@Test
	public void minimal() {
		// Prepare
		Modell modell = ModellImpl.createForJUnit();
		Entitaet entitaet = modell.createEntitaet();
		entitaet.setName("Teilnehmer");
		
		Feld id = entitaet.createFeld();
		id.setName("id");
		id.setNameTabelle("teilnehmer_id");
		id.setTyp("Id");
		id.setErforderlich(true);
		entitaet.getFelder().add(id);
		
		Feld feld2 = entitaet.createFeld();
		feld2.setName("name");
		feld2.setTyp("Text");
		feld2.setLaenge(100);
		feld2.setVorbelegung("\"Erich\"");
		feld2.setErforderlich(false);
		entitaet.getFelder().add(feld2);

		Feld feld3 = entitaet.createFeld();
		feld3.setName("alterInJahren");
		feld3.setTyp("Integer");
		feld3.setErforderlich(true);
		feld3.setVorbelegung("0");
		entitaet.getFelder().add(feld3);

		entitaet.anreichern();
		try {
			entitaet.validate();
		} catch (ValidatorException e) {
			throw new RuntimeException(e.getFullMessage());
		}

		// Test
		CodeGenerator gen = g(entitaet);
		gen.generate();
	}
}
