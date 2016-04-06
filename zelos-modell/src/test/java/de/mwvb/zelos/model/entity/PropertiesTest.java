package de.mwvb.zelos.model.entity;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.mwvb.zelos.model.entity.impl.EntitaetImpl;

public class PropertiesTest {

	@Test
	public void testEigenschaften2Properties_einfachUndKommentare() {
		// Prepare
		String eigenschaften = "a=b\nc=d #mit Kommentar\n\n# nur ein Kommentar";
		
		// Test
		Map<String,String> properties = EntitaetImpl.eigenschaften2Properties(eigenschaften);

		// Verify
		Assert.assertEquals(2, properties.size());
		Assert.assertEquals("b", properties.get("a"));
		Assert.assertEquals("d", properties.get("c"));
	}

	@Test
	public void testEigenschaften2Properties_spacesUndZeilenfortsetzungen() {
		// Prepare
		String eigenschaften = "  spaces = ja \\ \nlang=kurz. \\\nund nochwas \\\nund hier ist Ende";
		
		// Test
		Map<String,String> properties = EntitaetImpl.eigenschaften2Properties(eigenschaften);

		// Verify
		Assert.assertEquals("ja \\", properties.get("spaces"));
		Assert.assertEquals("kurz. und nochwas und hier ist Ende", properties.get("lang"));
	}

	@Test
	public void testEigenschaften2Properties_zeilenfortsetzungenFehlertolerant() {
		// Prepare
		String eigenschaften = " lang =  kurz. \\\nund nochwas \\\nund hier ist Ende \\";
		
		// Test
		Map<String,String> properties = EntitaetImpl.eigenschaften2Properties(eigenschaften);

		// Verify
		Assert.assertEquals("kurz. und nochwas und hier ist Ende", properties.get("lang"));
	}

	@Test
	public void testEigenschaften2Properties_zeilenfortsetzungFehlertolerant() {
		// Prepare
		String eigenschaften = " lang =  kurz. \\\n ich = bin.dabei ";
		
		// Test
		Map<String,String> properties = EntitaetImpl.eigenschaften2Properties(eigenschaften);

		// Verify
		Assert.assertEquals("kurz.  ich = bin.dabei", properties.get("lang"));
	}

	@Test
	public void testEigenschaften2Properties_zeilenfortsetzungAberOhneLineEinrueckung() {
		// Prepare
		String eigenschaften = " lang =  kurz. \\\n\t\t Text ist eingerückt.    \\\nEnde";
		
		// Test
		Map<String,String> properties = EntitaetImpl.eigenschaften2Properties(eigenschaften);

		// Verify
		Assert.assertEquals("kurz. Text ist eingerückt. Ende", properties.get("lang"));
	}

	@Test
	public void testEigenschaften2Properties_tab() {
		// Prepare
		String eigenschaften = "\n \t A \t  = \t b \t  \n";
		
		// Test
		Map<String,String> properties = EntitaetImpl.eigenschaften2Properties(eigenschaften);

		// Verify
		Assert.assertEquals("b", properties.get("A"));
	}
}
