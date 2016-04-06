package de.mwvb.zelos.model.entity;

import java.util.Map;

public interface HatEigenschaften {
	
	/**
	 * Eigenschaften nenne ich die Textform der Feld-Properties
	 */
	String getEigenschaften();
	
	void setEigenschaften(String eigenschaften);

	/**
	 * @return geparste Eigenschaften
	 */
	Map<String,String> getProperties();
}
