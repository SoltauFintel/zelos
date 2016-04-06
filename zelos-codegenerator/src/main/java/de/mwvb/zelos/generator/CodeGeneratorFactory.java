package de.mwvb.zelos.generator;

import de.mwvb.zelos.model.entity.Entitaet;

public class CodeGeneratorFactory {

	public CodeGenerator create(Entitaet entitaet) {
		return new CodeGenerator(entitaet);
	}
}
