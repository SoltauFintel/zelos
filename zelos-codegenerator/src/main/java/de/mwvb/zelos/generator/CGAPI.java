package de.mwvb.zelos.generator;

/**
 * Befehle f�r Command-Datei
 */
class CGAPI {
	private final CodeGenerator codeGenerator;
	
	CGAPI(CodeGenerator cg) {
		this.codeGenerator = cg;
	}
	
	public void erstelle(String vm, String dn) {
		codeGenerator.erstelle(vm, dn);
	}
}
