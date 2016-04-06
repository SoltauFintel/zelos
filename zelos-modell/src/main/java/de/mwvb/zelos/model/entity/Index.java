package de.mwvb.zelos.model.entity;

import java.util.List;

public interface Index {

	boolean isUnique();
	
	List<String> getFeldnamen();
}
