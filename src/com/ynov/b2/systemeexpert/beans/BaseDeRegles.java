package com.ynov.b2.systemeexpert.beans;

import java.util.ArrayList;

public class BaseDeRegles {

	protected ArrayList<Regle> regles;
	
	public BaseDeRegles() {
		this.regles = new ArrayList<Regle>();
	}

	public ArrayList<Regle> getRegles() {
		return regles;
	}

	public void setRegles(ArrayList<Regle> regles) {
		//this.regles.addAll(regles);
		for(Regle r : regles) {
			Regle copie = new Regle(r.premisses, r.conclusion, r.nom);
			this.regles.add(copie);
		}
	}
	
	public void clearBase() {
		this.regles.clear();
	}
	
	public void ajouterRegle(Regle regle) {
		this.regles.add(regle);
	}
	
	public void effacerRegle(Regle regle) {
		this.regles.remove(regle);
	}
	
	
	
}
