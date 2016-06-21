package de.mwvb.zelos.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.mwvb.zelos.model.entity.Entitaet;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class CodeGenerator {
	// TODO logging
	private final Entitaet ent;
	public String dir = "../zelos/modelltyp/";
	public boolean write = true;
	public boolean debugOutput = false;
	private Map<String, String> outputlist = new HashMap<String, String>();
	
	CodeGenerator(Entitaet entitaet) {
		if (entitaet == null) {
			throw new IllegalArgumentException("entitaet darf nicht null sein!");
		}
		this.ent = entitaet;
	}
	
	public void generate() {
		// Commands generieren
		String commands = generateCommands(ent);
		if (debugOutput) {
			System.out.println("### COMMANDS ###");
			System.out.println(commands);
		}
		
		// Commands-Datei ausführen
		executeCommands(commands); // ... -> erstelle() Aufrufe
		
		// Output speichern
		if (write) {
			speichereOutput();
		}
	}
	
	private String generateCommands(Entitaet ent) {
		SimpleVelocity v = v("_commands");
		v.process();
		return v.getOutput();
	}

	private void executeCommands(String commands) {
		Binding binding = new Binding();
		binding.setVariable("CG", new CGAPI(this));
		GroovyShell shell = new GroovyShell(binding);
		String delegator = "void erstelle(String vm,String dn){\nCG.erstelle(vm,dn);\n}\n";
		shell.evaluate(commands + "\n\n" + delegator);
	}
	
	private SimpleVelocity v(String dn) {
		SimpleVelocity v = new SimpleVelocity(dir + dn + ".vm");
		v.addVar("name", ent.getName());
		v.addVar("name.lower", ent.getName().toLowerCase());
		v.addVar("name.upper", ent.getName().toUpperCase());
		v.addVar("nameTabelle", ent.getNameTabelle());
		v.addVar("nameTabelle.lower", ent.getNameTabelle().toLowerCase());
		v.addVar("nameTabelle.upper", ent.getNameTabelle().toUpperCase());
		v.addVar("beschreibung", ent.getBeschreibung());
		v.addVar("anzahlFelder", ent.getAnzahlFelder());
		for (Map.Entry<String,String> e : ent.getProperties().entrySet()) {
			v.addVar(e.getKey(), e.getValue());
		}
		v.addVar("fk", ent.getFremdschluessel());
		v.addVar("nfk", ent.getFremdschluessel().size());
		v.addVar("ix", ent.getIndizes());
		v.addVar("nix", ent.getIndizes().size());
		v.addVar("felder", ent.getFelder());
		// TODO Feld Properties ???
		return v;
	}

	// wird von Command-Datei via CGAPI aufgerufen
	public void erstelle(String template, String output) {
		SimpleVelocity v = v(template);
		v.process();
		outputlist.put(output, v.getOutput());
		if (debugOutput) {
			System.out.println();
			System.out.println("### " + output + " ###");
			System.out.println();
			System.out.println(v.getOutput());
		}
	}

	private void speichereOutput() {
		for (Map.Entry<String,String> e : outputlist.entrySet()) {
			try {
				System.out.println("  generiere: " + new File(e.getKey()).getAbsolutePath());
				new File(e.getKey()).getParentFile().mkdirs();
				FileWriter w = new FileWriter(e.getKey());
				w.write(e.getValue());
				w.close();
			} catch (IOException e1) {
				throw new CodeGeneratorException("Fehler beim Speichern der Datei " + e.getKey() + "\nEntität: " + ent.getName());
			}
		}
	}
}
