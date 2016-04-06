package de.mwvb.zelos.model.dao.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.mwvb.zelos.model.dao.ModellDAO;
import de.mwvb.zelos.model.dao.ModellFactory;
import de.mwvb.zelos.model.entity.Modelltyp;
import de.mwvb.zelos.model.entity.impl.ModelltypImpl;

/**
 * @author Marcus Warm
 */
public class XMLModellFactory implements ModellFactory {
	private final Map<String, Modelltyp> modelltypen = new HashMap<>();
	
	@Override
	public ModellDAO getModellDAO() {
		return new XMLModellDAO(this);
	}
	
	@Override
	public Modelltyp getModelltyp(String dateiname) {
		try {
			String key = new File(dateiname).getCanonicalPath();
			Modelltyp modelltyp = modelltypen.get(key);
			if (modelltyp == null) {
				modelltyp = new ModelltypImpl(key);
				modelltypen.put(key, modelltyp);
			}
			return modelltyp;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
