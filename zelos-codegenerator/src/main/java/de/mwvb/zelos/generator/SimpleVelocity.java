package de.mwvb.zelos.generator;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Velocity
 * <br>Mit Velocity kann man z.B. HTML Dokumente generieren.
 * 
 * <p>Diese Klasse bietet vereinfachten Zugriff auf Velocity. Velocity Vorlagendateien
 * enden normalerweise mit ".vm". Der Velocity-Editor in Eclipse bietet dann Syntax
 * Highlighting.
 * 
 * <p>Normale Variablen fangen mit $ an (z.B. "$titel").
 * 
 * <p>Beispiel für eine Schleife: 
 * <br>#foreach($i in $liste)
 * <br>- $i.name, $i.ort
 * <br>#end
 * 
 * <p>Wenn das Einfügen einer $Variable nicht klappt, kann man die auch als ${Variable}
 * einfügen. Im Code braucht man weiterhin nur "Variable" als Variablenname zu
 * definieren.
 * 
 * <p>Velocity Doku: http://velocity.apache.org/engine/devel/vtl-reference-guide.html
 * 
 * @author  Marcus Warm
 * @version 20.02.2009
 */
public class SimpleVelocity {
	private VelocityContext context;
	private Template template;
	private StringWriter sw;

	public SimpleVelocity() {
	}

	/**
	 * @param pDateinameVorlage
	 */
	public SimpleVelocity(String pDateinameVorlage) {
		setTemplate(pDateinameVorlage);
	}

	/**
	 * Vorlagendatei festlegen
	 * 
	 * @param pDateinameVorlage
	 */
	public void setTemplate(String pDateinameVorlage) {
		try {
			pDateinameVorlage = pDateinameVorlage.replace("\\", "/");
			int o = pDateinameVorlage.lastIndexOf("/");
			String pfad = ".", dnt = "";
			if (o >= 0) {
				pfad = pDateinameVorlage.substring(0, o);
				dnt = pDateinameVorlage.substring(o + 1);
			} else {
				dnt = pDateinameVorlage;
			}
			Properties p = new Properties();
			p.setProperty("file.resource.loader.path", pfad);
			Velocity.init(p);
			template = Velocity.getTemplate(dnt);
			clear();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Variablen löschen
	 */
	public void clear() {
		context = new VelocityContext();
	}
	
	/**
	 * Variableninhalt setzen
	 */
	public void addVar(String pVarName, Object pInhalt) {
		if (context == null) {
			throw new RuntimeException("Fehler: setTemplate wurde nicht aufgerufen. Context nicht initialisiert.");
		}
		context.put(pVarName, pInhalt);
	}
	
	/**
	 * Tabelle beginnen
	 * 
	 * @return Tabellenobjekt, mit dem man die Reiheninhalte definieren kann
	 */
	public SimpleVelocityTable newTable(String pVarNameOfTable) {
		SimpleVelocityTable table = new SimpleVelocityTable();
		context.put(pVarNameOfTable, table.getList());
		return table;
	}
	
	/**
	 * Dokument anhand Vorlage und Variablen erstellen.
	 * Das Ergebnis kann über getOutput abgefragt werden.
	 */
	public void process() {
		try {
			sw = new StringWriter();
			template.merge(context, sw);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return Dokument als Text
	 */
	public String getOutput() {
		return sw.toString();
	}
}
