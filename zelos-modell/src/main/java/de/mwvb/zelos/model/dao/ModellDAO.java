package de.mwvb.zelos.model.dao;

import de.mwvb.zelos.model.entity.Modell;

public interface ModellDAO {
	
	/**
	 * Modelldatei muss vorhanden sein.
	 */
	Modell load(String dateiname);
	
	void save(Modell modell);
}
