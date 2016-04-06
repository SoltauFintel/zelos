package de.mwvb.zelos.model.entity.impl;

import java.util.List;

import de.mwvb.zelos.model.entity.Index;

public class IndexImpl implements Index {
	private final boolean unique;
	private final List<String> feldnamen;

	public IndexImpl(boolean unique, List<String> feldnamen) {
		this.unique = unique;
		this.feldnamen = feldnamen;
	}

	@Override
	public boolean isUnique() {
		return unique;
	}

	@Override
	public List<String> getFeldnamen() {
		return feldnamen;
	}
}
