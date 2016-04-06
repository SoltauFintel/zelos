package de.mwvb.zelos.model.entity;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.mwvb.zelos.model.entity.impl.ModellImpl;

@Ignore("Implementierung fehlt noch, Tests daher rot") // TODO
public class IndexTest {
	private Entitaet entitaet;
	
	@Before
	public void init() {
		Modell modell = ModellImpl.createForJUnit(); 
		entitaet = modell.createEntitaet();
	}
	
	@Test
	public void indexMit1Feld() {
		// Prepare
		Feld feld1 = entitaet.createFeld();
		feld1.setName("aa");
		feld1.setIndex("1");
		entitaet.getFelder().add(feld1);

		Feld feld2 = entitaet.createFeld(); // kein Index
		feld2.setName("ba");
		entitaet.getFelder().add(feld2);

		// Test
		List<Index> ixList = entitaet.getIndizes();
		
		// Verify
		Assert.assertEquals(1, ixList.size());
		Assert.assertEquals("[aa]", ixList.get(0).getFeldnamen().toString());
	}

	@Test
	public void indexMit2Felder() {
		// Prepare
		Feld feld1 = entitaet.createFeld();
		feld1.setName("aa");
		feld1.setIndex("2"); // Indexnummer braucht nicht mit 1 anzufangen
		entitaet.getFelder().add(feld1);

		Feld feld2 = entitaet.createFeld();
		feld2.setName("bb");
		feld2.setIndex("2");
		entitaet.getFelder().add(feld2);

		// Test
		List<Index> ixList = entitaet.getIndizes();
		
		// Verify
		Assert.assertEquals(1, ixList.size());
		Assert.assertEquals("[aa,bb]", ixList.get(0).getFeldnamen().toString());
	}

	@Test
	public void zweiIndizes() {
		// Prepare
		Feld feld1 = entitaet.createFeld();
		feld1.setName("aa");
		feld1.setIndex("1,2"); // 1. Feld ist in 2 Indizes enthalten
		entitaet.getFelder().add(feld1);

		Feld feld2 = entitaet.createFeld();
		feld2.setName("bb");
		feld2.setIndex("2");
		entitaet.getFelder().add(feld2);

		// Test
		List<Index> ixList = entitaet.getIndizes();
		
		// Verify
		Assert.assertEquals(2, ixList.size());
		Assert.assertEquals("[aa]", ixList.get(0).getFeldnamen().toString());
		Assert.assertEquals("[aa,bb]", ixList.get(1).getFeldnamen().toString());
		Assert.assertFalse(ixList.get(0).isUnique());
		Assert.assertFalse(ixList.get(1).isUnique());
	}

	@Test
	public void einIndex_einUnqueIndex() {
		// Prepare
		Feld feld1 = entitaet.createFeld();
		feld1.setName("aa");
		feld1.setIndex("1,2u");
		entitaet.getFelder().add(feld1);

		Feld feld2 = entitaet.createFeld();
		feld2.setName("bb");
		feld2.setIndex("2");
		entitaet.getFelder().add(feld2);

		// Test
		List<Index> ixList = entitaet.getIndizes();
		
		// Verify
		Assert.assertEquals(2, ixList.size());
		Assert.assertEquals("[aa]", ixList.get(0).getFeldnamen().toString());
		Assert.assertEquals("[aa,bb]", ixList.get(1).getFeldnamen().toString());
		Assert.assertFalse(ixList.get(0).isUnique());
		Assert.assertTrue(ixList.get(1).isUnique());
	}
}
