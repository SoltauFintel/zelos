package de.mwvb.zelos.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Velocity Tabelle
 * 
 * @author  Marcus Warm
 * @version 20.02.2009
 */
public class SimpleVelocityTable {
	private List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
	private Map<String, String> row = null;
	
	SimpleVelocityTable() {
	}
	
	/**
	 * @return Reihen Liste, wobei jede Reihe aus einer Variablen Liste besteht
	 */
	List<Map<String, String>> getList() {
		return rows;
	}
	
	/**
	 * createRow muss zu Beginn jeder Reihe aufgerufen werden 
	 */
	public void createRow() {
		row = new HashMap<String, String>();
		rows.add(row);
	}
	
	/**
	 * Variableninhalt für die aktuelle Reihe setzen
	 */
	public void addVar(String pVarName, String pInhalt) {
		row.put(pVarName, pInhalt);
	}
}
