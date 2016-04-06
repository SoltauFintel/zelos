package de.mwvb.zelos.model.entity;

import org.apache.commons.lang3.StringUtils;

public class ValidatorException extends RuntimeException {
	private final String entitaetName;
	private final String feldName;
	
	/**
	 * @param feldName darf null sein
	 */
	public ValidatorException(String msg, String entitaetName, String feldName) {
		super(msg);
		if (StringUtils.isBlank(entitaetName)) {
			throw new IllegalArgumentException("entitaetName darf nicht leer sein!");
		}
		this.entitaetName = entitaetName;
		this.feldName = feldName;
	}

	/**
	 * @param feldName darf null sein
	 */
	public ValidatorException(String msg, String entitaetName, String feldName, Throwable t) {
		super(msg, t);
		if (StringUtils.isBlank(entitaetName)) {
			throw new IllegalArgumentException("entitaetName darf nicht leer sein!");
		}
		this.entitaetName = entitaetName;
		this.feldName = feldName;
	}

	public String getFullMessage() {
		if (feldName == null) {
			return "Validierung von " + entitaetName + ": " + getMessage();
		}
		return "Validierung von " + entitaetName + "." + feldName + ": " + getMessage();
	}

	public String getEntitaetName() {
		return entitaetName;
	}

	public String getFeldName() {
		return feldName;
	}
}
