package de.mwvb.zelos.gui;

public class DialogModel<T> {
	public final T e;
	public final boolean neu;
	public boolean ok = false;
	public String parentBez;
	
	public DialogModel(T e, boolean neu) {
		this.e = e;
		this.neu = neu;
	}
}
