package de.mwvb.zelos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.mwvb.base.xml.XMLDocument;
import de.mwvb.base.xml.XMLElement;
import de.mwvb.zelos.generator.CodeGeneratorFactory;
import de.mwvb.zelos.model.dao.ModellDAO;
import de.mwvb.zelos.model.dao.ModellFactory;
import de.mwvb.zelos.model.dao.xml.XMLModellFactory;
import de.mwvb.zelos.model.entity.Modell;

public class ZelosConfig {
	private final List<Modell> modelle = new ArrayList<>();
	// Es ist angedacht, dass der Klassenname mal aus einer ConfigDatei kommt.
	private final ModellDAO modellDAO;
	private final String codeGeneratorFactoryClassName;
	
	public ZelosConfig() {
		this(new File("zelos.xml"));
	}

	public ZelosConfig(File zelosFile) {
		XMLDocument dok = XMLDocument.load(zelosFile.getAbsolutePath());

		String className = getClassName(dok, "ModellFactory", XMLModellFactory.class.getName());
		codeGeneratorFactoryClassName = getClassName(dok, "CodeGeneratorFactory", CodeGeneratorFactory.class.getName());
		
		ModellFactory factory = (ModellFactory) instantiateClass(className);
		modellDAO = factory.getModellDAO();
		
		dok.getChildren().stream()
			.filter(e -> "Modell".equals(e.getName()))
			.forEach(e -> {
				modelle.add(modellDAO.load(e.getText()));
			});
	}
	
	private static String getClassName(XMLDocument dok, String name, String defaultClassName) {
		Optional<XMLElement> e = dok.getChildren().stream()
				.filter(el -> name.equals(el.getName()))
				.findFirst();
		return e.isPresent() ? e.get().getText() : defaultClassName;
	}

	private static Object instantiateClass(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ModellDAO getModellDAO() {
		return modellDAO;
	}

	public List<Modell> getModelle() {
		return modelle;
	}
	
	public CodeGeneratorFactory createCodeGeneratorFactory() {
		return (CodeGeneratorFactory) instantiateClass(codeGeneratorFactoryClassName);
	}
}
