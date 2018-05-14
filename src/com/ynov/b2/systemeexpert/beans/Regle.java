package com.ynov.b2.systemeexpert.beans;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Regle {

	
	protected ArrayList<IFait> premisses;
	public IFait conclusion;
	protected String nom;
	
	
	public Regle(ArrayList<IFait> premisses, IFait conclusion, String nom) {
		this.premisses = premisses;
		this.conclusion = conclusion;
		this.nom = nom;
	}
	
	
	
	public ArrayList<IFait> getPremisses() {
		return premisses;
	}
	public void setPremisses(ArrayList<IFait> premisses) {
		this.premisses = premisses;
	}
	
	public IFait getConclusion() {
		return conclusion;
	}
	public void setConclusion(IFait conclusion) {
		this.conclusion = conclusion;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	public String toString() {
		String res = nom + " : IF (";
		
		StringJoiner sj = new StringJoiner(" AND ");
		for(IFait premisse : this.premisses)
			sj.add(premisse.toString());
		
		res += sj.toString() + ") THEN "+ this.conclusion.toString();
		return res;
		
	}
	
	
	
	
	
}
