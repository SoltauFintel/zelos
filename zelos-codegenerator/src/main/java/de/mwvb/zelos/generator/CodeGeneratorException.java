package de.mwvb.zelos.generator;

public class CodeGeneratorException extends RuntimeException {

	public CodeGeneratorException(String msg) {
		super(msg);
	}

	public CodeGeneratorException(String msg, Throwable t) {
		super(msg, t);
	}
}
