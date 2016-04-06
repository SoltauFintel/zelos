package de.mwvb.zelos.model.entity;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.mwvb.zelos.model.entity.impl.ModellImpl;

@Ignore("Implementierung fehlt noch, Tests daher rot") // TODO 
public class FremdschluesselTest {

	@Test
	public void test() {
		// Prepare
		Modell modell = ModellImpl.createForJUnit(); 
		Entitaet entitaet = modell.createEntitaet();
		entitaet.setName("Lasso");
		
		Feld feld1 = entitaet.createFeld();
		feld1.setName("foo_id");
		feld1.setFremdschluessel("Foo.foo_id");
		entitaet.getFelder().add(feld1);

		Feld feld2 = entitaet.createFeld(); // Hier gibts keinen FK.
		feld2.setName("irgendwas_id");
		entitaet.getFelder().add(feld2);

		Feld feld3 = entitaet.createFeld();
		feld3.setName("fux_id");
		feld3.setFremdschluessel("Fux"); // Feldname-Automatik
		entitaet.getFelder().add(feld3);

		Feld feld4 = entitaet.createFeld();
		feld4.setName("abc");
		feld4.setFremdschluessel("Abc.id");
		entitaet.getFelder().add(feld4);

		// Test
		List<Fremdschluessel> fkList = entitaet.getFremdschluessel();
		
		// Verify
		int index = 0;
		Assert.assertEquals(3, fkList.size());
		Assert.assertEquals("foo_id>Foo.foo_id", fkList.get(index++).toString());
		Assert.assertEquals("fux_id>Fux.fux_id", fkList.get(index++).toString());
		Assert.assertEquals("abc>Abc.id", fkList.get(index++).toString());
	}
}
